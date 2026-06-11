package com.retail.inventory.enums;

import lombok.Getter;

/**
 * 库存变动类型
 */
@Getter
public enum StockChangeTypeEnum {
    SALE(1, "销售"),
    MOVE(2, "调拨"),
    PURCHASE(3, "采购入库"),
    LOSS(4, "盘点损益");



    private final Integer code;
    private final String desc;

    StockChangeTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 根据code获取枚举
    public static StockChangeTypeEnum getByCode(Integer code) {
        for (StockChangeTypeEnum e : values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }
}