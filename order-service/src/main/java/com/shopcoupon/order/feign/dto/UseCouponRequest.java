package com.shopcoupon.order.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UseCouponRequest {

    private Long couponId;

    private Long userId;
}
