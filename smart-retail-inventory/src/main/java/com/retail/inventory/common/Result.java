package com.retail.inventory.common;

import com.retail.inventory.exception.BizExceptionEnum;
import lombok.Data;

@Data
public class Result<T> {
    private Integer code;
    private String message;
    private T data;
    // 成功方法
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("Successful operation");
        result.setData(data);
        return result;
    }
    // 错误方法
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(null);
        return result;
    }
    // 重载错误方法
    public static <T> Result<T> error(String message) {
        return error(500, message);
    }

    // 接收枚举的错误方法
    public static <T> Result<T> error(BizExceptionEnum bizExceptionEnum) {
        return error(bizExceptionEnum.getCode(), bizExceptionEnum.getMessage());
    }

}
