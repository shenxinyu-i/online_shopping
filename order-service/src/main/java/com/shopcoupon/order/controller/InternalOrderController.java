package com.shopcoupon.order.controller;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.order.dto.UpdateOrderStatusRequest;
import com.shopcoupon.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 内部接口 - 供 Payment-Service Feign 调用
 */
@RestController
@RequestMapping("/api/order/internal")
@RequiredArgsConstructor
public class InternalOrderController {

    private final OrderService orderService;

    @PutMapping("/status")
    public Result<Void> updateOrderStatus(@Valid @RequestBody UpdateOrderStatusRequest request) {
        orderService.updateOrderStatus(request.getOrderNo(), request.getStatus());
        return Result.success(null);
    }
}
