package com.github.payment.service.impl;

import com.github.payment.controller.product.vo.ProductResponseVO;
import com.github.payment.convert.ProductConvert;
import com.github.payment.databject.ProductDO;
import com.github.payment.mapper.ProductMapper;
import com.github.payment.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品信息的 service 实现类
 *
 * @author jackwu
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductMapper baseMapper;

    private final ProductConvert convertor;

    @Override
    public List<ProductResponseVO> getProductList() {
        List<ProductDO> productList = baseMapper.selectList(null);

        return convertor.convertList(productList);
    }

    @Override
    public ProductDO getProductById(Long productId) {
        return baseMapper.selectById(productId);
    }
}
