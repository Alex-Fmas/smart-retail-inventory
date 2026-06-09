package com.retail.inventory.vo.stock;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StockLogVO {
    private Long id;
    private Long productId;
    private String productName;
    private Integer fromArea;
    private String fromAreaName;
    private Integer toArea;
    private String toAreaName;
    private Integer quantity;
    private Integer type;
    private String typeName;
    private Long operatorId;
    private String operatorName;
    private LocalDateTime createTime;
}
