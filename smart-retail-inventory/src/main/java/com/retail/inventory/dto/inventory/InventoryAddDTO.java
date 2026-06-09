package com.retail.inventory.dto.inventory;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class InventoryAddDTO {
    @NotNull(message = "商品ID不能为空")
    private Long productId;
    
    @NotNull(message = "区域类型不能为空")
    private Integer areaType;
    
    @Min(value = 0, message = "库存数量不能小于0")
    private Integer quantity;
    
    @Min(value = 0, message = "预警阈值不能小于0")
    private Integer minThreshold;
}
