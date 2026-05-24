package com.shopcoupon.coupon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("coupon_template")
public class CouponTemplate {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long shopId;

    private String name;

    /** 类型: FULL_REDUCTION/DISCOUNT/FIXED */
    private String type;

    private BigDecimal thresholdAmount;

    private BigDecimal discountAmount;

    private Integer totalCount;

    private LocalDateTime seckillStartTime;

    private LocalDateTime seckillEndTime;

    private Integer validDays;

    private Integer perUserLimit;

    /** 状态: 1有效 0失效 */
    private Integer status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
