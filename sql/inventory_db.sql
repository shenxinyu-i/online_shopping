CREATE DATABASE IF NOT EXISTS inventory_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE inventory_db;

CREATE TABLE `inventory` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `product_id` BIGINT NOT NULL UNIQUE COMMENT '商品ID',
    `total_stock` INT NOT NULL DEFAULT 0 COMMENT '总库存',
    `available_stock` INT NOT NULL DEFAULT 0 COMMENT '可用库存',
    `locked_stock` INT NOT NULL DEFAULT 0 COMMENT '锁定库存',
    `version` INT NOT NULL DEFAULT 0 COMMENT '乐观锁版本号',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存表';

CREATE TABLE `inventory_log` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `order_no` VARCHAR(50) NOT NULL COMMENT '关联订单号',
    `change_type` VARCHAR(20) NOT NULL COMMENT '变更类型: LOCK/CONFIRM/RELEASE',
    `change_count` INT NOT NULL COMMENT '变更数量',
    `before_available` INT NOT NULL COMMENT '变更前可用库存',
    `after_available` INT NOT NULL COMMENT '变更后可用库存',
    `before_locked` INT NOT NULL COMMENT '变更前锁定库存',
    `after_locked` INT NOT NULL COMMENT '变更后锁定库存',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_product_id` (`product_id`),
    INDEX `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存流水表';

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

INSERT INTO `inventory` VALUES
(1, 1, 1000, 1000, 0, 0, NOW(), NOW()),
(2, 2, 500, 500, 0, 0, NOW(), NOW()),
(3, 3, 800, 800, 0, 0, NOW(), NOW()),
(4, 4, 300, 300, 0, 0, NOW(), NOW()),
(5, 5, 600, 600, 0, 0, NOW(), NOW());
