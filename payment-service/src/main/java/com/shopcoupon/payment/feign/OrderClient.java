package com.shopcoupon.payment.feign;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.payment.feign.dto.OrderDTO;
import com.shopcoupon.payment.feign.dto.UpdateOrderStatusRequest;
import com.shopcoupon.payment.feign.fallback.OrderClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service", fallbackFactory = OrderClientFallbackFactory.class)
public interface OrderClient {

    @PutMapping("/api/order/internal/status")
    Result<Void> updateOrderStatus(@RequestBody UpdateOrderStatusRequest request);

    @GetMapping("/api/order/internal/{orderNo}")
    Result<OrderDTO> getOrderDetail(@PathVariable("orderNo") String orderNo);
}
