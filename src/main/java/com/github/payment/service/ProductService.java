package com.github.payment.service;

import com.github.payment.controller.product.vo.ProductResponseVO;
import com.github.payment.databject.ProductDO;

import java.util.List;

/**
 * 商品信息的 service 接口
 *
 * @author jackwu
 */
public interface ProductService {

    /**
     * 获取商品信息列表
     *
     * @return 商品信息列表
     */
    List<ProductResponseVO> getProductList();

    /**
     * 根据商品编号获取商品信息
     *
     * @param productId 商品编号
     * @return 获取到的商品信息
     */
    ProductDO getProductById(Long productId);
}
