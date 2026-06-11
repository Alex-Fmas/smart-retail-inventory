package com.retail.inventory.exception;
import lombok.Getter;

@Getter
public enum BizExceptionEnum {

    // 通用异常
    PARAM_ERROR(400, "参数错误"),
    ID_NOT_NULL(400, "ID不能为空"),
    USERNAME_NOT_NULL(400, "用户名不能为空"),
    DATA_NOT_EXIST(404, "数据不存在"),

    // 权限异常
    USER_NOT_LOGIN(401, "用户未登录"),
    NO_PERMISSION(403, "没有权限"),

    // 业务异常
    USER_NOT_EXIST(1000, "用户不存在"),
    USER_NOT_ACTIVE(1001, "用户已失效"),
    USER_ROLE_NOT_EXIST(1002, "用户角色不存在"),
    ROLE_NOT_EXIST(1003, "角色不存在"),
    USER_PASSWORD_ERROR(1004, "用户密码错误"),

    PRODUCT_NOT_EXIST(1101, "商品不存在"),
    INVENTORY_NOT_EXIST(1102, "库存记录不存在"),
    STOCK_NOT_ENOUGH(1103, "库存不足"),
    STOCK_CHANGE_TYPE_ERROR(1104, "无效的库存变动类型"),
    STOCK_NOT_EXIST(1105, "库存记录不存在"),

    // 系统异常
    SYSTEM_ERROR(500, "服务器繁忙，请稍后再试");

    private final Integer code;
    private final String message;

    BizExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}