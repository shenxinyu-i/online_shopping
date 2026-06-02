package com.shopcoupon.order.feign;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.order.feign.dto.UseCouponRequest;
import com.shopcoupon.order.feign.fallback.CouponClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "coupon-service", fallbackFactory = CouponClientFallbackFactory.class)
public interface CouponClient {

    /**
     * 核销优惠券
     * @param request
     * @return
     */
    @PostMapping("/api/coupon/internal/use")
    Result<Void> useCoupon(@RequestBody UseCouponRequest request);

    /**
     * 退回优惠券
     * @param request
     * @return
     */
    @PostMapping("/api/coupon/internal/restore")
    Result<Void> restoreCoupon(@RequestBody UseCouponRequest request);

    /**
     * 获取优惠券优惠金额
     * @param couponId
     * @param originalAmount
     * @return
     */
    @GetMapping("/api/coupon/internal/discount")
    Result<BigDecimal> getDiscountAmount(
            @RequestParam("couponId") Long couponId,
            @RequestParam("originalAmount") BigDecimal originalAmount,
            @RequestParam("shopId") Long shopId
    );

}
