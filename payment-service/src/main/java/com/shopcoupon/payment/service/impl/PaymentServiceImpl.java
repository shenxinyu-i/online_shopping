package com.shopcoupon.payment.service.impl;

import com.shopcoupon.common.exception.BusinessException;
import com.shopcoupon.common.result.Result;
import com.shopcoupon.common.utils.SnowflakeIdGenerator;
import com.shopcoupon.payment.dto.CreatePaymentRequest;
import com.shopcoupon.payment.entity.PaymentRecord;
import com.shopcoupon.payment.feign.InventoryClient;
import com.shopcoupon.payment.feign.OrderClient;
import com.shopcoupon.payment.feign.dto.ConfirmDeductRequest;
import com.shopcoupon.payment.feign.dto.UpdateOrderStatusRequest;
import com.shopcoupon.payment.mapper.PaymentFlowMapper;
import com.shopcoupon.payment.mapper.PaymentRecordMapper;
import com.shopcoupon.payment.service.PaymentService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRecordMapper paymentRecordMapper;
    private final PaymentFlowMapper paymentFlowMapper;
    private final OrderClient orderClient;
    private final InventoryClient inventoryClient;
    private final SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(2, 1);

    @Override
    public PaymentRecord createPayment(Long userId, CreatePaymentRequest request) {
        // TODO: 查询订单信息，创建支付记录
        throw new BusinessException("创建支付单功能待完善");
    }

    @Override
    @GlobalTransactional(name = "process-payment", rollbackFor = Exception.class)
    public void processPayment(String paymentNo) {
        // TODO: 1. 更新支付状态为 SUCCESS（本地事务）
        // TODO: 2. Feign调用 order-service 更新订单状态为 PAID
        // TODO: 3. Feign调用 inventory-service 确认扣减库存
        // TODO: 4. 记录支付流水 payment_flow

        PaymentRecord record = paymentRecordMapper.selectById(paymentNo);
        if (record == null) {
            // TODO: 按 paymentNo 字段查询而非主键
            throw new BusinessException("支付单功能待完善：需先实现 createPayment 和按 paymentNo 查询");
        }

        Result<Void> orderResult = orderClient.updateOrderStatus(
                new UpdateOrderStatusRequest(record.getOrderNo(), "PAID"));
        if (!orderResult.isSuccess()) {
            throw new BusinessException(orderResult.getMessage());
        }

        // TODO: 从订单获取 productId 和 quantity
        Result<Void> inventoryResult = inventoryClient.confirmDeduct(
                new ConfirmDeductRequest(null, null, record.getOrderNo()));
        if (!inventoryResult.isSuccess()) {
            throw new BusinessException(inventoryResult.getMessage());
        }
    }

    @Override
    public PaymentRecord getPaymentStatus(String paymentNo) {
        // TODO: 查询支付状态
        throw new BusinessException("查询支付状态功能待完善");
    }

    @Override
    public void refund(String paymentNo) {
        // TODO: 退款逻辑，更新支付状态，记录流水
        throw new BusinessException("退款功能待完善");
    }
}
