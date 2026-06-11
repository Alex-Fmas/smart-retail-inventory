package com.retail.inventory.dto.userRole;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRoleUpdateDTO {
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @NotNull(message = "角色ID不能为空")
    private Long roleId;
}
