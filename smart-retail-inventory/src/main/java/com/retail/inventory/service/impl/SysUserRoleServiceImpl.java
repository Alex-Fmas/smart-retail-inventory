package com.retail.inventory.service.impl;

import com.retail.inventory.entity.SysRole;
import com.retail.inventory.entity.SysUser;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.mapper.SysRoleMapper;
import com.retail.inventory.mapper.SysUserMapper;
import com.retail.inventory.mapper.SysUserRoleMapper;
import com.retail.inventory.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysRoleMapper sysRoleMapper;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int changeUserRole(Long empId, Long newRoleId) {
        SysUser sysUserById = sysUserMapper.getSysUserById(empId);
        if (sysUserById == null) {
            throw new BizException(BizExceptionEnum.USER_NOT_EXIST);
        }
        if (sysUserById.getStatus() == 0) {
            throw new BizException(BizExceptionEnum.USER_NOT_ACTIVE);
        }
        if (sysRoleMapper.getRoleById(newRoleId) == null) {
            throw new BizException(BizExceptionEnum.ROLE_NOT_EXIST);
        }
        int deleteResult = deleteByUserId(empId);
        if (deleteResult <= 0) {
            throw new BizException(BizExceptionEnum.USER_ROLE_NOT_EXIST);
        }
        int insertResult = insertUserRole(empId, newRoleId);
        return insertResult;
    }
}
