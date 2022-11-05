package com.github.payment.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.common.core.constants.OrderStatusConstants;
import com.github.common.core.util.NoUtil;
import com.github.payment.databject.OrderInfoDO;
import com.github.payment.databject.ProductDO;
import com.github.payment.mapper.OrderInfoMapper;
import com.github.payment.service.OrderInfoService;
import com.github.payment.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.github.common.core.util.ExceptionUtil.exception;

/**
 * 商品信息的 service 实现类
 *
 * @author jackwu
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderInfoServiceImpl implements OrderInfoService {

    private final OrderInfoMapper baseMapper;

    private final ProductService productService;

    @Override
    public OrderInfoDO createOrderInfoById(Long productId) {

        // 根据编号获取到商品信息
        ProductDO productInfo = productService.getProductById(productId);
        if (ObjectUtil.isNull(productInfo)) {
            throw exception("商品编号不存在");
        }
        // 创建新的订单信息并插入数据库
        OrderInfoDO orderInfo = buildNewOrderInfo(productInfo);
        log.debug("创建好的订单信息: {}", orderInfo);

        baseMapper.insert(orderInfo);
        return orderInfo;
    }

    @Override
    public void updateCodeUrl(OrderInfoDO orderInfo) {
        log.debug("更新二维码链接 --- 订单号: [{}], 二维码链接: [{}] ",
                orderInfo.getOrderNo(), orderInfo.getCodeUrl());
        LambdaUpdateWrapper<OrderInfoDO> wrapper = new LambdaUpdateWrapper<OrderInfoDO>()
                .set(OrderInfoDO::getCodeUrl, orderInfo.getCodeUrl())
                .eq(OrderInfoDO::getOrderNo, orderInfo.getOrderNo());
        baseMapper.update(orderInfo, wrapper);
    }

    /**
     * 根据商品信息创建新的订单信息
     *
     * @param productInfo 商品信息
     * @return 创建好的订单信息
     */
    private OrderInfoDO buildNewOrderInfo(ProductDO productInfo) {
        return OrderInfoDO.builder()
                .productId(productInfo.getId())
                .title(productInfo.getTitle())
                .orderNo(NoUtil.getOrderNo())
                .totalFee(productInfo.getPrice())
                .orderStatus(OrderStatusConstants.NOTPAY)
                .userId(0L)
                .build();
    }
}
