package com.github.payment.task;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import com.github.common.core.constants.OrderStatusConstants;
import com.github.dependences.wechat.core.api.NativeQueryResponseDTO;
import com.github.dependences.wechat.core.constants.wechat.WxTradeStateConstants;
import com.github.dependences.wechat.core.service.NativePayService;
import com.github.payment.databject.OrderInfoDO;
import com.github.payment.service.OrderInfoService;
import com.github.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author jackwu
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentTask {

    private final OrderInfoService orderInfoService;

    private final PaymentService paymentService;

    private final NativePayService nativePayService;

    @Scheduled(cron = "0/30 * * * * ?")
    public void orderConfirm() {
        log.info("===> 开始执行定时查单任务");
        TimeInterval timer = DateUtil.timer();

        List<OrderInfoDO> orderList = orderInfoService.getNoPayOrderByDuration(5);
        if (orderList != null) {
            orderConfirm(orderList);
        }

        log.info("===> 执行定时查单任务结束, 花费[{}]ms", timer.intervalMs());
    }

    private void orderConfirm(List<OrderInfoDO> orderList) {
        log.warn("要进行查单的订单列表: 共计[{}]条", orderList.size());
        orderList.forEach(order -> {
            log.warn(">>> {}", order);
            String orderNo = order.getOrderNo();
            // 查询订单信息
            NativeQueryResponseDTO queryResponse = nativePayService.queryPaymentOrder(orderNo);
            // 获取到微信端的支付状态信息
            String tradeState = queryResponse.getTradeState();
            // 假如已经支付成功的话
            if (WxTradeStateConstants.SUCCESS.equals(tradeState)) {
                log.info("核实订单[{}]支付成功", orderNo);
                orderInfoService.updateOrderWhenPaymentSuccess(queryResponse);
                return;
            }
            // 如果是未支付的话
            if (WxTradeStateConstants.NOTPAY.equals(tradeState)) {
                log.info("核实订单[{}]未支付", orderNo);
                nativePayService.closePaymentOrder(orderNo);
                // 更新本地订单状态
                orderInfoService.updateStatusByOrderNo(orderNo, OrderStatusConstants.CLOSED);
            }
        });
    }
}
