package com.retail.inventory.vo.inventory;

import lombok.Data;

@Data
public class InventoryVO {
    private Long id;
    private Long productId;
    private String productName;
    private Integer areaType;
    private String areaTypeName;
    private Integer quantity;
    private Integer minThreshold;
}
