package com.github.payment.controller.payment;

import com.github.common.core.vo.CommonResult;
import com.github.payment.controller.payment.vo.PrepaymentResponseVO;
import com.github.payment.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.common.core.vo.CommonResult.success;

/**
 * 支付接口
 *
 * @author jackwu
 */
@Api(tags = "支付接口")
@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @ApiOperation("预支付并生成支付二维码")
    @PostMapping("/native/{productId}")
    public CommonResult<PrepaymentResponseVO> nativePrepayment(@PathVariable Long productId) {
        return success(service.nativePrepayment(productId));
    }
}
