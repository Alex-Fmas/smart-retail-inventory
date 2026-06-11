package com.retail.inventory.service.impl;

import com.retail.inventory.entity.SysRole;
import com.retail.inventory.entity.SysUser;
import com.retail.inventory.exception.BizException;
import com.retail.inventory.exception.BizExceptionEnum;
import com.retail.inventory.mapper.SysUserMapper;
import com.retail.inventory.service.SysUserRoleService;
import com.retail.inventory.service.authService;
import com.retail.inventory.utils.JwtUtil;
import com.retail.inventory.vo.login.LoginVO;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class authServiceImpl implements authService {
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysUserRoleService sysUserRoleService;
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

        String token = jwtUtil.generateToken(byUsername.getId(), byUsername.getUsername());
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
