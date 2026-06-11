package com.retail.inventory.controller;

import com.retail.inventory.common.Result;
import com.retail.inventory.dto.login.LoginDTO;
import com.retail.inventory.service.authService;
import com.retail.inventory.vo.login.LoginVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    authService authService;

    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO dto) {
        return Result.success(authService.login(dto.getUsername(), dto.getPassword()));
    }
}
