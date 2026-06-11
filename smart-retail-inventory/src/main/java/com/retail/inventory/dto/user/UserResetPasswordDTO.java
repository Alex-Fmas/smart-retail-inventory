package com.retail.inventory.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserResetPasswordDTO {
    @NotNull(message = "用户ID不能为空")
    private Long id;
}
