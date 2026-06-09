package com.retail.inventory.service;

import com.retail.inventory.entity.SysRole;

import java.util.List;

public interface SysRoleService {
    /**
     * 获取角色
     * @param id
     * @return
     */
    SysRole getRoleById(Long id);

    /**
     * 添加角色
     * @param role
     * @return
     */
    Long addRole(SysRole role);
    /**
     * 更新角色
     * @param role
     * @return
     */
    int updateRole(SysRole role);
    /**
     * 删除角色
     * @param id
     * @return
     */
    int deleteRole(Long id);
    /**
     * 获取所有角色
     * @return
     */
    List<SysRole> listRoles();
}
