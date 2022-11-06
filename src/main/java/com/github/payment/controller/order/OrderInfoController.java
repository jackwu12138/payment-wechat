package com.github.payment.controller.order;

import com.github.common.core.vo.CommonResult;
import com.github.payment.controller.order.vo.OrderInfoResponseVO;
import com.github.payment.service.OrderInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.github.common.core.vo.CommonResult.success;

/**
 * @author jackwu
 */
@Api(tags = "订单接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/order-info")
public class OrderInfoController {

    private final OrderInfoService service;

    @ApiOperation("获取所有订单列表")
    @GetMapping("/list")
    private CommonResult<List<OrderInfoResponseVO>> list() {
        return success(service.getOrderInfoList());
    }

    @ApiOperation("查询订单状态")
    @GetMapping("query-order-status/{orderNo}")
    public CommonResult<String> queryOrderStatus(@PathVariable String orderNo) {
        String status = service.getOrderStatusByOrderNo(orderNo);
        return success(status);
    }
}
