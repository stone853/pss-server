package com.flong.springboot.modules.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.OrderSendLog;
import com.flong.springboot.modules.entity.vo.OrderSendLogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderSendLogMapper extends BaseMapper<OrderSendLog> {
    List<OrderSendLogVo> getByOrderSendCode(@Param("orderSendCode") String orderSendCode);
}