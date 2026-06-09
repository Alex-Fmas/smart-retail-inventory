package com.retail.inventory.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductAddDTO {
    @NotBlank(message = "条形码不能为空")
    private String barcode;
    
    @NotBlank(message = "商品名称不能为空")
    private String name;
    
    @DecimalMin(value = "0.01", message = "价格必须大于0")
    private BigDecimal price;
    
    private String unit;
}
