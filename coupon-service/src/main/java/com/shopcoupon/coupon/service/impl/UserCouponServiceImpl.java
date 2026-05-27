package com.shopcoupon.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopcoupon.coupon.entity.CouponTemplate;
import com.shopcoupon.coupon.entity.UserCoupon;
import com.shopcoupon.coupon.mapper.UserCouponMapper;
import com.shopcoupon.coupon.service.CouponTemplateService;
import com.shopcoupon.coupon.service.UserCouponService;
import com.shopcoupon.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserCouponServiceImpl
        extends ServiceImpl<UserCouponMapper, UserCoupon>
        implements UserCouponService {

    @Resource
    private CouponTemplateService templateServicel;
    @Override
    public List<UserCoupon> listMyCoupons(Long userId, String status) {
        LambdaQueryWrapper<UserCoupon> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserCoupon::getUserId, userId);

        if (status != null && !status.isEmpty()) {
            wrapper.eq(UserCoupon::getStatus, status);
        }

        wrapper.orderByDesc(UserCoupon::getAcquiredAt);
        return list(wrapper);
    }

    @Override
    public void useCoupon(Long couponId, Long userId) {
        // 1. 查优惠券
        UserCoupon coupon = getById(couponId);
        if (coupon == null) {
            throw new BusinessException("优惠券不存在");
        }

        // 2. 检查归属
        if (!userId.equals(coupon.getUserId())) {
            throw new BusinessException("该优惠券不属于您，无法使用");
        }

        // 3. 检查状态
        if ("USED".equals(coupon.getStatus())) {
            throw new BusinessException("该优惠券已使用，无法再次使用");
        }
        if ("EXPIRED".equals(coupon.getStatus())) {
            throw new BusinessException("该优惠券已过期，无法使用");
        }

        // 4. 检查有效期
        if (coupon.getExpireAt() != null && coupon.getExpireAt().isBefore(LocalDateTime.now())) {
            coupon.setStatus("EXPIRED");
            updateById(coupon);
            throw new BusinessException("该优惠券已过期，过期时间: " + coupon.getExpireAt());
        }

        // 5. 核销
        coupon.setStatus("USED");
        coupon.setUsedAt(LocalDateTime.now());

        boolean updated = updateById(coupon);
        if (!updated) {
            throw new BusinessException("优惠券核销失败，请重试");
        }

        log.info("优惠券核销成功: couponId={}, userId={}", couponId, userId);
    }

    @Override
    public void restoreCoupon(Long couponId, Long userId) {
        UserCoupon coupon = getById(couponId);
        if (coupon == null) {
            throw new BusinessException("优惠券不存在，无法退回");
        }
        if (!userId.equals(coupon.getUserId())) {
            throw new BusinessException("该优惠券不属于您，无法退回");
        }
        if (coupon.getExpireAt() != null && coupon.getExpireAt().isBefore(LocalDateTime.now())) {
            throw new BusinessException("该优惠券已过期，无法退回");
        }
        if (!"USED".equals(coupon.getStatus())) {
            throw new BusinessException("优惠券状态异常，当前状态: " + coupon.getStatus() + "，无法退回");
        }

        // 用 LambdaUpdateWrapper，显式设置 usedAt = null
        LambdaUpdateWrapper<UserCoupon> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(UserCoupon::getId, couponId)
                .set(UserCoupon::getStatus, "UNUSED")
                .set(UserCoupon::getUsedAt, null);

        boolean updated = update(wrapper);
        if (!updated) {
            throw new BusinessException("优惠券退回失败，请重试");
        }

        log.info("优惠券退回成功: couponId={}, userId={}", couponId, userId);
    }

    @Override
    public BigDecimal calculateDiscount(Long couponId, BigDecimal originAmount) {
        //1.检查用户是否拥有该优惠券
        UserCoupon coupon=getById(couponId);
        if(coupon==null)
        {
            throw new BusinessException("您未拥有该优惠券");
        }
        //2.检查该优惠券是否存在
        CouponTemplate template=templateServicel.getTemplateById(coupon.getTemplateId());
        if(template==null)
        {
            throw new BusinessException("该优惠券不存在");

        }
        //3.根据优惠券类型进行核销
        switch(template.getType())
        {
            case"FULL_REDUCTION":
                if(originAmount.compareTo(template.getThresholdAmount())<0)
                {
                    throw new BusinessException("未达到满减金额");
                }
                return template.getDiscountAmount();
            case"FIXED":
                return template.getDiscountAmount();
            case"DISCOUNT":
                return originAmount.multiply(BigDecimal.ONE.subtract(template.getDiscountAmount()));
            default:
                throw new BusinessException("未知类型");
        }
    }
}