package com.github.common.core.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * json 工具类
 *
 * @author jackwu
 */
@Slf4j
public class JsonUtil {

    /**
     * 将 bean 转换为下划线格式的 json 字符串
     *
     * @param param 要转换的 bean
     * @return json 字符串
     */
    public static String toUnderlineCaseJson(Object param) {
        JSONObject json = JSONUtil.createObj();
        Map<String, Object> bean = BeanUtil.beanToMap(param, true, false);
        for (Map.Entry<String, Object> entry : bean.entrySet()) {
            json.set(entry.getKey(), entry.getValue());
        }
        return json.toString();
    }
}
