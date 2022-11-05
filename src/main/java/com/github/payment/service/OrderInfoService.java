package com.github.payment.service;

import com.github.payment.databject.OrderInfoDO;

/**
 * 订单信息的 service 接口
 *
 * @author jackwu
 */
public interface OrderInfoService {

    /**
     * 根据商品编号创建订单
     *
     * @param productId 商品编号
     * @return 创建好的订单信息
     */
    OrderInfoDO createOrderInfoById(Long productId);

    /**
     * 更新二维码链接
     *
     * @param orderInfo 要更新链接的二维码信息
     */
    void updateCodeUrl(OrderInfoDO orderInfo);
}
