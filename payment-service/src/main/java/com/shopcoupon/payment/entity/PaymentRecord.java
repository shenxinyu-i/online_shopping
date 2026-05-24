package com.shopcoupon.payment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("payment_record")
public class PaymentRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String paymentNo;

    private String orderNo;

    private Long userId;

    private BigDecimal amount;

    /** 支付方式: BALANCE/WECHAT/ALIPAY */
    private String paymentMethod;

    /** 状态: PENDING/SUCCESS/FAILED/REFUNDED */
    private String status;

    private LocalDateTime paidAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
