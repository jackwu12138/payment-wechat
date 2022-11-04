package com.github.payment.controller.product.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jackwu
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseVO {

    /**
     * 商品编号
     */
    private String id;

    /**
     * 商品名称
     */
    private String title;

    /**
     * 价格（分）
     */
    private Integer price;
}
