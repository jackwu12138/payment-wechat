package com.github.payment.service;

import com.github.payment.controller.product.vo.ProductResponseVO;

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
}
