package com.github.dependences.wechat.core.constants.wechat;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 退款状态的常量类
 *
 * @author jackwu
 */
public interface WxRefundStatusConstants {

    /**
     * 退款成功
     */
    String SUCCESS = "SUCCESS";

    /**
     * 退款关闭
     */
    String CLOSED = "CLOSED";

    /**
     * 退款处理中
     */
    String PROCESSING = "PROCESSING";

    /**
     * 退款异常
     */
    String ABNORMAL = "ABNORMAL";


}
