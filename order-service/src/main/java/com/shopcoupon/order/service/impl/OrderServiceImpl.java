package com.shopcoupon.order.service.impl;

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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final CouponClient couponClient;
    private final InventoryClient inventoryClient;
    private final SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(1, 1);

    @Override
    @GlobalTransactional(name = "create-order", rollbackFor = Exception.class)
    public String createOrder(Long userId, CreateOrderRequest request) {
        // TODO: 1. 查询商品信息，计算原价/优惠/实付金额
        // TODO: 2. 创建订单记录（本地事务）
        String orderNo = idGenerator.nextIdStr();

        if (request.getCouponId() != null) {
            Result<Void> couponResult = couponClient.useCoupon(
                    new UseCouponRequest(request.getCouponId(), userId));
            if (!couponResult.isSuccess()) {
                throw new BusinessException(couponResult.getMessage());
            }
        }

        Result<Void> inventoryResult = inventoryClient.lockStock(
                new LockStockRequest(request.getProductId(), request.getQuantity(), orderNo));
        if (!inventoryResult.isSuccess()) {
            throw new BusinessException(inventoryResult.getMessage());
        }

        // TODO: 保存订单到 order_info 表
        return orderNo;
    }

    @Override
    public List<OrderInfo> listOrders(Long userId, String status) {
        // TODO: 分页查询用户订单列表
        throw new BusinessException("订单列表功能待完善");
    }

    @Override
    public OrderInfo getOrderDetail(Long userId, String orderNo) {
        // TODO: 查询订单详情并校验归属
        throw new BusinessException("订单详情功能待完善");
    }

    @Override
    public void cancelOrder(Long userId, String orderNo) {
        // TODO: 校验订单状态，调用库存回补和券退还
        throw new BusinessException("取消订单功能待完善");
    }

    @Override
    public void updateOrderStatus(String orderNo, String status) {
        // TODO: 更新订单状态（供支付服务调用，参与Seata全局事务）
        throw new BusinessException("更新订单状态功能待完善");
    }
}
