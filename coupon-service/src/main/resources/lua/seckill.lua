-- ============================================================
-- 秒杀抢券 Lua 脚本
-- KEYS[1] = 用户标记 key    seckill:user:{templateId}:{userId}
-- KEYS[2] = 库存 key        seckill:coupon:{templateId}:stock
-- 返回值: -3=库存未预热  -1=已领取  -2=库存不足  >=0=剩余库存
-- ============================================================

local userKey = KEYS[1]
local stockKey = KEYS[2]

-- 1. 检查库存是否已预热
local stock = redis.call('GET', stockKey)
if stock == false then
    return -3
end

-- 2. 检查用户是否已领取
if redis.call('EXISTS', userKey) == 1 then
    return -1
end

-- 3. 原子扣减库存
local remaining = redis.call('DECR', stockKey)
if remaining < 0 then
    redis.call('INCR', stockKey)
    return -2
end

-- 4. 标记用户已领取（24小时过期）
redis.call('SETEX', userKey, 86400, '1')

return remaining