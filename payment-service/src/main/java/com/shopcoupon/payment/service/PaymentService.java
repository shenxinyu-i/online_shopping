package com.shopcoupon.payment.service;

import com.shopcoupon.payment.dto.CreatePaymentRequest;
import com.shopcoupon.payment.entity.PaymentRecord;

public interface PaymentService {

    /**
     * 创建支付单
     */
    PaymentRecord createPayment(Long userId, CreatePaymentRequest request);

    /**
     * 模拟支付成功（Seata全局事务：支付 + 改订单状态 + 确认扣库存）
     */
    void processPayment(String paymentNo);

    PaymentRecord getPaymentStatus(String paymentNo);

    /**
     * 退款
     */
    void refund(String paymentNo);
}
