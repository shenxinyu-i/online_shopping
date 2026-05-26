package com.shopcoupon.coupon.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopcoupon.coupon.dto.CouponTemplateRequest;
import com.shopcoupon.coupon.entity.CouponTemplate;
import com.shopcoupon.coupon.mapper.CouponTemplateMapper;
import com.shopcoupon.coupon.service.CouponTemplateService;
import com.shopcoupon.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.baomidou.mybatisplus.extension.toolkit.Db.save;
@Slf4j
@Service
@RequiredArgsConstructor
public class CouponTemplateServiceImpl extends ServiceImpl<CouponTemplateMapper,CouponTemplate> implements CouponTemplateService {

    private final CouponTemplateMapper couponTemplateMapper;
    private final StringRedisTemplate redisTemplate;

    /**
     * 商户创建优惠券
     * @param request
     * @return
     */
    @Override
    public CouponTemplate createTemplate(CouponTemplateRequest request) {
        //1检查优惠券是否在有效期内
        if(request.getSeckillStartTime().isBefore(LocalDateTime.now()))
        {
            throw new BusinessException("优惠券开始时间必须大于当前时间");
        }
        if(request.getSeckillEndTime().isBefore(LocalDateTime.now()))
        {
            throw new BusinessException("优惠券结束时间必须大于当前时间");
        }
        if(request.getTotalCount()<0)
        {
            throw new BusinessException("优惠券数量必须大于零");
        }
        //2 创建优惠券
        CouponTemplate template =new CouponTemplate();
        BeanUtil.copyProperties(request,template);
        template.setStatus(1);
        boolean isSaved = save(template);//继承mybatisplus方法保存到数据库
        if(!isSaved)
        {
            throw new BusinessException("创建订单失败");
        }
        log.info("创建优惠券活动成功: id={}, name={}, 总量={}",
                template.getId(), template.getName(), template.getTotalCount());
        return template;

    }

    /**
     * 商户更新优惠券
     * @param templateId
     * @param request
     * @return
     */
    @Override
    public CouponTemplate updateTemplate(Long templateId, CouponTemplateRequest request) {
        CouponTemplate template=getById(templateId);
        if(template==null)
        {
            throw new BusinessException("该优惠券活动不存在");
        }
        if(request.getSeckillStartTime().isBefore(LocalDateTime.now()))
        {
            throw new BusinessException("该优惠券活动已开始不准修改");

        }
        BeanUtil.copyProperties(request,template);
        template.setId(templateId);
        boolean isUpdated = updateById(template);
        if(!isUpdated)
        {
            throw new BusinessException("优惠券更新失败");
        }
        log.info("优惠券信息更新成功 id={}",templateId);
        return template;
    }

    /**
     * 根据优惠券id查询优惠券
     * @param templateId
     * @return
     */
    @Override
    public CouponTemplate getTemplateById(Long templateId) {
        CouponTemplate template = couponTemplateMapper.selectById(templateId);
        if (template == null) {
            throw new BusinessException(404, "优惠券活动不存在");
        }
        return template;
    }

    /**
     * 根据店铺id查询其所有的优惠券
     * @param shopId
     * @return
     */
    @Override
    public List<CouponTemplate> listTemplates(Long shopId) {
        LambdaQueryWrapper<CouponTemplate> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(CouponTemplate::getShopId,shopId)
                .orderByDesc(CouponTemplate::getCreatedAt);
        return list(wrapper);
    }

    /**
     * 商家优惠券预热
     * @param templateId
     */
    @Override
    public void warmupStock(Long templateId) {
        //1查询优惠券是否存在
        CouponTemplate template = getById(templateId);
        if(template==null)
        {
            throw new BusinessException("该优惠券活动不存在");
        }

        //检验活动状态
        if(template.getStatus()!=1)
        {
            throw new BusinessException("该活动过期无法预热");
        }
        //3将优惠券库存数量预热到redis中
        String stockKey="seckill:coupon:" + templateId + ":stock";
        redisTemplate.opsForValue().set(stockKey,String.valueOf(template.getTotalCount()));
        //4标记活动已经开始
        String startKey="seckill:coupon:" + templateId + ":start";
        redisTemplate.opsForValue().set(startKey,"true");
        log.info("库存预热成功: templateId={}, stock={}", templateId, template.getTotalCount());
    }
}
