package com.shopcoupon.payment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePaymentRequest {

    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    private String paymentMethod;
}
