package com.retail.inventory.dto.inventory;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
public class InventoryUpdateDTO {
    @NotNull(message = "库存ID不能为空")
    private Long id;
    
    @Min(value = 0, message = "库存数量不能小于0")
    private Integer quantity;
    
    @Min(value = 0, message = "预警阈值不能小于0")
    private Integer minThreshold;
}
