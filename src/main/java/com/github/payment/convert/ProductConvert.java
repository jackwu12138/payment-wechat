package com.github.payment.convert;

import com.github.payment.controller.product.vo.ProductResponseVO;
import com.github.payment.databject.ProductDO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author jackwu
 */
@Mapper(componentModel = "spring")
public interface ProductConvert {

    /**
     * 将 ProductDO 列表转换为 ProductResponseVO 列表
     *
     * @param productList 要转换的 ProductDO 列表
     * @return 转换后的 ProductResponseVO 列表
     */
    List<ProductResponseVO> convertList(List<ProductDO> productList);
}
