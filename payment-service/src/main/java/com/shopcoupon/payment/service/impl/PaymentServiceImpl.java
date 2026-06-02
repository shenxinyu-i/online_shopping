package com.shopcoupon.payment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.shopcoupon.common.exception.BusinessException;
import com.shopcoupon.common.result.Result;
import com.shopcoupon.common.utils.SnowflakeIdGenerator;
import com.shopcoupon.payment.dto.CreatePaymentRequest;
import com.shopcoupon.payment.entity.PaymentFlow;
import com.shopcoupon.payment.entity.PaymentRecord;
import com.shopcoupon.payment.feign.InventoryClient;
import com.shopcoupon.payment.feign.OrderClient;
import com.shopcoupon.payment.feign.dto.ConfirmDeductRequest;
import com.shopcoupon.payment.feign.dto.OrderDTO;
import com.shopcoupon.payment.feign.dto.UpdateOrderStatusRequest;
import com.shopcoupon.payment.mapper.PaymentFlowMapper;
import com.shopcoupon.payment.mapper.PaymentRecordMapper;
import com.shopcoupon.payment.service.PaymentService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRecordMapper paymentRecordMapper;
    private final PaymentFlowMapper paymentFlowMapper;
    private final OrderClient orderClient;
    private final InventoryClient inventoryClient;
    private final SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(2, 1);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentRecord createPayment(Long userId, CreatePaymentRequest request) {
        // 1. 通过Feign查询订单信息
        Result<OrderDTO> orderResult = orderClient.getOrderDetail(request.getOrderNo());
        if (!orderResult.isSuccess() || orderResult.getData() == null) {
            throw new BusinessException("订单不存在或查询失败");
        }
        OrderDTO order = orderResult.getData();

        // 2. 校验订单归属
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException("无权对该订单进行支付");
        }

        // 3. 校验订单状态（只能支付待支付订单）
        if (!"PENDING".equals(order.getStatus())) {
            throw new BusinessException("订单状态异常，当前状态：" + order.getStatus());
        }

        // 4. 生成支付单号
        String paymentNo = idGenerator.nextIdStr();

        // 5. 创建支付记录
        PaymentRecord record = new PaymentRecord();
        record.setPaymentNo(paymentNo);
        record.setOrderNo(request.getOrderNo());
        record.setUserId(userId);
        record.setAmount(order.getFinalAmount());
        record.setPaymentMethod(request.getPaymentMethod() != null ? request.getPaymentMethod() : "WECHAT");
        record.setStatus("PENDING");
        record.setCreatedAt(LocalDateTime.now());
        record.setUpdatedAt(LocalDateTime.now());

        int inserted = paymentRecordMapper.insert(record);
        if (inserted == 0) {
            throw new BusinessException("创建支付单失败");
        }

        log.info("支付单创建成功: paymentNo={}, orderNo={}, amount={}",
                paymentNo, request.getOrderNo(), order.getFinalAmount());
        return record;
    }

    @Override
    @GlobalTransactional(name = "process-payment", rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public void processPayment(String paymentNo) {
        // 1. 查询支付记录
        PaymentRecord record = getPaymentByNo(paymentNo);
        if (record == null) {
            throw new BusinessException("支付单不存在");
        }

        // 2. 校验支付状态
        if (!"PENDING".equals(record.getStatus())) {
            throw new BusinessException("支付单状态异常，当前状态：" + record.getStatus());
        }

        // 3. 更新支付状态为 SUCCESS
        record.setStatus("SUCCESS");
        record.setPaidAt(LocalDateTime.now());
        record.setUpdatedAt(LocalDateTime.now());
        int updated = paymentRecordMapper.updateById(record);
        if (updated == 0) {
            throw new BusinessException("更新支付状态失败");
        }

        // 4. 记录支付流水
        savePaymentFlow(record.getPaymentNo(), record.getOrderNo(), "PAYMENT",
                record.getAmount(), "支付成功");

        // 5. Feign调用 order-service 更新订单状态为 PAID
        Result<Void> orderResult = orderClient.updateOrderStatus(
                new UpdateOrderStatusRequest(record.getOrderNo(), "PAID"));
        if (!orderResult.isSuccess()) {
            throw new BusinessException("更新订单状态失败：" + orderResult.getMessage());
        }

        // 6. Feign调用 inventory-service 确认扣减库存
        // 需要从订单获取 productId 和 quantity
        Result<OrderDTO> orderDetailResult = orderClient.getOrderDetail(record.getOrderNo());
        if (!orderDetailResult.isSuccess() || orderDetailResult.getData() == null) {
            throw new BusinessException("查询订单详情失败");
        }
        OrderDTO order = orderDetailResult.getData();

        Result<Void> inventoryResult = inventoryClient.confirmDeduct(
                new ConfirmDeductRequest(order.getProductId(), order.getQuantity(), record.getOrderNo()));
        if (!inventoryResult.isSuccess()) {
            throw new BusinessException("确认扣减库存失败：" + inventoryResult.getMessage());
        }

        log.info("支付处理成功: paymentNo={}, orderNo={}, amount={}",
                paymentNo, record.getOrderNo(), record.getAmount());
    }

    @Override
    public PaymentRecord getPaymentStatus(String paymentNo) {
        PaymentRecord record = getPaymentByNo(paymentNo);
        if (record == null) {
            throw new BusinessException("支付单不存在");
        }
        return record;
    }

    @Override
    @GlobalTransactional(name = "refund-payment", rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public void refund(String paymentNo) {
        // 1. 查询支付记录
        PaymentRecord record = getPaymentByNo(paymentNo);
        if (record == null) {
            throw new BusinessException("支付单不存在");
        }

        // 2. 校验支付状态（只能退款已支付订单）
        if (!"SUCCESS".equals(record.getStatus())) {
            throw new BusinessException("支付单状态异常，无法退款，当前状态：" + record.getStatus());
        }

        // 3. 更新支付状态为 REFUNDED
        record.setStatus("REFUNDED");
        record.setUpdatedAt(LocalDateTime.now());
        int updated = paymentRecordMapper.updateById(record);
        if (updated == 0) {
            throw new BusinessException("更新退款状态失败");
        }

        // 4. 记录退款流水
        savePaymentFlow(record.getPaymentNo(), record.getOrderNo(), "REFUND",
                record.getAmount(), "退款成功");

        // 5. 更新订单状态为已取消
        Result<Void> orderResult = orderClient.updateOrderStatus(
                new UpdateOrderStatusRequest(record.getOrderNo(), "CANCELLED"));
        if (!orderResult.isSuccess()) {
            throw new BusinessException("更新订单状态失败：" + orderResult.getMessage());
        }

        // 6. 释放库存
        Result<OrderDTO> orderDetailResult = orderClient.getOrderDetail(record.getOrderNo());
        if (orderDetailResult.isSuccess() && orderDetailResult.getData() != null) {
            OrderDTO order = orderDetailResult.getData();
            Result<Void> inventoryResult = inventoryClient.releaseStock(
                    new com.shopcoupon.payment.feign.dto.ReleaseStockRequest(
                            order.getProductId(), order.getQuantity(), record.getOrderNo()));
            if (!inventoryResult.isSuccess()) {
                log.warn("退款时释放库存失败: {}", inventoryResult.getMessage());
            }
        }

        log.info("退款处理成功: paymentNo={}, orderNo={}, amount={}",
                paymentNo, record.getOrderNo(), record.getAmount());
    }

    // ------------------------- 私有辅助方法 -------------------------

    /**
     * 根据支付单号查询支付记录
     */
    private PaymentRecord getPaymentByNo(String paymentNo) {
        return paymentRecordMapper.selectOne(new LambdaQueryWrapper<PaymentRecord>()
                .eq(PaymentRecord::getPaymentNo, paymentNo));
    }

    /**
     * 保存支付/退款流水
     */
    private void savePaymentFlow(String paymentNo, String orderNo, String flowType,
                                  BigDecimal amount, String remark) {
        PaymentFlow flow = new PaymentFlow();
        flow.setPaymentNo(paymentNo);
        flow.setOrderNo(orderNo);
        flow.setFlowType(flowType);
        flow.setAmount(amount);
        flow.setRemark(remark);
        flow.setCreatedAt(LocalDateTime.now());
        paymentFlowMapper.insert(flow);
    }
}
