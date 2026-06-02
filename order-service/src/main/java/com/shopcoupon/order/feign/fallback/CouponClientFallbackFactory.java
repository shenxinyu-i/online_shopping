package com.shopcoupon.order.feign.fallback;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.order.feign.CouponClient;
import com.shopcoupon.order.feign.dto.UseCouponRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class CouponClientFallbackFactory implements FallbackFactory<CouponClient> {

    @Override
    public CouponClient create(Throwable cause) {
        log.error("优惠券服务调用异常: {}", cause.getMessage());
        return new CouponClient() {
            @Override
            public Result<Void> useCoupon(UseCouponRequest request) {
                return Result.fail("优惠券服务繁忙，请稍后重试");
            }

            @Override
            public Result<Void> restoreCoupon(UseCouponRequest request) {
                return Result.fail("优惠券服务繁忙，请稍后重试");
            }

            @Override
            public Result<BigDecimal> getDiscountAmount(Long couponId, BigDecimal originalAmount, Long shopId) {
                log.warn("getDiscountAmount 降级: couponId={}, shopId={}", couponId, shopId);
                return Result.fail("优惠券服务繁忙，请稍后重试");
            }
        };
    }
}

