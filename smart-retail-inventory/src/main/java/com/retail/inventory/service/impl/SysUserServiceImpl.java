package com.retail.inventory.service.impl;

import com.retail.inventory.entity.SysUser;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.mapper.SysUserMapper;
import com.retail.inventory.service.SysUserService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class SysUserServiceImpl implements SysUserService {
    private static final String DEFAULT_PASSWORD = "123456";

    @Autowired
    SysUserMapper sysUserMapper;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

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
        sysUser.setPassword(passwordEncoder.encode(sysUser.getPassword()));
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
    public List<SysUser> listUser() {
        List<SysUser> list = sysUserMapper.ListUser();
        return list;
    }

    @Override
    public int deleteLogicSysUser(Long id) {
        SysUser sysUserById = sysUserMapper.getSysUserById(id);
        if (sysUserById == null) {
            throw new BizException(BizExceptionEnum.USER_NOT_EXIST);
        }
        SysUser sysUser = new SysUser();
        sysUser.setId(id);
        sysUser.setStatus(0);
        int result = sysUserMapper.updateSysUser(sysUser);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(SysUser sysUser, String newPassword) {
        SysUser sysUserById = sysUserMapper.getSysUserById(sysUser.getId());
        if (sysUserById == null) {
            throw new BizException(BizExceptionEnum.USER_NOT_EXIST);
        }
        String inputPassword = sysUser.getPassword();

        boolean matches = passwordEncoder.matches(inputPassword, sysUserById.getPassword());

        if (!matches) {
            throw new BizException(BizExceptionEnum.USER_PASSWORD_ERROR);
        }

        sysUser.setPassword(passwordEncoder.encode(newPassword));
        sysUserMapper.updateSysUser(sysUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(SysUser sysUser) {
        SysUser sysUserById = sysUserMapper.getSysUserById(sysUser.getId());
        if (sysUserById == null) {
            throw new BizException(BizExceptionEnum.USER_NOT_EXIST);
        }
        sysUser.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        sysUserMapper.updateSysUser(sysUser);
    }
}
