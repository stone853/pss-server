package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.OrderSend;
import com.flong.springboot.modules.entity.OrderSend;
import com.flong.springboot.modules.entity.dto.OrderSendDto;
import com.flong.springboot.modules.entity.dto.OrderSendMaterialDto;
import com.flong.springboot.modules.entity.vo.MaterialDetailSendOrderVo;
import com.flong.springboot.modules.entity.vo.MaterialDetailSendVo;
import com.flong.springboot.modules.entity.vo.OrderSendVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderSendMapper extends BaseMapper<OrderSend> {
    public IPage<OrderSendVo> pageList(Page<OrderSend> page, @Param("orderSendDto") OrderSendDto orderSendDto);

    OrderSendVo getOneById(@Param("id") int id);


    List<MaterialDetailSendOrderVo> getOrderMaterial(@Param("orderSendMaterialDto")OrderSendMaterialDto orderSendMaterialDto);


    List<MaterialDetailSendOrderVo> getSendMaterial(@Param("orderSendMaterialDto")OrderSendMaterialDto orderSendMaterialDto);
}