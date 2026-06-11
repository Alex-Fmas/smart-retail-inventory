package com.retail.inventory.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserChangePasswordDTO {
    @NotNull(message = "用户ID不能为空")
    private Long id;
    @NotNull(message = "密码不能为空")
    private String password;
    @NotNull(message = "新密码不能为空")
    private String newPassword;
}
