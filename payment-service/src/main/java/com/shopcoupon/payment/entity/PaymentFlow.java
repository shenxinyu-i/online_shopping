package com.shopcoupon.payment.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("payment_flow")
public class PaymentFlow {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String paymentNo;

    private String orderNo;

    /** 流水类型: PAYMENT/REFUND */
    private String flowType;

    private BigDecimal amount;

    private String remark;

    private LocalDateTime createdAt;
}
