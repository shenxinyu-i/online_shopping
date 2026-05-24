package com.shopcoupon.coupon.service;

import com.shopcoupon.common.result.Result;

public interface SeckillService {

    /**
     * 秒杀抢券（Redisson分布式锁 + Redis原子扣减）
     */
    Result<String> seckillCoupon(Long templateId, Long userId);
}
