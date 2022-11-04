package com.github.payment.databject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.dependences.mybatisplus.core.BaseDO;
import lombok.*;

/**
 * 商品信息
 *
 * @author jackwu
 */
@Data
@Builder
@TableName("t_product")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductDO extends BaseDO {

    /**
     * 商品名称
     */
    private String title;

    /**
     * 价格（分）
     */
    private Integer price;
}
