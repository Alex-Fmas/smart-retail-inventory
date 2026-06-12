package com.retail.inventory.controller;

import com.retail.inventory.common.Result;
import com.retail.inventory.dto.login.LoginDTO;
import com.retail.inventory.service.AuthService;
import com.retail.inventory.vo.login.LoginVO;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO dto) {
        Result<LoginVO> success = Result.success(authService.login(dto.getUsername(), dto.getPassword()));
        log.info(
                "用户={} 登录成功",
                dto.getUsername()
        );
        return success;
    }
}
