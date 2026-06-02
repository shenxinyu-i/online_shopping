package com.shopcoupon.payment.feign.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseStockRequest {

    private Long productId;

    private Integer count;

    private String orderNo;
}
