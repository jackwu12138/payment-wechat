package com.github.common.core.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.wall.violation.ErrorCode;
import com.github.common.core.exception.PaymentException;

/**
 * @author jackwu
 */
public class ExceptionUtil {
    public static PaymentException exception(String message) {
        return new PaymentException("ERROR", message);

    }

    public static PaymentException exception0(String message, Object... params) {
        return exception(StrUtil.format(message, params));
    }
}
