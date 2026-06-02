package com.shopcoupon.order.controller;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.order.dto.UpdateOrderStatusRequest;
import com.shopcoupon.order.entity.OrderInfo;
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

    /**
     * 内部查询订单详情（无需用户ID校验）
     */
    @GetMapping("/{orderNo}")
    public Result<OrderInfo> getOrderByNo(@PathVariable String orderNo) {
        return Result.success(orderService.getOrderByOrderNo(orderNo));
    }
}
