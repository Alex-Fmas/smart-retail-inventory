package com.retail.inventory.dto.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class StockLogAddDTO {
    @NotNull(message = "商品ID不能为空")
    private Long productId;
    
    private Integer fromArea;
    
    private Integer toArea;
    
    @NotNull(message = "数量不能为空")
    @Min(value = 1, message = "数量必须大于0")
    private Integer quantity;
    
    @NotNull(message = "变动类型不能为空")
    private Integer type;
    
    @NotNull(message = "操作人ID不能为空")
    private Long operatorId;
}
