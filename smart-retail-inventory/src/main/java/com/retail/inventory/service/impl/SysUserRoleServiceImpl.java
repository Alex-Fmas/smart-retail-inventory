package com.retail.inventory.service.impl;

import com.retail.inventory.entity.SysRole;
import com.retail.inventory.mapper.SysUserRoleMapper;
import com.retail.inventory.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    @Override
    public int insertUserRole(Long userId, Long roleId) {
        int result = sysUserRoleMapper.insertUserRole(userId, roleId);
        return result;
    }

    @Override
    public int deleteByUserId(Long userId) {
        int result = sysUserRoleMapper.deleteByUserId(userId);
        return result;
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        List<Long> roleIdsByUserId = sysUserRoleMapper.getRoleIdsByUserId(userId);
        return roleIdsByUserId;
    }

    @Override
    public List<SysRole> getRolesByUserId(Long userId) {
        List<SysRole> rolesByUserId = sysUserRoleMapper.getRolesByUserId(userId);
        return rolesByUserId;
    }
}
