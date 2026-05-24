CREATE DATABASE IF NOT EXISTS payment_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE payment_db;

CREATE TABLE `payment_record` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `payment_no` VARCHAR(50) NOT NULL UNIQUE COMMENT '支付单号',
    `order_no` VARCHAR(50) NOT NULL COMMENT '关联订单号',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '支付金额',
    `payment_method` VARCHAR(20) DEFAULT 'BALANCE' COMMENT '支付方式',
    `status` VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT '状态: PENDING/SUCCESS/FAILED/REFUNDED',
    `paid_at` DATETIME COMMENT '支付时间',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_order_no` (`order_no`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付记录表';

CREATE TABLE `payment_flow` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `payment_no` VARCHAR(50) NOT NULL COMMENT '支付单号',
    `order_no` VARCHAR(50) NOT NULL COMMENT '订单号',
    `flow_type` VARCHAR(20) NOT NULL COMMENT '流水类型: PAYMENT/REFUND',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '金额',
    `remark` VARCHAR(200) COMMENT '备注',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    INDEX `idx_payment_no` (`payment_no`),
    INDEX `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付流水表';

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
