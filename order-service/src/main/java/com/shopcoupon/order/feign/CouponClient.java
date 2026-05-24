package com.shopcoupon.order.feign;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.order.feign.dto.UseCouponRequest;
import com.shopcoupon.order.feign.fallback.CouponClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "coupon-service", fallbackFactory = CouponClientFallbackFactory.class)
public interface CouponClient {

    @PostMapping("/api/coupon/internal/use")
    Result<Void> useCoupon(@RequestBody UseCouponRequest request);

    @PostMapping("/api/coupon/internal/restore")
    Result<Void> restoreCoupon(@RequestBody UseCouponRequest request);
}
