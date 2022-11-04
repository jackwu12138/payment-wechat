package com.github.common.core.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果
 *
 * @author jackwu
 */
@Data
public class CommonResult<T> implements Serializable {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误提示，用户可阅读
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public static <T> CommonResult<T> success() {
        CommonResult<T> result = new CommonResult<>();
        result.code = "0";
        result.msg = "";
        return result;
    }

    public static <T> CommonResult<T> success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.code = "0";
        result.msg = "";
        result.data = data;
        return result;
    }

    public static <T> CommonResult<T> error() {
        CommonResult<T> result = new CommonResult<>();
        result.code = "500";
        result.msg = "error";
        return result;
    }

    public static <T> CommonResult<T> error(String code, String message) {
        CommonResult<T> result = new CommonResult<>();
        result.code = code;
        result.msg = message;
        return result;
    }
}
