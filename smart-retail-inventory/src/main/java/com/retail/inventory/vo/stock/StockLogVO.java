package com.retail.inventory.vo.stock;

import com.retail.inventory.entity.StockLog;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StockLogVO {
    private Long id;
    private Long productId;
    //private String productName; // 商品名称
    private Integer fromArea;
    private String fromAreaName;    // 区域名称
    private Integer toArea;
    private String toAreaName;      // 区域名称
    private Integer quantity;
    private Integer type;
    private String typeName;        // 变动类型
    private Long operatorId;
    //private String operatorName;      暂定不返回
    private LocalDateTime createTime;

    public StockLogVO setStockLog(StockLog stockLog) {
        this.id = stockLog.getId();
        this.productId = stockLog.getProductId();
        this.fromArea = stockLog.getFromArea();
        // 1:货架, 2:仓库, 0:外部入库
        switch (stockLog.getFromArea()) {
            case 1 -> this.fromAreaName = "货架";
            case 2 -> this.fromAreaName = "仓库";
            case 0 -> this.fromAreaName = "外部入库";
            default -> this.fromAreaName = "未知";
        }
        this.toArea = stockLog.getToArea();
        // 1:货架, 2:仓库, 0:销售出库/报损
        switch (stockLog.getToArea()) {
            case 1 -> this.toAreaName = "货架";
            case 2 -> this.toAreaName = "仓库";
            case 0 -> this.toAreaName = "销售出库/报损";
            default -> this.toAreaName = "未知";
        }
        this.quantity = stockLog.getQuantity();
        this.type = stockLog.getType();
        // 1:销售, 2:调拨, 3:采购入库, 4:盘点损益
        switch (stockLog.getType()) {
            case 1 -> this.typeName = "销售";
            case 2 -> this.typeName = "调拨";
            case 3 -> this.typeName = "采购入库";
            case 4 -> this.typeName = "盘点损益";
            default -> this.typeName = "未知";
        }
        this.operatorId = stockLog.getOperatorId();
        this.createTime = stockLog.getCreateTime();
        return this;
    }
}
