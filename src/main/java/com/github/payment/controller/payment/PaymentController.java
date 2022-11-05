package com.github.payment.controller.payment;

import com.github.common.core.vo.CommonResult;
import com.github.dependences.wechat.core.api.NativePayNotifyResponseDTO;
import com.github.payment.controller.payment.vo.PrepaymentResponseVO;
import com.github.payment.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.github.common.core.vo.CommonResult.success;

/**
 * 支付接口
 *
 * @author jackwu
 */

@Api(tags = "支付接口")
@Slf4j
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

    @ApiOperation("支付结果通知接口")
    @PostMapping("/native/notify")
    public ResponseEntity<NativePayNotifyResponseDTO> nativeNotify(HttpServletRequest request) {
        ResponseEntity<NativePayNotifyResponseDTO> responseEntity = service.nativePaymentNotify(request);
        log.info("返回给微信的响应信息 --- {}", responseEntity);
        return responseEntity;
    }
}
