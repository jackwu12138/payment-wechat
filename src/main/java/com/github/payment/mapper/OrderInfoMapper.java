package com.github.payment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.payment.databject.OrderInfoDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品信息的 mapper 层
 *
 * @author jackwu
 */
@Mapper
public interface OrderInfoMapper extends BaseMapper<OrderInfoDO> {
}
