package com.github.dependences.web.core.handler;

import com.github.common.core.exception.PaymentException;
import com.github.common.core.vo.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author jackwu
 */
@Slf4j
@RestControllerAdvice
public class PaymentExceptionHandler {

    /**
     * 处理业务异常 ServiceException
     * <p>
     * 例如说，商品库存不足，用户手机号已存在。
     */
    @ExceptionHandler(PaymentException.class)
    public CommonResult<?> serviceExceptionHandler(PaymentException ex) {
        log.info("[serviceExceptionHandler] {} -- {}", ex.getCode(), ex.getMsg());
        return CommonResult.error(ex.getCode(), ex.getMsg());
    }

    /**
     * 兜底处理所有异常
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResult<?> defaultExceptionHandler(Throwable ex) {
        log.error("[defaultExceptionHandler]", ex);
        return CommonResult.error("ERROR", "未知错误");
    }
}
