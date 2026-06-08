package com.retail.inventory.entity;

import lombok.Data;

@Data
public class SysRole {
    private Long id;
    private String roleName;
    private String description;
}
/**
 * DROP TABLE IF EXISTS `sys_role`;
 * CREATE TABLE `sys_role` (
 *                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
 *                             `role_name` varchar(50) NOT NULL COMMENT '角色名称(如: MANAGER, STAFF)',
 *                             `description` varchar(100) DEFAULT NULL COMMENT '角色描述',
 *                             PRIMARY KEY (`id`),
 *                             UNIQUE KEY `uk_role_name` (`role_name`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色表';
 */
