package com.retail.inventory.vo.login;

import lombok.Data;

import java.util.List;

@Data
public class LoginVO {

    private String token;

    private Long userId;

    private String username;

    private List<String> roles;
}
