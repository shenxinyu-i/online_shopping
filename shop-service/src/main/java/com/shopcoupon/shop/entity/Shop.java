package com.shopcoupon.shop.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("shop")
public class Shop {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private Long ownerId;

    private String logoUrl;

    private String description;

    /** 状态: 1营业 0歇业 */
    private Integer status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
