package com.retail.inventory.service.impl;

import com.retail.inventory.entity.SysUser;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.mapper.SysUserMapper;
import com.retail.inventory.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Override
    public SysUser getSysUserById(Long id) {
        SysUser sysUserById = sysUserMapper.getSysUserById(id);
        return sysUserById;
    }

    @Override
    public SysUser getSysUserByName(String name) {
        SysUser sysUserByName = sysUserMapper.getByUsername(name);
        return sysUserByName;
    }

    @Override
    public Long addSysUser(SysUser sysUser) {
        int result = sysUserMapper.addSysUser(sysUser);
        Long id = sysUser.getId();
        return id;
    }

    @Override
    public int updateSysUser(SysUser sysUser) {
        int result = sysUserMapper.updateSysUser(sysUser);
        return result;
    }

    @Override
    public int deleteSysUser(Long id) {
        int result = sysUserMapper.deleteSysUser(id);
        return result;
    }

    @Override
    public List<SysUser> ListUser() {
        List<SysUser> list = sysUserMapper.ListUser();
        return list;
    }
}
