package com.shopcoupon.coupon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponTemplateRequest {

    @NotNull(message = "店铺ID不能为空")
    private Long shopId;

    @NotBlank(message = "优惠券名称不能为空")
    private String name;

    @NotBlank(message = "优惠券类型不能为空")
    private String type;

    private BigDecimal thresholdAmount;

    @NotNull(message = "优惠金额不能为空")
    private BigDecimal discountAmount;

    @NotNull(message = "发放总量不能为空")
    private Integer totalCount;

    @NotNull(message = "秒杀开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime seckillStartTime;

    @NotNull(message = "秒杀结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime seckillEndTime;

    private Integer validDays;

    private Integer perUserLimit;
}