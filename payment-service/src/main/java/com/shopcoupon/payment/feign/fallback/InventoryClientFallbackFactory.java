package com.shopcoupon.payment.feign.fallback;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.payment.feign.InventoryClient;
import com.shopcoupon.payment.feign.dto.ConfirmDeductRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InventoryClientFallbackFactory implements FallbackFactory<InventoryClient> {

    @Override
    public InventoryClient create(Throwable cause) {
        log.error("库存服务调用异常: {}", cause.getMessage());
        return request -> Result.fail("库存服务繁忙，请稍后重试");
    }
}
