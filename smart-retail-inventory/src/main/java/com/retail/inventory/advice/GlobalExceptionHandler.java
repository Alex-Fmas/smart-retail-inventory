package com.retail.inventory.advice;

import com.retail.inventory.common.Result;
import com.retail.inventory.exception.BizException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BizException.class)
    public Result handleException(BizException e) {
        System.out.println("全局异常处理" + "BizException" + e.getMessage());
        return Result.error(e.getCode(), e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        System.out.println("全局异常处理" + "Exception" + e.getClass() + e.getMessage());
        return Result.error(500, e.getMessage());
    }

}
