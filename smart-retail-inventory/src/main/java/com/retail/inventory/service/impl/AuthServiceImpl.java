package com.retail.inventory.service.impl;

import com.retail.inventory.entity.SysRole;
import com.retail.inventory.entity.SysUser;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.mapper.SysRoleMapper;
import com.retail.inventory.mapper.SysUserMapper;
import com.retail.inventory.service.SysUserRoleService;
import com.retail.inventory.service.AuthService;
import com.retail.inventory.utils.JwtUtil;
import com.retail.inventory.vo.login.LoginVO;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysUserRoleService sysUserRoleService;
    @Autowired
    SysRoleMapper sysRoleMapper;
    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public LoginVO login(String username, String password) {
        SysUser byUsername = sysUserMapper.getByUsername(username);
        if (byUsername ==  null) {
            throw new BizException(BizExceptionEnum.USER_NOT_EXIST);
        }

        boolean matches = passwordEncoder.matches(password, byUsername.getPassword());

        if (!matches) {
            throw new BizException(BizExceptionEnum.USER_PASSWORD_ERROR);
        }

        List<String> roles = sysRoleMapper.listRoleNamesByUserId(byUsername.getId());

        String token = jwtUtil.generateToken(byUsername.getId(), byUsername.getUsername(), roles);
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setUserId(byUsername.getId());
        loginVO.setUsername(byUsername.getUsername());

        loginVO.setRoles(sysUserRoleService.getRolesByUserId(
                byUsername.getId()).stream()
                .map(SysRole::getRoleName)
                .toList());

        return loginVO;
    }
}
