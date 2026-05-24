package com.shopcoupon.coupon.service.impl;

import com.shopcoupon.common.result.Result;
import com.shopcoupon.coupon.mapper.CouponTemplateMapper;
import com.shopcoupon.coupon.mapper.UserCouponMapper;
import com.shopcoupon.coupon.service.SeckillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeckillServiceImpl implements SeckillService {

    private final RedissonClient redissonClient;
    private final StringRedisTemplate redisTemplate;
    private final CouponTemplateMapper couponTemplateMapper;
    private final UserCouponMapper userCouponMapper;

    @Override
    public Result<String> seckillCoupon(Long templateId, Long userId) {
        // TODO: 1. 检查活动时间 seckillStartTime ~ seckillEndTime
        // TODO: 2. Redisson分布式锁防止同一用户重复抢券
        // TODO: 3. Redis检查用户是否已领取 seckill:user:{templateId}:{userId}
        // TODO: 4. Redis原子扣减库存 DECR seckill:coupon:{templateId}:stock
        // TODO: 5. 标记用户已领取
        // TODO: 6. 异步写入 user_coupon 表
        String lockKey = "seckill:lock:" + templateId + ":" + userId;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (!lock.tryLock(2, 10, TimeUnit.SECONDS)) {
                return Result.fail("系统繁忙，请稍后重试");
            }
            return Result.fail("秒杀抢券核心逻辑待完善");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return Result.fail("系统异常");
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    @Async
    public void saveUserCouponAsync(Long templateId, Long userId) {
        // TODO: 异步保存用户优惠券到数据库
    }
}
