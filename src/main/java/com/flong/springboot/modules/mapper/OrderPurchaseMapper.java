package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.ContractPurchase;
import com.flong.springboot.modules.entity.OrderPurchase;
import com.flong.springboot.modules.entity.dto.ContractPurchaseDto;
import com.flong.springboot.modules.entity.dto.OrderPurchaseDto;
import com.flong.springboot.modules.entity.vo.ContractPurchaseVo;
import com.flong.springboot.modules.entity.vo.OrderPurchaseVo;
import org.apache.ibatis.annotations.Param;

public interface OrderPurchaseMapper extends BaseMapper<OrderPurchase> {
    public IPage<OrderPurchaseVo> pageList(Page<OrderPurchase> page, @Param("orderPurchaseDto") OrderPurchaseDto orderPurchaseDto);

    OrderPurchaseVo getOneById(@Param("id") int id);
}