package com.shopcoupon.coupon.service.impl;

import com.shopcoupon.coupon.entity.UserCoupon;
import com.shopcoupon.coupon.mapper.UserCouponMapper;
import com.shopcoupon.coupon.service.UserCouponService;
import com.shopcoupon.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCouponServiceImpl implements UserCouponService {

    private final UserCouponMapper userCouponMapper;

    @Override
    public List<UserCoupon> listMyCoupons(Long userId, String status) {
        // TODO: 查询用户券包，支持按状态过滤，短TTL缓存
        throw new BusinessException("我的券包功能待完善");
    }

    @Override
    public void useCoupon(Long couponId, Long userId) {
        // TODO: 校验券归属、状态、有效期，更新为 USED（Seata AT模式参与全局事务）
        throw new BusinessException("优惠券核销功能待完善");
    }

    @Override
    public void restoreCoupon(Long couponId, Long userId) {
        // TODO: 订单取消时将券状态恢复为 UNUSED
        throw new BusinessException("优惠券退还功能待完善");
    }
}
