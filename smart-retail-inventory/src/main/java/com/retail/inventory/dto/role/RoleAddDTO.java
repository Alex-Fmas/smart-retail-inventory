package com.retail.inventory.dto.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class RoleAddDTO {
    @NotBlank(message = "角色名称不能为空")
    private String roleName;
    
    private String description;
}
