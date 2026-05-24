package com.shopcoupon.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateOrderStatusRequest {

    @NotBlank(message = "订单号不能为空")
    private String orderNo;

    @NotBlank(message = "订单状态不能为空")
    private String status;
}
