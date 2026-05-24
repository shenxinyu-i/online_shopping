CREATE DATABASE IF NOT EXISTS shop_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE shop_db;

CREATE TABLE `shop` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL COMMENT '店铺名称',
    `owner_id` BIGINT NOT NULL COMMENT '店主用户ID',
    `logo_url` VARCHAR(500) COMMENT '店铺Logo',
    `description` VARCHAR(500) COMMENT '店铺描述',
    `status` TINYINT DEFAULT 1 COMMENT '状态: 1营业 0歇业',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_owner_id` (`owner_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='店铺表';

CREATE TABLE `product` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `shop_id` BIGINT NOT NULL COMMENT '所属店铺ID',
    `name` VARCHAR(200) NOT NULL COMMENT '商品名称',
    `description` TEXT COMMENT '商品描述',
    `price` DECIMAL(10,2) NOT NULL COMMENT '商品原价',
    `category` VARCHAR(50) COMMENT '商品分类',
    `image_url` VARCHAR(500) COMMENT '商品主图',
    `status` TINYINT DEFAULT 1 COMMENT '状态: 1上架 0下架',
    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_shop_id` (`shop_id`),
    INDEX `idx_category` (`category`),
    INDEX `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

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

INSERT INTO `shop` VALUES
(1, '数码旗舰店', 2, '/img/logo1.png', '主营各类数码产品，正品保障', 1, NOW(), NOW()),
(2, '品质生活馆', 3, '/img/logo2.png', '生活好物，提升生活品质', 1, NOW(), NOW());

INSERT INTO `product` VALUES
(1, 1, '无线降噪蓝牙耳机', '主动降噪，40小时续航，Hi-Fi音质', 299.00, '数码', '/img/earphone.jpg', 1, NOW(), NOW()),
(2, 1, '机械键盘RGB背光', 'Cherry青轴，全键无冲，RGB光效', 499.00, '数码', '/img/keyboard.jpg', 1, NOW(), NOW()),
(3, 1, '便携式充电宝20000mAh', '大容量，快充协议，轻薄便携', 159.00, '数码', '/img/powerbank.jpg', 1, NOW(), NOW()),
(4, 2, '保温杯500ml', '316不锈钢，24小时保温', 89.00, '生活', '/img/cup.jpg', 1, NOW(), NOW()),
(5, 2, '记忆棉颈椎枕', '人体工学设计，缓解颈椎压力', 129.00, '生活', '/img/pillow.jpg', 1, NOW(), NOW());
