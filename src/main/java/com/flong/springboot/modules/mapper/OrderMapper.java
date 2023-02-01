package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.Order;
import com.flong.springboot.modules.entity.dto.OrderDto;
import com.flong.springboot.modules.entity.vo.OrderVo;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper extends BaseMapper<Order> {
    IPage<OrderVo> pagePurList(Page<Order> page, @Param("orderDto") OrderDto orderDto);

    IPage<OrderVo> pageOutList(Page<Order> page, @Param("orderDto") OrderDto orderDto);

    OrderVo getOneById(@Param("id") int id);
}