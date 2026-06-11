package com.retail.inventory.service;

import com.retail.inventory.vo.login.LoginVO;

public interface AuthService {
    LoginVO login(String username, String password);
}
