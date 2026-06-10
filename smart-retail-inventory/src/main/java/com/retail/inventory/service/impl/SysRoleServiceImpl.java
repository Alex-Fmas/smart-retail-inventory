package com.retail.inventory.service.impl;

import com.retail.inventory.entity.SysRole;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.mapper.SysRoleMapper;
import com.retail.inventory.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public SysRole getRoleById(Long id) {
        SysRole roleById = sysRoleMapper.getRoleById(id);
        return roleById;
    }

    @Override
    public Long addRole(SysRole role) {
        int result = sysRoleMapper.addRole(role);
        Long id = role.getId();
        return id;
    }

    @Override
    public int updateRole(SysRole role) {
        int result = sysRoleMapper.updateRole(role);
        return result;
    }

    @Override
    public int deleteRole(Long id) {
        int result = sysRoleMapper.deleteRole(id);
        return result;
    }

    @Override
    public List<SysRole> listRoles() {
        List<SysRole> listRoles = sysRoleMapper.listRoles();
        return listRoles;
    }
}
