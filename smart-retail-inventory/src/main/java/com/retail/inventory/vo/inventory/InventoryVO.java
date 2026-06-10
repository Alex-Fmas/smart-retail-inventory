package com.retail.inventory.vo.inventory;

import com.retail.inventory.entity.Inventory;
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

    public InventoryVO setInventory(Inventory inventory, String productName) {
        this.id = inventory.getId();
        this.productId = inventory.getProductId();
        this.productName = productName;
        this.areaType = inventory.getAreaType();
        // 1 前台货架 2 后台仓库
        switch (inventory.getAreaType()){
            case 1 -> this.areaTypeName = "前台货架";
            case 2 -> this.areaTypeName = "后台仓库";
            default -> this.areaTypeName = "未知";
        }
        this.quantity = inventory.getQuantity();
        this.minThreshold = inventory.getMinThreshold();
        return this;
    }
}
