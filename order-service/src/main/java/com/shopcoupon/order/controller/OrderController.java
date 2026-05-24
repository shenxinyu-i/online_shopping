package com.shopcoupon.order.controller;

import com.shopcoupon.common.constant.CommonConstants;
import com.shopcoupon.common.result.Result;
import com.shopcoupon.order.dto.CreateOrderRequest;
import com.shopcoupon.order.entity.OrderInfo;
import com.shopcoupon.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Result<String> createOrder(
            @RequestHeader(CommonConstants.USER_ID_HEADER) Long userId,
            @Valid @RequestBody CreateOrderRequest request) {
        return Result.success(orderService.createOrder(userId, request));
    }

    @GetMapping
    public Result<List<OrderInfo>> listOrders(
            @RequestHeader(CommonConstants.USER_ID_HEADER) Long userId,
            @RequestParam(required = false) String status) {
        return Result.success(orderService.listOrders(userId, status));
    }

    @GetMapping("/{orderNo}")
    public Result<OrderInfo> getOrderDetail(
            @RequestHeader(CommonConstants.USER_ID_HEADER) Long userId,
            @PathVariable String orderNo) {
        return Result.success(orderService.getOrderDetail(userId, orderNo));
    }

    @PostMapping("/{orderNo}/cancel")
    public Result<Void> cancelOrder(
            @RequestHeader(CommonConstants.USER_ID_HEADER) Long userId,
            @PathVariable String orderNo) {
        orderService.cancelOrder(userId, orderNo);
        return Result.success(null);
    }
}
