package com.github.payment.service;

import com.github.dependences.wechat.core.api.NativeQueryResponseDTO;
import com.github.payment.controller.order.vo.OrderInfoResponseVO;
import com.github.payment.databject.OrderInfoDO;

import java.util.List;

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

    /**
     * 付款成功后更新订单
     *
     * @param encryptedData 加密数据
     */
    void updateOrderWhenPaymentSuccess(NativeQueryResponseDTO encryptedData);

    /**
     * 根据订单号更新订单状态
     *
     * @param orderNo 订单号
     * @param status  要更新的状态
     */
    void updateStatusByOrderNo(String orderNo, String status);

    /**
     * 根据订单号查询订单
     *
     * @param orderNo 要查询的订单号
     * @return 查询到的订单
     */
    OrderInfoDO getOrderByOrderNo(String orderNo);

    /**
     * 找到所有超过超时时间未支付的订单
     *
     * @param timeout 超时时间
     * @return 所有找到的订单
     */
    List<OrderInfoDO> getNoPayOrderByDuration(int timeout);

    /**
     * 获取订单列表
     *
     * @return 所有的订单列表
     */
    List<OrderInfoResponseVO> getOrderInfoList();

    /**
     * 根据订单号获取订单状态
     *
     * @param orderNo 订单号
     * @return 查询到的订单状态
     */
    String getOrderStatusByOrderNo(String orderNo);
}
