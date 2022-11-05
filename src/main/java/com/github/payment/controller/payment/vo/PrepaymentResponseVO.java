package com.github.payment.controller.payment.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jackwu
 */
@ApiModel("支付接口 - 支付信息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrepaymentResponseVO {

    /**
     * 二维码链接
     */
    @ApiModelProperty("二维码链接")
    private String codeUrl;

    /**
     * 订单号
     */
    @ApiModelProperty("订单号")
    private String orderNo;
}
