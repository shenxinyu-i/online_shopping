package com.shopcoupon.inventory.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SetStockRequest {

    @NotNull(message = "商品ID不能为空")
    private Long productId;

    @NotNull(message = "库存数量不能为空")
    @Min(value = 0, message = "库存数量不能为负数")
    private Integer stock;
}
