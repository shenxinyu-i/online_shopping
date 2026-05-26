package com.shopcoupon.coupon.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shopcoupon.common.result.Result;
import com.shopcoupon.coupon.entity.CouponTemplate;
import com.shopcoupon.coupon.entity.UserCoupon;
import com.shopcoupon.coupon.mapper.CouponTemplateMapper;
import com.shopcoupon.coupon.mapper.UserCouponMapper;
import com.shopcoupon.coupon.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class SeckillServiceImpl extends ServiceImpl<CouponTemplateMapper, CouponTemplate>implements SeckillService {

    @Resource
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private UserCouponMapper userCouponMapper;

    /**
     * 从 resources/lua/seckill.lua 加载的秒杀脚本
     */
    private static final DefaultRedisScript<Long> SECKILL_LUA_SCRIPT;
    static {
        SECKILL_LUA_SCRIPT = new DefaultRedisScript<>();//redis脚本执行器
        SECKILL_LUA_SCRIPT.setLocation(new ClassPathResource("lua/seckill.lua"));
        SECKILL_LUA_SCRIPT.setResultType(Long.class);//将返回值转成long类型
    }


    /**
     * 1.查数据库活动检查是否在秒杀时间内
     * 2.获取分布式锁防止高并发，锁的颗粒度定位优惠券id+用户id，防止一个用户高并发的抢券导致一个用户抢多张券
     * 3.执行lua脚本将通过原子操作扣减库存保存用户领取信息到redis中防止一个用户抢多个券提示已经抢券
     * 4.异步写入数据库
     * @param templateId
     * @param userId
     * @return
     */
    @Override
    public Result<String> seckillCoupon(Long templateId, Long userId) {
        // 第1步：查询活动信息，校验活动时间
        CouponTemplate template = getById(templateId);
        if (template == null) {
            return Result.fail("优惠券活动不存在");
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(template.getSeckillStartTime())) {
            return Result.fail("秒杀活动尚未开始，开始时间: " + template.getSeckillStartTime());
        }
        if (now.isAfter(template.getSeckillEndTime())) {
            return Result.fail("秒杀活动已结束");
        }

        // 第2步：Redisson 分布式锁（兜底，防极端并发）
        //锁的颗粒度为优惠券id+用户id，A用户抢1券和B用户抢1券不用抢锁
        String lockKey = "seckill:lock:" + templateId + ":" + userId;
        //从redis中获取一个逻辑锁，对应redis中的一个key，getlock只是获取创建一个java对象还没有去redis中获取对象
        RLock lock = redissonClient.getLock(lockKey);

        // 向 Redis 执行：SET seckill:lock:1:100 xxx NX EX 10
        try {
            if (!lock.tryLock(2, 10, TimeUnit.SECONDS)) {
                log.warn("用户{}获取锁失败，可能重复提交", userId);
                return Result.fail("系统繁忙，请稍后重试");
            }

            // 第3步：执行 Lua 脚本（原子操作）
            String userKey = "seckill:user:" + templateId + ":" + userId;
            String stockKey = "seckill:coupon:" + templateId + ":stock";

            Long result = redisTemplate.execute(
                    SECKILL_LUA_SCRIPT,
                    Arrays.asList(userKey, stockKey)
            );

            // 第4步：根据返回值处理结果
            if (result == -3) {
                return Result.fail("活动库存未就绪，请联系管理员预热");
            }
            if (result == -1) {
                return Result.fail("您已领取过该优惠券，每人限领1张");
            }
            if (result == -2) {
                log.info("优惠券已抢光: templateId={}, userId={}", templateId, userId);
                return Result.fail("优惠券已被抢光");
            }

            // 第5步：抢券成功，异步写库（包含同步数据库库存）
            saveUserCouponAsync(templateId, userId, template);

            log.info("抢券成功: templateId={}, userId={}, 剩余库存={}", templateId, userId, result);
            return Result.success("抢券成功！");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("抢券被中断: templateId={}, userId={}", templateId, userId, e);
            return Result.fail("系统异常，请稍后重试");
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 异步保存用户优惠券到数据库，并同步扣减数据库库存
     */
    @Async
    public void saveUserCouponAsync(Long templateId, Long userId, CouponTemplate template) {
        try {
            // 1. 保存用户领券记录到 user_coupon 表
            UserCoupon userCoupon = new UserCoupon();
            userCoupon.setUserId(userId);
            userCoupon.setTemplateId(templateId);
            userCoupon.setCouponCode(IdUtil.getSnowflakeNextIdStr());
            userCoupon.setStatus("UNUSED");
            userCoupon.setAcquiredAt(LocalDateTime.now());
            userCoupon.setExpireAt(LocalDateTime.now().plusDays(template.getValidDays()));
            userCouponMapper.insert(userCoupon);

            // 2. 同步扣减数据库库存（原子操作）
            baseMapper.decreaseStock(templateId);

            log.info("领券成功并同步库存: userId={}, templateId={}, couponCode={}",
                    userId, templateId, userCoupon.getCouponCode());
        } catch (Exception e) {
            log.error("异步写库失败: templateId={}, userId={}", templateId, userId, e);
        }
    }
}