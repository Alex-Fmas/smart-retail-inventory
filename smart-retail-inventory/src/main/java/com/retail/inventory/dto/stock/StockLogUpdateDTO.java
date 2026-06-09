package com.retail.inventory.dto.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class StockLogUpdateDTO {
    @NotNull(message = "流水ID不能为空")
    private Long id;
    
    @Min(value = 1, message = "数量必须大于0")
    private Integer quantity;
}
