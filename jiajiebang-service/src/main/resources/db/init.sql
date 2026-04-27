-- 家洁帮小程序数据库初始化脚本
-- 创建数据库
CREATE DATABASE IF NOT EXISTS jiajiebang DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE jiajiebang;

-- 1. 用户表
CREATE TABLE `users` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `openid` VARCHAR(64) NOT NULL COMMENT '微信OpenID',
    `union_id` VARCHAR(64) DEFAULT NULL COMMENT '微信UnionID',
    `nick_name` VARCHAR(100) DEFAULT NULL COMMENT '用户昵称',
    `avatar_url` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `gender` TINYINT DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_openid` (`openid`),
    KEY `idx_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 2. 服务分类表
CREATE TABLE `service_categories` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `name` VARCHAR(100) NOT NULL COMMENT '分类名称',
    `icon` VARCHAR(500) DEFAULT NULL COMMENT '分类图标URL',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '分类描述',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '排序号（数字越小越靠前）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_sort` (`sort`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务分类表';

-- 3. 服务表
CREATE TABLE `services` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '服务ID',
    `category_id` BIGINT NOT NULL COMMENT '分类ID',
    `name` VARCHAR(200) NOT NULL COMMENT '服务名称',
    `price` DECIMAL(10, 2) NOT NULL COMMENT '价格（元）',
    `unit` VARCHAR(20) DEFAULT '次' COMMENT '单位',
    `duration` VARCHAR(50) DEFAULT NULL COMMENT '服务时长',
    `image` VARCHAR(500) DEFAULT NULL COMMENT '服务图片URL',
    `rating` DECIMAL(3, 1) DEFAULT 0.0 COMMENT '评分（0-5分）',
    `order_count` INT NOT NULL DEFAULT 0 COMMENT '订单数量',
    `description` TEXT COMMENT '服务描述',
    `features` JSON DEFAULT NULL COMMENT '服务特色（JSON数组）',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-下架，1-上架',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_status` (`status`),
    KEY `idx_rating` (`rating`),
    KEY `idx_order_count` (`order_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务表';

-- 4. 服务提供者表（保洁师）
CREATE TABLE `service_providers` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '提供者ID',
    `name` VARCHAR(100) NOT NULL COMMENT '姓名',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `experience` INT DEFAULT 0 COMMENT '从业年限',
    `rating` DECIMAL(3, 1) DEFAULT 0.0 COMMENT '评分（0-5分）',
    `order_count` INT NOT NULL DEFAULT 0 COMMENT '服务订单数',
    `certificates` JSON DEFAULT NULL COMMENT '资质证书（JSON数组）',
    `specialties` JSON DEFAULT NULL COMMENT '擅长领域（JSON数组）',
    `introduction` TEXT COMMENT '个人简介',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '联系电话',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-休息，1-工作中',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_status` (`status`),
    KEY `idx_rating` (`rating`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务提供者表';

-- 5. 服务与提供者关联表
CREATE TABLE `service_provider_relation` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `service_id` BIGINT NOT NULL COMMENT '服务ID',
    `provider_id` BIGINT NOT NULL COMMENT '提供者ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_service_provider` (`service_id`, `provider_id`),
    KEY `idx_provider_id` (`provider_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务与提供者关联表';

-- 6. 地址表
CREATE TABLE `addresses` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '地址ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `name` VARCHAR(50) NOT NULL COMMENT '联系人姓名',
    `phone` VARCHAR(20) NOT NULL COMMENT '联系电话',
    `address` VARCHAR(500) NOT NULL COMMENT '详细地址',
    `is_default` TINYINT NOT NULL DEFAULT 0 COMMENT '是否默认地址：0-否，1-是',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_is_default` (`is_default`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='地址表';

-- 7. 订单表
CREATE TABLE `orders` (
    `id` VARCHAR(32) NOT NULL COMMENT '订单ID（ORD+日期+序号）',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `service_id` BIGINT NOT NULL COMMENT '服务ID',
    `service_name` VARCHAR(200) NOT NULL COMMENT '服务名称',
    `service_image` VARCHAR(500) DEFAULT NULL COMMENT '服务图片URL',
    `provider_id` BIGINT DEFAULT NULL COMMENT '提供者ID',
    `provider_name` VARCHAR(100) DEFAULT NULL COMMENT '提供者姓名',
    `provider_avatar` VARCHAR(500) DEFAULT NULL COMMENT '提供者头像URL',
    `service_time` DATETIME NOT NULL COMMENT '预约服务时间',
    `address_id` BIGINT DEFAULT NULL COMMENT '地址ID',
    `address` VARCHAR(500) NOT NULL COMMENT '服务地址',
    `price` DECIMAL(10, 2) NOT NULL COMMENT '订单金额（元）',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注信息',
    `status` VARCHAR(20) NOT NULL DEFAULT 'pending' COMMENT '订单状态：pending-待接单，accepted-待服务，serving-服务中，completed-已完成，cancelled-已取消',
    `cancel_reason` VARCHAR(500) DEFAULT NULL COMMENT '取消原因',
    `complete_time` DATETIME DEFAULT NULL COMMENT '完成时间',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_service_id` (`service_id`),
    KEY `idx_provider_id` (`provider_id`),
    KEY `idx_status` (`status`),
    KEY `idx_service_time` (`service_time`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 8. 评价表
CREATE TABLE `reviews` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评价ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `user_name` VARCHAR(100) DEFAULT NULL COMMENT '用户昵称',
    `user_avatar` VARCHAR(500) DEFAULT NULL COMMENT '用户头像URL',
    `order_id` VARCHAR(32) NOT NULL COMMENT '订单ID',
    `service_id` BIGINT NOT NULL COMMENT '服务ID',
    `service_name` VARCHAR(200) DEFAULT NULL COMMENT '服务名称',
    `provider_id` BIGINT NOT NULL COMMENT '提供者ID',
    `rating` TINYINT NOT NULL COMMENT '评分（1-5星）',
    `content` TEXT COMMENT '评价内容',
    `images` JSON DEFAULT NULL COMMENT '评价图片（JSON数组）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_order_id` (`order_id`),
    KEY `idx_service_id` (`service_id`),
    KEY `idx_provider_id` (`provider_id`),
    KEY `idx_rating` (`rating`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

-- 9. 优惠券表
CREATE TABLE `coupons` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '优惠券ID',
    `title` VARCHAR(200) NOT NULL COMMENT '优惠券标题',
    `discount` DECIMAL(10, 2) NOT NULL COMMENT '优惠金额（元）',
    `min_amount` DECIMAL(10, 2) NOT NULL DEFAULT 0 COMMENT '最低使用金额（元）',
    `total_count` INT NOT NULL DEFAULT 0 COMMENT '发放总量',
    `used_count` INT NOT NULL DEFAULT 0 COMMENT '已使用数量',
    `start_time` DATETIME NOT NULL COMMENT '有效期开始时间',
    `end_time` DATETIME NOT NULL COMMENT '有效期结束时间',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-停用，1-启用',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_status` (`status`),
    KEY `idx_end_time` (`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='优惠券表';

-- 10. 用户优惠券关联表
CREATE TABLE `user_coupons` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `coupon_id` BIGINT NOT NULL COMMENT '优惠券ID',
    `status` VARCHAR(20) NOT NULL DEFAULT 'unused' COMMENT '状态：unused-未使用，used-已使用，expired-已过期',
    `use_time` DATETIME DEFAULT NULL COMMENT '使用时间',
    `order_id` VARCHAR(32) DEFAULT NULL COMMENT '使用的订单ID',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '领取时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_coupon_id` (`coupon_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户优惠券关联表';

-- 11. 轮播图表
CREATE TABLE `banners` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '轮播图ID',
    `image` VARCHAR(500) NOT NULL COMMENT '轮播图URL',
    `link` VARCHAR(500) DEFAULT NULL COMMENT '跳转链接',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '排序号（数字越小越靠前）',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-停用，1-启用',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_sort` (`sort`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='轮播图表';

-- 插入测试数据

-- 插入服务分类数据
INSERT INTO `service_categories` (`name`, `icon`, `description`, `sort`) VALUES
('日常保洁', 'https://cdn.jiejiabang.com/icons/daily-cleaning.png', '家庭日常清洁打扫', 1),
('深度清洁', 'https://cdn.jiejiabang.com/icons/deep-cleaning.png', '全屋深度清洁护理', 2),
('开荒保洁', 'https://cdn.jiejiabang.com/icons/initial-cleaning.png', '新房开荒清洁', 3),
('家电清洗', 'https://cdn.jiejiabang.com/icons/appliance-cleaning.png', '空调、油烟机等家电清洗', 4),
('搬家服务', 'https://cdn.jiejiabang.com/icons/moving.png', '专业搬家服务', 5);

-- 插入服务数据
INSERT INTO `services` (`category_id`, `name`, `price`, `unit`, `duration`, `image`, `rating`, `order_count`, `description`, `features`, `status`) VALUES
(1, '日常保洁-2小时', 89.00, '次', '2小时', 'https://cdn.jiejiabang.com/services/daily-2h.jpg', 4.8, 1256, '适用于日常家庭清洁，包含客厅、卧室、厨房、卫生间的基础清洁', '["专业保洁师", "自带清洁工具", "不满意重做"]', 1),
(1, '日常保洁-4小时', 169.00, '次', '4小时', 'https://cdn.jiejiabang.com/services/daily-4h.jpg', 4.9, 2345, '适合大面积家庭清洁，全面覆盖各个房间', '["资深保洁师", "进口清洁剂", "全程保险"]', 1),
(2, '深度清洁-标准版', 299.00, '次', '6小时', 'https://cdn.jiejiabang.com/services/deep-standard.jpg', 4.7, 856, '全屋深度清洁，包括玻璃、橱柜内部等难以清洁的区域', '["高温蒸汽消毒", "除螨杀菌", "深度去污"]', 1),
(2, '深度清洁-豪华版', 499.00, '次', '8小时', 'https://cdn.jiejiabang.com/services/deep-luxury.jpg', 4.9, 567, '顶级深度清洁服务，包含家具保养和空气净化', '["全屋消毒", "家具保养", "空气净化", "专属团队"]', 1),
(3, '开荒保洁-小户型', 399.00, '次', '4小时', 'https://cdn.jiejiabang.com/services/initial-small.jpg', 4.6, 423, '适合80平米以下新房开荒清洁', '["专业工具", "无尘作业", "验收保障"]', 1),
(3, '开荒保洁-大户型', 699.00, '次', '8小时', 'https://cdn.jiejiabang.com/services/initial-large.jpg', 4.8, 312, '适合80平米以上新房开荒清洁', '["多人团队", "专业设备", "质量保证"]', 1),
(4, '空调清洗-挂机', 129.00, '台', '1小时', 'https://cdn.jiejiabang.com/services/ac-wall.jpg', 4.7, 1567, '壁挂式空调深度清洗消毒', '["高温蒸汽", "杀菌消毒", "延长寿命"]', 1),
(4, '油烟机清洗', 189.00, '台', '2小时', 'https://cdn.jiejiabang.com/services/cooker.jpg', 4.8, 1234, '油烟机深度拆洗，去除顽固油污', '["完全拆解", "强力去油", "恢复如新"]', 1);

-- 插入服务提供者数据
INSERT INTO `service_providers` (`name`, `avatar`, `experience`, `rating`, `order_count`, `certificates`, `specialties`, `introduction`, `phone`, `status`) VALUES
('李阿姨', 'https://cdn.jiejiabang.com/providers/li.jpg', 5, 4.9, 523, '["健康证", "保洁师资格证"]', '["日常保洁", "深度清洁"]', '从事家政行业5年，擅长厨房和卫生间深度清洁，工作认真细致。', '13800001111', 1),
('王师傅', 'https://cdn.jiejiabang.com/providers/wang.jpg', 8, 4.8, 867, '["健康证", "高级保洁师证", "家电清洗证"]', '["家电清洗", "开荒保洁"]', '8年家政服务经验，精通各类家电清洗和维护，技术过硬。', '13800002222', 1),
('张姐', 'https://cdn.jiejiabang.com/providers/zhang.jpg', 3, 4.7, 345, '["健康证", "保洁师资格证"]', '["日常保洁", "深度清洁"]', '热情周到，做事麻利，深受客户好评。', '13800003333', 1),
('陈阿姨', 'https://cdn.jiejiabang.com/providers/chen.jpg', 6, 4.9, 678, '["健康证", "高级保洁师证"]', '["开荒保洁", "深度清洁"]', '专注开荒保洁6年，经验丰富，服务质量有保障。', '13800004444', 1);

-- 插入服务与提供者关联数据
INSERT INTO `service_provider_relation` (`service_id`, `provider_id`) VALUES
(1, 1), (1, 3), (2, 1), (2, 3), (3, 1), (3, 4), (4, 4),
(5, 2), (5, 4), (6, 2), (6, 4), (7, 2), (8, 2);

-- 插入轮播图数据
INSERT INTO `banners` (`image`, `link`, `sort`, `status`) VALUES
('https://cdn.jiejiabang.com/banners/banner1.jpg', '/pages/service-list/index', 1, 1),
('https://cdn.jiejiabang.com/banners/banner2.jpg', '/pages/coupon/index', 2, 1),
('https://cdn.jiejiabang.com/banners/banner3.jpg', '/pages/new-user/index', 3, 1);

-- 插入优惠券数据
INSERT INTO `coupons` (`title`, `discount`, `min_amount`, `total_count`, `used_count`, `start_time`, `end_time`, `status`) VALUES
('新用户专享券', 20.00, 100.00, 1000, 234, '2026-04-01 00:00:00', '2026-05-31 23:59:59', 1),
('满减优惠券', 30.00, 200.00, 500, 156, '2026-04-01 00:00:00', '2026-05-15 23:59:59', 1),
('春季特惠券', 50.00, 300.00, 300, 89, '2026-04-20 00:00:00', '2026-04-30 23:59:59', 1);
