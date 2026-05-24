CREATE DATABASE IF NOT EXISTS coupon_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE coupon_db;

CREATE TABLE `coupon_template` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `shop_id` BIGINT NOT NULL COMMENT '所属店铺ID',
    `name` VARCHAR(100) NOT NULL COMMENT '优惠券名称',
    `type` VARCHAR(20) NOT NULL COMMENT '类型: FULL_REDUCTION/DISCOUNT/FIXED',
    `threshold_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '使用门槛金额',
    `discount_amount` DECIMAL(10,2) NOT NULL COMMENT '优惠金额/折扣率',
    `total_count` INT NOT NULL COMMENT '发放总量',
    `seckill_start_time` DATETIME NOT NULL COMMENT '秒杀开始时间',
    `seckill_end_time` DATETIME NOT NULL COMMENT '秒杀结束时间',
    `valid_days` INT DEFAULT 7 COMMENT '领取后有效天数',
    `per_user_limit` INT DEFAULT 1 COMMENT '每人限领数量',
    `status` TINYINT DEFAULT 1 COMMENT '状态: 1有效 0失效',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_shop_id` (`shop_id`),
    INDEX `idx_status_time` (`status`, `seckill_start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券模板表';

CREATE TABLE `user_coupon` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `template_id` BIGINT NOT NULL COMMENT '优惠券模板ID',
    `coupon_code` VARCHAR(50) NOT NULL UNIQUE COMMENT '券码',
    `status` VARCHAR(20) NOT NULL DEFAULT 'UNUSED' COMMENT '状态: UNUSED/USED/EXPIRED',
    `acquired_at` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
    `used_at` DATETIME COMMENT '使用时间',
    `expire_at` DATETIME COMMENT '过期时间',
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_template_id` (`template_id`),
    INDEX `idx_user_status` (`user_id`, `status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户优惠券表';

CREATE TABLE `undo_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `branch_id` BIGINT NOT NULL,
    `xid` VARCHAR(100) NOT NULL,
    `context` VARCHAR(128) NOT NULL,
    `rollback_info` LONGBLOB NOT NULL,
    `log_status` INT NOT NULL,
    `log_created` DATETIME NOT NULL,
    `log_modified` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Seata AT模式回滚日志表';

INSERT INTO `coupon_template` VALUES
(1, 1, '双11满200减50', 'FULL_REDUCTION', 200.00, 50.00, 500, '2025-12-01 00:00:00', '2025-12-12 23:59:59', 7, 1, 1, NOW(), NOW()),
(2, 1, '新人立减30元', 'FIXED', 0.00, 30.00, 200, '2025-12-01 00:00:00', '2025-12-31 23:59:59', 15, 1, 1, NOW(), NOW()),
(3, 2, '全场9折券', 'DISCOUNT', 0.00, 0.90, 300, '2025-12-01 00:00:00', '2025-12-20 23:59:59', 7, 2, 1, NOW(), NOW());
