package com.github.payment.convert;

import com.github.payment.controller.order.vo.OrderInfoResponseVO;
import com.github.payment.databject.OrderInfoDO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author jackwu
 */
@Mapper(componentModel = "spring")
public interface OrderInfoConvert {

    /**
     * 将 OrderInfoDO 列表转换为 OrderInfoResponseVO 列表
     *
     * @param orderInfoList 要转换的 OrderInfoDO 列表
     * @return 转换后的 OrderInfoResponseVO 列表
     */
    List<OrderInfoResponseVO> convertList(List<OrderInfoDO> orderInfoList);
}
