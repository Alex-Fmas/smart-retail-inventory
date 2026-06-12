package com.retail.inventory.advice;

import com.retail.inventory.common.Result;
import com.retail.inventory.context.CurrentUserHolder;
import com.retail.inventory.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BizException.class)
    public Result handleException(BizException e) {
        System.out.println("全局异常处理" + "BizException" + e.getMessage());
        log.warn(
                "用户={} 业务异常={} ",
                CurrentUserHolder.getUsername(),
                e.getMessage()
        );
        return Result.error(e.getCode(), e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        System.out.println("全局异常处理" + "Exception" + e.getClass() + e.getMessage());
        log.error(
                "用户={} 系统异常",
                CurrentUserHolder.getUsername(),
                e
        );
        return Result.error(500, e.getMessage());
    }

}
