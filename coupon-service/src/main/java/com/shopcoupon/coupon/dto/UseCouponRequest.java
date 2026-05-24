package com.shopcoupon.coupon.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UseCouponRequest {

    @NotNull(message = "用户券ID不能为空")
    private Long couponId;

    @NotNull(message = "用户ID不能为空")
    private Long userId;
}
