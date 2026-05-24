package com.shopcoupon.inventory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("inventory_log")
public class InventoryLog {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long productId;

    private String orderNo;

    /** 变更类型: LOCK/CONFIRM/RELEASE */
    private String changeType;

    private Integer changeCount;

    private Integer beforeAvailable;

    private Integer afterAvailable;

    private Integer beforeLocked;

    private Integer afterLocked;

    private LocalDateTime createdAt;
}
