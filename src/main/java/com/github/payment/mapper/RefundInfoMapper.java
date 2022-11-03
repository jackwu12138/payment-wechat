package com.github.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.payment.databject.RefundInfoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 退款信息的 mapper 层
 *
 * @author jackwu
 */
@Mapper
public interface RefundInfoMapper extends BaseMapper<RefundInfoDO> {
}
