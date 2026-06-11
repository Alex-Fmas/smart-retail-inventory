package com.retail.inventory.service;

import com.retail.inventory.entity.SysRole;

import java.util.List;

public interface SysUserRoleService {
    /**
     * 添加用户角色关系
     * @param userId
     * @param roleId
     * @return
     */
    int insertUserRole(Long userId, Long roleId);

    /**
     * 删除用户角色关系
     * @param userId
     * @return
     */
    int deleteByUserId(Long userId);

    /**
     * 根据用户ID查询拥有的角色ID列表
     * @param userId
     * @return
     */
    List<Long> getRoleIdsByUserId(Long userId);

    /**
     * 根据用户ID查询角色列表
     * @param userId
     * @return
     */
    List<SysRole> getRolesByUserId(Long userId);

    /**
     * 更换员工角色
     * @param empId 员工ID
     * @param newRoleId 新岗位ID
     */
    int changeUserRole(Long empId, Long newRoleId);
}
