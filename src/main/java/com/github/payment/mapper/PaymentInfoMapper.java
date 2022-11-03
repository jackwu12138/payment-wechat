package com.github.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.payment.databject.PaymentInfoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 支付信息的 mapper 层
 *
 * @author jackwu
 */
@Mapper
public interface PaymentInfoMapper extends BaseMapper<PaymentInfoDO> {
}
