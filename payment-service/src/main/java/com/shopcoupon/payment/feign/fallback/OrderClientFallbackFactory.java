package com.shopcoupon.payment.feign.fallback;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.payment.feign.OrderClient;
import com.shopcoupon.payment.feign.dto.UpdateOrderStatusRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderClientFallbackFactory implements FallbackFactory<OrderClient> {

    @Override
    public OrderClient create(Throwable cause) {
        log.error("订单服务调用异常: {}", cause.getMessage());
        return request -> Result.fail("订单服务繁忙，请稍后重试");
    }
}
