package com.shopcoupon.coupon.service.impl;

import com.shopcoupon.coupon.dto.CouponTemplateRequest;
import com.shopcoupon.coupon.entity.CouponTemplate;
import com.shopcoupon.coupon.mapper.CouponTemplateMapper;
import com.shopcoupon.coupon.service.CouponTemplateService;
import com.shopcoupon.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponTemplateServiceImpl implements CouponTemplateService {

    private final CouponTemplateMapper couponTemplateMapper;
    private final StringRedisTemplate redisTemplate;

    @Override
    public CouponTemplate createTemplate(CouponTemplateRequest request) {
        // TODO: 校验商家权限，创建优惠券模板
        throw new BusinessException("创建优惠券模板功能待完善");
    }

    @Override
    public CouponTemplate updateTemplate(Long templateId, CouponTemplateRequest request) {
        // TODO: 更新模板信息
        throw new BusinessException("更新优惠券模板功能待完善");
    }

    @Override
    public CouponTemplate getTemplateById(Long templateId) {
        CouponTemplate template = couponTemplateMapper.selectById(templateId);
        if (template == null) {
            throw new BusinessException(404, "优惠券活动不存在");
        }
        return template;
    }

    @Override
    public List<CouponTemplate> listTemplates(Long shopId) {
        // TODO: 按店铺查询优惠券活动列表
        throw new BusinessException("优惠券列表功能待完善");
    }

    @Override
    public void warmupStock(Long templateId) {
        // TODO: 活动开始前将 total_count 预热到 Redis
        // SET seckill:coupon:{templateId}:stock = total_count
        throw new BusinessException("库存预热功能待完善");
    }
}
