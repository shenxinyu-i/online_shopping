package com.shopcoupon.payment.feign.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDTO {

    private Long id;

    private String orderNo;

    private Long userId;

    private Long shopId;

    private Long productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer quantity;

    private BigDecimal originalAmount;

    private BigDecimal discountAmount;

    private BigDecimal finalAmount;

    private Long couponId;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
