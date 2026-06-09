package com.retail.inventory.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SysUser {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String phone;
    private Integer status;
    private LocalDateTime createTime;
}
/**
 * DROP TABLE IF EXISTS `sys_user`;
 * CREATE TABLE `sys_user` (
 *                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
 *                             `username` varchar(50) NOT NULL COMMENT '登录账号',
 *                             `password` varchar(100) NOT NULL COMMENT '加密后的密码',
 *                             `nickname` varchar(50) NOT NULL COMMENT '员工真实姓名/昵称',
 *                             `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
 *                             `status` tinyint NOT NULL DEFAULT '1' COMMENT '账号状态（1:启用, 0:禁用）',
 *                             `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
 *                             PRIMARY KEY (`id`),
 *                             UNIQUE KEY `uk_username` (`username`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';
 */
