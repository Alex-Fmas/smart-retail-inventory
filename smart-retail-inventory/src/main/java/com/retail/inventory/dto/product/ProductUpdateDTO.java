package com.retail.inventory.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateDTO {
    @NotNull(message = "商品ID不能为空")
    private Long id;
    
    private String name;
    
    @DecimalMin(value = "0.01", message = "价格必须大于0")
    private BigDecimal price;
    
    private String unit;
}
