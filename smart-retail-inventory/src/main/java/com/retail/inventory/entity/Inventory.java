package com.retail.inventory.entity;

import lombok.Data;

@Data
public class Inventory {
    private Long id;
    private Long productId;
    private Integer areaType;
    private Integer quantity;
    private Integer minThreshold;
}
/**
 * DROP TABLE IF EXISTS `inventory`;
 * CREATE TABLE `inventory` (
 *                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '库存记录ID',
 *                              `product_id` bigint NOT NULL COMMENT '商品ID',
 *                              `area_type` tinyint NOT NULL COMMENT '区域类型（1: 前台货架, 2: 后台仓库）',
 *                              `quantity` int NOT NULL DEFAULT '0' COMMENT '当前库存数量',
 *                              `min_threshold` int DEFAULT '0' COMMENT '预警低限',
 *                              PRIMARY KEY (`id`),
 *                              UNIQUE KEY `uk_product_area` (`product_id`,`area_type`),
 *                              CONSTRAINT `fk_inv_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`) ON DELETE CASCADE
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='区域库存表';
 */
