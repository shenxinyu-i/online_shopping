package com.shopcoupon.coupon.service;

import com.shopcoupon.coupon.entity.UserCoupon;

import java.math.BigDecimal;
import java.util.List;

public interface UserCouponService {

    List<UserCoupon> listMyCoupons(Long userId, String status);

    /**
     * 核销优惠券（供Order-Service Feign调用，参与Seata全局事务）
     */
    void useCoupon(Long couponId, Long userId);

    /**
     * 退还优惠券（订单取消时调用）
     */
    void restoreCoupon(Long couponId, Long userId);
    /**
     * 计算优惠券优惠金额
     */
    BigDecimal calculateDiscount(Long couponId,BigDecimal originAmount);
}
