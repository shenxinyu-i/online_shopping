package com.shopcoupon.shop.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ProductRequest {

    @NotNull(message = "店铺ID不能为空")
    private Long shopId;

    @NotBlank(message = "商品名称不能为空")
    private String name;

    private String description;

    @NotNull(message = "商品价格不能为空")
    private BigDecimal price;

    private String category;

    private String imageUrl;
}
