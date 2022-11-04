package com.github.common.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务逻辑错误抛出的 Exception
 *
 * @author jackwu
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PaymentException extends RuntimeException {

    /**
     * 错误代码
     */
    private String code;

    /**
     * 所抛出的错误的错误信息
     */
    private String msg;

    private PaymentException() {}
}
