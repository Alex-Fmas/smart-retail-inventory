package com.retail.inventory.dto.role;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RoleUpdateDTO {
    @NotNull(message = "角色ID不能为空")
    private Long id;
    
    private String roleName;
    
    private String description;
}
