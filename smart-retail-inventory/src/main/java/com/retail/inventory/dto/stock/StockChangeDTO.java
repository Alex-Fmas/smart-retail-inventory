package com.retail.inventory.dto.stock;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockChangeDTO {
    @NotNull(message = "商品ID不能为空")
    private Long productId;
    @NotNull(message = "来源区域不能为空")
    private Integer fromArea;
    @NotNull(message = "目标区域不能为空")
    private Integer toArea;
    @Min(value = 1, message = "变动数量必须大于0")
    private Integer quantity;
    @NotNull(message = "变动类型不能为空")
    private Integer type;
    @NotNull(message = "操作员ID不能为空")
    private Long operatorId;
    private LocalDateTime createTime;
}
