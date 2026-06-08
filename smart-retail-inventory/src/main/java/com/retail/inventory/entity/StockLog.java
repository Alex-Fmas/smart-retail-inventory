package com.retail.inventory.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockLog {
    private Long id;
    private Long productId;
    private Integer fromArea;
    private Integer toArea;
    private Integer quantity;
    private Integer type;
    private Long operatorId;
    private LocalDateTime createTime;
}
/**
 * DROP TABLE IF EXISTS `stock_log`;
 * CREATE TABLE `stock_log` (
 *                              `id` bigint NOT NULL AUTO_INCREMENT COMMENT '流水ID',
 *                              `product_id` bigint NOT NULL COMMENT '商品ID',
 *                              `from_area` tinyint DEFAULT NULL COMMENT '源区域（1:货架, 2:仓库, 0:外部入库）',
 *                              `to_area` tinyint DEFAULT NULL COMMENT '目的区域（1:货架, 2:仓库, 0:销售出库/报损）',
 *                              `quantity` int NOT NULL COMMENT '变动数量',
 *                              `type` tinyint NOT NULL COMMENT '变动类型（1:销售, 2:调拨, 3:采购入库, 4:盘点损益）',
 *                              `operator_id` bigint NOT NULL COMMENT '操作人ID',
 *                              `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '变动时间',
 *                              PRIMARY KEY (`id`),
 *                              KEY `fk_log_product_id` (`product_id`),
 *                              KEY `fk_log_operator_id` (`operator_id`),
 *                              CONSTRAINT `fk_log_operator_id` FOREIGN KEY (`operator_id`) REFERENCES `sys_user` (`id`),
 *                              CONSTRAINT `fk_log_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='库存变动流水表';
 */