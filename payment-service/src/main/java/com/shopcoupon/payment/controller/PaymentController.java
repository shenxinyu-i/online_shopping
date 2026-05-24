package com.shopcoupon.payment.controller;

import com.shopcoupon.common.constant.CommonConstants;
import com.shopcoupon.common.result.Result;
import com.shopcoupon.payment.dto.CreatePaymentRequest;
import com.shopcoupon.payment.entity.PaymentRecord;
import com.shopcoupon.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public Result<PaymentRecord> createPayment(
            @RequestHeader(CommonConstants.USER_ID_HEADER) Long userId,
            @Valid @RequestBody CreatePaymentRequest request) {
        return Result.success(paymentService.createPayment(userId, request));
    }

    @PostMapping("/{paymentNo}/pay")
    public Result<Void> processPayment(@PathVariable String paymentNo) {
        paymentService.processPayment(paymentNo);
        return Result.success("支付成功", null);
    }

    @GetMapping("/{paymentNo}")
    public Result<PaymentRecord> getPaymentStatus(@PathVariable String paymentNo) {
        return Result.success(paymentService.getPaymentStatus(paymentNo));
    }

    @PostMapping("/{paymentNo}/refund")
    public Result<Void> refund(@PathVariable String paymentNo) {
        paymentService.refund(paymentNo);
        return Result.success(null);
    }
}
