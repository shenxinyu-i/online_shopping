package com.shopcoupon.payment.feign;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.payment.feign.dto.ConfirmDeductRequest;
import com.shopcoupon.payment.feign.fallback.InventoryClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", fallbackFactory = InventoryClientFallbackFactory.class)
public interface InventoryClient {

    @PostMapping("/api/inventory/confirm")
    Result<Void> confirmDeduct(@RequestBody ConfirmDeductRequest request);
}
