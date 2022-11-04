package com.github.common.core.util;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.core.util.StrUtil;

/**
 * 生成编号的工具类
 *
 * @author jackwu
 */
public class NoUtil {

    /**
     * 雪花算法 Id 生成器
     */
    private static final SnowflakeGenerator GENERATOR = new SnowflakeGenerator();

    /**
     * 订单编号的模板
     */
    private static final String ORDER_TEMPLATE = "order_{}";

    /**
     * 退款编号的模板
     */
    private static final String REFUND_TEMPLATE = "refund_{}";

    /**
     * 获取订单编号
     *
     * @return 生成的随机订单编号
     */
    public static String getOrderNo() {
        return StrUtil.format(ORDER_TEMPLATE, GENERATOR.next());
    }

    /**
     * 获取退款单编号
     *
     * @return 生成的退款单编号
     */
    public static String getRefundNo() {
        return StrUtil.format(REFUND_TEMPLATE, GENERATOR.next());
    }
}
