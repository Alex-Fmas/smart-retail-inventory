package com.retail.inventory.entity;

import lombok.Data;

@Data
public class SysUserRole {
    private Long userId;
    private Long roleId;
}
/**
 * DROP TABLE IF EXISTS `sys_user_role`;
 * CREATE TABLE `sys_user_role` (
 *                                  `user_id` bigint NOT NULL COMMENT '用户ID',
 *                                  `role_id` bigint NOT NULL COMMENT '角色ID',
 *                                  PRIMARY KEY (`user_id`,`role_id`),
 *                                  KEY `fk_role_id` (`role_id`),
 *                                  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE,
 *                                  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`) ON DELETE CASCADE
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户-角色关联表';
 */