package com.retail.inventory.vo.product;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductVO {
    private Long id;
    private String barcode;
    private String name;
    private BigDecimal price;
    private String unit;
}
