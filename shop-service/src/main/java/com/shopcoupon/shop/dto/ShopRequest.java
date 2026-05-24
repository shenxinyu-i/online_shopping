package com.shopcoupon.shop.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ShopRequest {

    @NotBlank(message = "店铺名称不能为空")
    private String name;

    private String logoUrl;

    private String description;
}
