package com.shopcoupon.coupon.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shopcoupon.coupon.entity.CouponTemplate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CouponTemplateMapper extends BaseMapper<CouponTemplate> {
    //数据库库存原子扣减
    @Update("UPDATE coupon_template SET total_count = total_count - 1 " +
            "WHERE id = #{templateId} AND total_count > 0")
    int decreaseStock(Long templateId);
}
