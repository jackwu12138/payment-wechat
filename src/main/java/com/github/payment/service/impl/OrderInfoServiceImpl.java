package com.github.payment.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.common.core.constants.OrderStatusConstants;
import com.github.common.core.util.NoUtil;
import com.github.dependences.wechat.core.api.NativeQueryResponseDTO;
import com.github.payment.databject.OrderInfoDO;
import com.github.payment.databject.ProductDO;
import com.github.payment.mapper.OrderInfoMapper;
import com.github.payment.service.OrderInfoService;
import com.github.payment.service.PaymentInfoService;
import com.github.payment.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

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

    private final PaymentInfoService paymentInfoService;

    private final ReentrantLock lock = new ReentrantLock();

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

    @Override
    public void updateOrderWhenPaymentSuccess(NativeQueryResponseDTO responseData) {
        if (lock.tryLock()) {
            try {
                // 判断订单状态
                String status = getOrderStatus(responseData.getOutTradeNo());
                log.info("订单状态: [{}]", status);
                if (ObjectUtil.isNull(status) || !StrUtil.equals(status, OrderStatusConstants.NOTPAY)) {
                    return;
                }
                // 更新订单状态
                String orderNo = responseData.getOutTradeNo();
                updateStatusByOrderNo(orderNo, OrderStatusConstants.SUCCESS);
                // 记录支付日志
                paymentInfoService.createPaymentInfo(responseData);
            } finally {
                lock.unlock();
            }
        }
    }

    @Override
    public void updateStatusByOrderNo(String orderNo, String status) {
        log.info("更新订单状态 ---> 订单号:[{}], 订单状态:[{}]", orderNo, status);
        LambdaQueryWrapper<OrderInfoDO> wrapper = new LambdaQueryWrapper<OrderInfoDO>()
                .eq(OrderInfoDO::getOrderNo, orderNo);
        OrderInfoDO entity = OrderInfoDO.builder().orderStatus(status).build();
        baseMapper.update(entity, wrapper);
    }

    @Override
    public OrderInfoDO getOrderByOrderNo(String orderNo) {
        LambdaQueryWrapper<OrderInfoDO> wrapper = new LambdaQueryWrapper<OrderInfoDO>()
                .eq(OrderInfoDO::getOrderNo, orderNo);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public List<OrderInfoDO> getNoPayOrderByDuration(int timeout) {
        Instant now = Instant.now().minus(Duration.ofMinutes(timeout));
        LambdaQueryWrapper<OrderInfoDO> wrapper = new LambdaQueryWrapper<OrderInfoDO>()
                .eq(OrderInfoDO::getOrderStatus, OrderStatusConstants.NOTPAY)
                .le(OrderInfoDO::getCreateTime, now);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 根据订单号获取订单状态
     *
     * @param orderNo 订单号
     * @return 订单状态
     */
    private String getOrderStatus(String orderNo) {
        LambdaQueryWrapper<OrderInfoDO> wrapper = new LambdaQueryWrapper<OrderInfoDO>()
                .eq(OrderInfoDO::getOrderNo, orderNo);
        OrderInfoDO orderInfo = baseMapper.selectOne(wrapper);
        if (ObjectUtil.isNull(orderInfo)) {
            return null;
        }
        return orderInfo.getOrderStatus();
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
