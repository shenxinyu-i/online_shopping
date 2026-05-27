package com.shopcoupon.order.service.impl;

import cn.hutool.db.sql.Order;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopcoupon.common.exception.BusinessException;
import com.shopcoupon.common.result.Result;
import com.shopcoupon.common.utils.SnowflakeIdGenerator;
import com.shopcoupon.order.dto.CreateOrderRequest;
import com.shopcoupon.order.entity.OrderInfo;
import com.shopcoupon.order.feign.CouponClient;
import com.shopcoupon.order.feign.InventoryClient;
import com.shopcoupon.order.feign.dto.LockStockRequest;
import com.shopcoupon.order.feign.dto.ReleaseStockRequest;
import com.shopcoupon.order.feign.dto.UseCouponRequest;
import com.shopcoupon.order.mapper.OrderMapper;
import com.shopcoupon.order.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderInfo> implements OrderService {

    private final OrderMapper orderMapper;
    private final CouponClient couponClient;
    private final InventoryClient inventoryClient;
    private final SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1, 1);

    /**
     * 创建订单（Seata全局事务：下单 + 核销券 + 预扣库存）
     */
    @Override
    @GlobalTransactional(name = "create-order", rollbackFor = Exception.class)
    public String createOrder(Long userId, CreateOrderRequest request) {
        // 1. 生成订单号
        String orderNo = idGenerator.nextIdStr();

        // 2. 查询商品价格（暂时模拟）
        BigDecimal productPrice = getProductPrice(request.getProductId());
        if (productPrice == null || productPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("商品价格异常");
        }

        // 3. 计算原价
        BigDecimal originalAmount = productPrice.multiply(new BigDecimal(request.getQuantity()));
        BigDecimal discountAmount = BigDecimal.ZERO;
        BigDecimal finalAmount = originalAmount;

        // 4. 处理优惠券
        if (request.getCouponId() != null) {
            // 4.1 先查优惠金额
            Result<BigDecimal> discountResult = couponClient.getDiscountAmount(
                    request.getCouponId(), originalAmount);
            if (!discountResult.isSuccess()) {
                throw new BusinessException(discountResult.getMessage());
            }
            discountAmount = discountResult.getData();

            // 4.2 计算实付
            finalAmount = originalAmount.subtract(discountAmount);
            if (finalAmount.compareTo(BigDecimal.ZERO) < 0) {
                finalAmount = BigDecimal.ZERO;
            }

            // 4.3 再核销券
            Result<Void> couponResult = couponClient.useCoupon(
                    new UseCouponRequest(request.getCouponId(), userId));
            if (!couponResult.isSuccess()) {
                throw new BusinessException(couponResult.getMessage());
            }
        }

        // 5. 锁定库存
        Result<Void> inventoryResult = inventoryClient.lockStock(
                new LockStockRequest(request.getProductId(), request.getQuantity(), orderNo));
        if (!inventoryResult.isSuccess()) {
            throw new BusinessException(inventoryResult.getMessage());
        }

        // 6. 保存订单
        saveOrderRecord(userId, request, orderNo, productPrice, originalAmount, discountAmount, finalAmount);

        log.info("订单创建成功: orderNo={}, userId={}, 原价={}, 优惠={}, 实付={}",
                orderNo, userId, originalAmount, discountAmount, finalAmount);
        return orderNo;
    }

    /**
     * 分页查询用户订单列表
     */
    @Override
    public List<OrderInfo> listOrders(Long userId, String status) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }

        // 构建查询条件
        LambdaQueryWrapper<OrderInfo> queryWrapper = new LambdaQueryWrapper<OrderInfo>()
                .eq(OrderInfo::getUserId, userId)
                .orderByDesc(OrderInfo::getCreatedAt);

        // 如果指定了状态，添加状态条件
        if (status != null && !status.trim().isEmpty()) {
            queryWrapper.eq(OrderInfo::getStatus, status);
        }

        // 这里默认分页参数：页码1，每页10条（实际项目中应接收分页参数）
        IPage<OrderInfo> page = new Page<>(1, 10);
        IPage<OrderInfo> orderPage = orderMapper.selectPage(page, queryWrapper);

        return orderPage.getRecords();
    }

    /**
     * 查询订单详情并校验归属
     */
    @Override
    public OrderInfo getOrderDetail(Long userId, String orderNo) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        if (orderNo == null || orderNo.trim().isEmpty()) {
            throw new BusinessException("订单号不能为空");
        }

        // 查询订单
        OrderInfo orderInfo = getOrderByOrderNo(orderNo);
        if (orderInfo == null) {
            throw new BusinessException("订单不存在");
        }

        // 校验订单归属
        if (!Objects.equals(orderInfo.getUserId(), userId)) {
            throw new BusinessException("无权查看该订单");
        }

        return orderInfo;
    }

    /**
     * 取消订单（库存回补 + 券退还）
     */
    @Override
    @GlobalTransactional(name = "cancel-order", rollbackFor = Exception.class)
    public void cancelOrder(Long userId, String orderNo) {
        if (userId == null) {
            throw new BusinessException("用户ID不能为空");
        }
        if (orderNo == null || orderNo.trim().isEmpty()) {
            throw new BusinessException("订单号不能为空");
        }

        // 1. 查询订单并校验
        OrderInfo orderInfo = getOrderByOrderNo(orderNo);
        if (orderInfo == null) {
            throw new BusinessException("订单不存在");
        }
        if (!Objects.equals(orderInfo.getUserId(), userId)) {
            throw new BusinessException("无权取消该订单");
        }

        // 2. 校验订单状态（只能取消待支付订单）
        if (!"PENDING".equals(orderInfo.getStatus())) {
            throw new BusinessException("仅待支付订单可取消");
        }

        // 3. 回补库存
        Result<Void> releaseResult = inventoryClient.releaseStock(
                new ReleaseStockRequest(orderInfo.getProductId(), orderInfo.getQuantity(), orderNo));
        if (!releaseResult.isSuccess()) {
            throw new BusinessException("库存回补失败：" + releaseResult.getMessage());
        }

        // 4. 退还优惠券（如果有使用优惠券）
        if (orderInfo.getCouponId() != null) {
            Result<Void> restoreResult = couponClient.restoreCoupon(
                    new UseCouponRequest(orderInfo.getCouponId(), userId));
            if (!restoreResult.isSuccess()) {
                throw new BusinessException("优惠券退还失败：" + restoreResult.getMessage());
            }
        }

        // 5. 更新订单状态为已取消
        updateOrderStatus(orderNo, "CANCELLED");

        log.info("订单取消成功，订单号：{}，用户ID：{}", orderNo, userId);
    }

    /**
     * 更新订单状态（供支付服务调用，参与Seata全局事务）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderStatus(String orderNo, String status) {
        if (orderNo == null || orderNo.trim().isEmpty()) {
            throw new BusinessException("订单号不能为空");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new BusinessException("订单状态不能为空");
        }

        // 校验状态合法性
        if (!List.of("PENDING", "PAID", "CANCELLED", "COMPLETED").contains(status)) {
            throw new BusinessException("订单状态不合法");
        }

        // 查询订单
        OrderInfo orderInfo = getOrderByOrderNo(orderNo);
        if (orderInfo == null) {
            throw new BusinessException("订单不存在");
        }

        // 更新状态
        OrderInfo updateOrder = new OrderInfo();
        updateOrder.setId(orderInfo.getId());
        updateOrder.setStatus(status);
        updateOrder.setUpdatedAt(LocalDateTime.now());

        int updateCount = orderMapper.updateById(updateOrder);
        if (updateCount == 0) {
            throw new BusinessException("订单状态更新失败");
        }

        log.info("订单状态更新成功，订单号：{}，新状态：{}", orderNo, status);
    }

    // ------------------------- 私有辅助方法 -------------------------

    /**
     * 保存订单记录到数据库
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveOrderRecord(Long userId, CreateOrderRequest request, String orderNo,
                                 BigDecimal productPrice, BigDecimal originalAmount,
                                 BigDecimal discountAmount, BigDecimal finalAmount) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(orderNo);
        orderInfo.setUserId(userId);
        orderInfo.setShopId(request.getShopId());
        orderInfo.setProductId(request.getProductId());
        orderInfo.setProductName(getProductName(request.getProductId())); // 模拟商品名称
        orderInfo.setProductPrice(productPrice);
        orderInfo.setQuantity(request.getQuantity());
        orderInfo.setOriginalAmount(originalAmount);
        orderInfo.setDiscountAmount(discountAmount);
        orderInfo.setFinalAmount(finalAmount);
        orderInfo.setCouponId(request.getCouponId());
        orderInfo.setStatus("PENDING"); // 初始状态：待支付
        orderInfo.setCreatedAt(LocalDateTime.now());
        orderInfo.setUpdatedAt(LocalDateTime.now());

        int insertCount = orderMapper.insert(orderInfo);
        if (insertCount == 0) {
            throw new BusinessException("订单记录保存失败");
        }
    }

    /**
     * 根据订单号查询订单
     */
    private OrderInfo getOrderByOrderNo(String orderNo) {
        LambdaQueryWrapper<OrderInfo> queryWrapper = new LambdaQueryWrapper<OrderInfo>()
                .eq(OrderInfo::getOrderNo, orderNo);
        return orderMapper.selectOne(queryWrapper);
    }

    /**
     * 模拟获取商品价格（实际项目中需调用商品服务）
     */
    private BigDecimal getProductPrice(Long productId) {
        // 实际项目中应通过Feign调用商品服务获取
        // 这里模拟返回固定价格，可根据productId设置不同价格
        return new BigDecimal("99.00");
    }

    /**
     * 模拟获取商品名称（实际项目中需调用商品服务）
     */
    private String getProductName(Long productId) {
        // 实际项目中应通过Feign调用商品服务获取
        return "默认商品名称-" + productId;
    }

    /**
     * 模拟获取优惠券优惠金额（实际项目中需调用优惠券服务）
     */
    private BigDecimal getCouponDiscountAmount(Long couponId, BigDecimal originalAmount) {
        // 实际项目中应通过Feign调用优惠券服务获取优惠金额
        // 这里模拟返回固定优惠金额10元
        return new BigDecimal("10.00");
    }
}