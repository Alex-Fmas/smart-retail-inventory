package com.retail.inventory.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockChangeVO {
    private Long productId;
    private Integer fromArea;
    private Integer toArea;
    private Integer quantity;
    private Integer type;
    private Long operatorId;
    private LocalDateTime createTime;
}
