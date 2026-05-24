package com.shopcoupon.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("order_info")
public class OrderInfo {

    @TableId(type = IdType.AUTO)
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

    /** 状态: PENDING/PAID/CANCELLED/COMPLETED */
    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
