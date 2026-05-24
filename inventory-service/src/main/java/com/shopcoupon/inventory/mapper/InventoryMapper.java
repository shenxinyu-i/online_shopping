package com.shopcoupon.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shopcoupon.inventory.entity.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface InventoryMapper extends BaseMapper<Inventory> {

    /**
     * 预扣库存（乐观锁）
     * TODO: 影响行数为0时需重试或抛异常
     */
    @Update("UPDATE inventory SET available_stock = available_stock - #{count}, " +
            "locked_stock = locked_stock + #{count}, version = version + 1 " +
            "WHERE product_id = #{productId} AND available_stock >= #{count} AND version = #{version}")
    int lockStock(@Param("productId") Long productId,
                  @Param("count") Integer count,
                  @Param("version") Integer version);

    /**
     * 确认扣减库存
     */
    @Update("UPDATE inventory SET locked_stock = locked_stock - #{count}, " +
            "total_stock = total_stock - #{count}, version = version + 1 " +
            "WHERE product_id = #{productId} AND locked_stock >= #{count} AND version = #{version}")
    int confirmDeduct(@Param("productId") Long productId,
                      @Param("count") Integer count,
                      @Param("version") Integer version);

    /**
     * 释放锁定库存
     */
    @Update("UPDATE inventory SET available_stock = available_stock + #{count}, " +
            "locked_stock = locked_stock - #{count}, version = version + 1 " +
            "WHERE product_id = #{productId} AND locked_stock >= #{count} AND version = #{version}")
    int releaseStock(@Param("productId") Long productId,
                       @Param("count") Integer count,
                       @Param("version") Integer version);
}
