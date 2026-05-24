# shop-coupon-platform

基于 Spring Cloud Alibaba 的多店铺电商促销平台（微服务骨架工程）。

## 项目结构

```
shop-coupon-platform/
├── common/              # 公共模块（Result、异常处理、工具类）
├── gateway-service/     # 网关 (8080)
├── auth-service/        # 认证 (8081)
├── shop-service/        # 店铺商品 (8082)
├── coupon-service/      # 优惠券 (8083)
├── order-service/       # 订单 (8084)
├── inventory-service/   # 库存 (8085)
├── payment-service/     # 支付 (8086)
└── sql/                 # 数据库初始化脚本
```

## 技术栈

| 组件 | 版本 |
|------|------|
| Spring Boot | 2.7.18 |
| Spring Cloud Alibaba | 2021.0.5.0 |
| Nacos | 2.x |
| Seata | 1.6.x (AT模式) |
| Sentinel | 1.8.x |
| Redis + Redisson | 6.x / 3.x |
| MySQL | 8.0 |

## 快速开始

### 1. 初始化数据库

```bash
mysql -u root -p < sql/auth_db.sql
mysql -u root -p < sql/shop_db.sql
mysql -u root -p < sql/coupon_db.sql
mysql -u root -p < sql/order_db.sql
mysql -u root -p < sql/inventory_db.sql
mysql -u root -p < sql/payment_db.sql
```

### 2. 启动中间件

按顺序启动：MySQL → Redis → Nacos → Seata Server → Sentinel Dashboard (8858)

> 注意：Sentinel Dashboard 端口设为 **8858**，避免与 Gateway (8080) 冲突。

### 3. 编译项目

```bash
cd shop-coupon-platform
mvn clean install -DskipTests
```

### 4. 启动服务（建议顺序）

```bash
# 1. auth-service (8081)
# 2. shop-service (8082)
# 3. coupon-service (8083)
# 4. inventory-service (8085)
# 5. order-service (8084)
# 6. payment-service (8086)
# 7. gateway-service (8080)

cd auth-service && mvn spring-boot:run
```

### 5. 测试登录

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user01","password":"123456"}'
```

## 测试账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | 123456 |
| 商家 | merchant01 | 123456 |
| 用户 | user01 | 123456 |

## API 路由

| 路径前缀 | 目标服务 |
|----------|----------|
| /api/auth/** | auth-service |
| /api/shop/** | shop-service |
| /api/coupon/** | coupon-service |
| /api/order/** | order-service |
| /api/inventory/** | inventory-service |
| /api/payment/** | payment-service |

## 团队分工与待完善项

各服务 `ServiceImpl` 中标注了 `TODO` 的业务逻辑待各成员完善：

| 成员 | 服务 | 核心待完善功能 |
|------|------|----------------|
| 组长 | gateway-service, coupon-service | JWT白名单、Sentinel限流规则、秒杀抢券、Redis库存预热 |
| 成员A | auth-service | 用户注册、角色校验 |
| 成员B | shop-service | 店铺/商品CRUD、Cache-Aside缓存 |
| 成员C | order-service | 订单创建/取消、Seata全局事务 |
| 成员D | inventory-service | 乐观锁库存预扣/确认/回补 |
| 成员E | payment-service | 支付单创建、模拟支付、Seata全局事务 |

## 内部 Feign 接口

服务间直接调用（不经过网关）：

- `POST /api/coupon/internal/use` — 核销优惠券
- `POST /api/coupon/internal/restore` — 退还优惠券
- `PUT /api/order/internal/status` — 更新订单状态
- `POST /api/inventory/lock` — 预扣库存
- `POST /api/inventory/confirm` — 确认扣减
- `POST /api/inventory/release` — 释放库存

## Seata 配置

各参与分布式事务的服务已在 `application.yml` 中配置：

```yaml
seata:
  tx-service-group: shopcoupon-tx-group
  data-source-proxy-mode: AT
```

全局事务入口：
- `OrderServiceImpl.createOrder()` — 下单场景
- `PaymentServiceImpl.processPayment()` — 支付场景
