package com.github.payment.controller.product;

import com.github.common.core.vo.CommonResult;
import com.github.payment.controller.product.vo.ProductResponseVO;
import com.github.payment.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.github.common.core.vo.CommonResult.success;

/**
 * 商品接口
 *
 * @author jackwu
 */
@Api(tags = "商品接口")
@Slf4j
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @ApiOperation("获取商品列表")
    @GetMapping("/list")
    public CommonResult<List<ProductResponseVO>> getProductList() {
        return success(service.getProductList());
    }
}
