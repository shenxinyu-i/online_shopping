package com.shopcoupon.order.service;

import com.shopcoupon.order.dto.CreateOrderRequest;
import com.shopcoupon.order.entity.OrderInfo;

import java.util.List;

public interface OrderService {

    /**
     * 创建订单（Seata全局事务：下单 + 核销券 + 预扣库存）
     */
    String createOrder(Long userId, CreateOrderRequest request);

    List<OrderInfo> listOrders(Long userId, String status);

    OrderInfo getOrderDetail(Long userId, String orderNo);

    /**
     * 取消订单（库存回补 + 券退还）
     */
    void cancelOrder(Long userId, String orderNo);

    /**
     * 更新订单状态（供Payment-Service Feign调用）
     */
    void updateOrderStatus(String orderNo, String status);

    /**
     * 根据订单号查询订单（供内部Feign调用，不校验归属）
     */
    OrderInfo getOrderByOrderNo(String orderNo);
}
