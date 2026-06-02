package com.shopcoupon.coupon.controller;

import com.shopcoupon.coupon.dto.UseCouponRequest;
import com.shopcoupon.coupon.service.UserCouponService;
import com.shopcoupon.common.result.Result;
import io.lettuce.core.dynamic.annotation.Param;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * 内部接口 - 供 Order-Service Feign 调用
 */
@RestController
@RequestMapping("/api/coupon/internal")
@RequiredArgsConstructor
public class InternalCouponController {

    private final UserCouponService userCouponService;

    @PostMapping("/use")
    public Result<Void> useCoupon(@Valid @RequestBody UseCouponRequest request) {
        userCouponService.useCoupon(request.getCouponId(), request.getUserId());
        return Result.success(null);
    }

    @PostMapping("/restore")
    public Result<Void> restoreCoupon(@Valid @RequestBody UseCouponRequest request) {
        userCouponService.restoreCoupon(request.getCouponId(), request.getUserId());
        return Result.success(null);
    }
    @GetMapping("/discount")
    public Result<BigDecimal> getDiscountAmount(
            @RequestParam Long couponId,
            @RequestParam BigDecimal originalAmount,
            @RequestParam Long shopId) {
        BigDecimal discount = userCouponService.calculateDiscount(couponId, originalAmount, shopId);
        return Result.success(discount);
    }

}
