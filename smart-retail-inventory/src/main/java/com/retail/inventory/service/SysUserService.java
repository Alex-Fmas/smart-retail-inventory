package com.retail.inventory.service;

import com.retail.inventory.entity.SysUser;

import java.util.List;

public interface SysUserService {
    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    SysUser getSysUserById(Long id);

    /**
     * 根据用户名查询用户
     * @param name
     * @return
     */
    SysUser getSysUserByName(String name);

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    Long addSysUser(SysUser sysUser);

    /**
     * 修改用户
     * @param sysUser
     * @return
     */
    int updateSysUser(SysUser sysUser);

    /**
     * 删除用户
     * @param id
     * @return
     */
    int deleteSysUser(Long id);

    /**
     * 查询所有用户
     * @return
     */
    List<SysUser> listUser();

    /**
     * 逻辑删除用户
     */
    int deleteLogicSysUser(Long id);

    /**
     * 更改密码
     */
    void changePassword(SysUser sysUser, String newPassword);

    /**
     * 重制密码
     */
    void resetPassword(SysUser sysUser);
}
