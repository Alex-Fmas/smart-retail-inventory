package com.retail.inventory.enums;

public enum StockAreaTypeEnum {
    // 1:货架, 2:仓库, 0:外部入库
    SHELF(1, "货架"),
    WAREHOUSE(2, "仓库"),
    EXTERNAL_INPUT(0, "外部入库/销售/报损");

    private Integer code;
    private String desc;

    StockAreaTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
    public static StockAreaTypeEnum getByCode(Integer code) {
        for (StockAreaTypeEnum value : StockAreaTypeEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }
}
