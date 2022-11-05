package com.github.payment.controller.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品信息的 response VO
 *
 * @author jackwu
 */
@ApiModel("商品接口 - 商品信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseVO {

    /**
     * 商品编号
     */
    @ApiModelProperty("商品编号")
    private Long id;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    private String title;

    /**
     * 价格（分）
     */
    @ApiModelProperty("价格(分)")
    private Integer price;
}
