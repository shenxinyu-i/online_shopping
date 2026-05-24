package com.shopcoupon.order.feign.fallback;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.order.feign.InventoryClient;
import com.shopcoupon.order.feign.dto.LockStockRequest;
import com.shopcoupon.order.feign.dto.ReleaseStockRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InventoryClientFallbackFactory implements FallbackFactory<InventoryClient> {

    @Override
    public InventoryClient create(Throwable cause) {
        log.error("库存服务调用异常: {}", cause.getMessage());
        return new InventoryClient() {
            @Override
            public Result<Void> lockStock(LockStockRequest request) {
                return Result.fail("库存服务繁忙，请稍后重试");
            }

            @Override
            public Result<Void> releaseStock(ReleaseStockRequest request) {
                return Result.fail("库存服务繁忙，请稍后重试");
            }
        };
    }
}
