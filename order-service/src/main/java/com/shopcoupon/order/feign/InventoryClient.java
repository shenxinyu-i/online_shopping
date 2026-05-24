package com.shopcoupon.order.feign;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.order.feign.dto.LockStockRequest;
import com.shopcoupon.order.feign.dto.ReleaseStockRequest;
import com.shopcoupon.order.feign.fallback.InventoryClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", fallbackFactory = InventoryClientFallbackFactory.class)
public interface InventoryClient {

    @PostMapping("/api/inventory/lock")
    Result<Void> lockStock(@RequestBody LockStockRequest request);

    @PostMapping("/api/inventory/release")
    Result<Void> releaseStock(@RequestBody ReleaseStockRequest request);
}
