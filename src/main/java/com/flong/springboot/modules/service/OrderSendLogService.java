package com.flong.springboot.modules.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.modules.entity.OrderSendLog;
import com.flong.springboot.modules.mapper.OrderSendLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderSendLogService extends ServiceImpl<OrderSendLogMapper, OrderSendLog> {
        @Autowired
        OrderSendLogMapper orderSendLogMapper;



}
