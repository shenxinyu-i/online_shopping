package com.shopcoupon.order.feign.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private Long id;

    private Long shopId;

    private String name;

    private String description;

    private BigDecimal price;

    private String category;

    private String imageUrl;

    private Integer status;
}
