package com.retail.inventory.entity;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Product {
    private Long id;
    private String barcode;
    private String name;
    private BigDecimal price;
    private String unit;
}
/**
 * DROP TABLE IF EXISTS `product`;
 * CREATE TABLE `product` (
 *                            `id` bigint NOT NULL AUTO_INCREMENT COMMENT '商品ID',
 *                            `barcode` varchar(50) NOT NULL COMMENT '商品条形码',
 *                            `name` varchar(100) NOT NULL COMMENT '商品名称',
 *                            `price` decimal(10,2) NOT NULL COMMENT '售价',
 *                            `unit` varchar(10) DEFAULT NULL COMMENT '单位',
 *                            PRIMARY KEY (`id`),
 *                            UNIQUE KEY `uk_barcode` (`barcode`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='商品信息表';
 */
