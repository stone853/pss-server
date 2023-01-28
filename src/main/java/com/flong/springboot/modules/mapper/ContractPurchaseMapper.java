package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.ContractPurchase;

import com.flong.springboot.modules.entity.dto.ContractPurchaseDto;
import com.flong.springboot.modules.entity.vo.ContractPurchaseVo;
import org.apache.ibatis.annotations.Param;

public interface ContractPurchaseMapper extends BaseMapper<ContractPurchase> {
    IPage<ContractPurchaseVo> pageList(Page<ContractPurchase> page, @Param("contractPurchase") ContractPurchaseDto contractPurchase);

    ContractPurchaseVo getOneById(@Param("id") int id);

    ContractPurchaseVo getOneByCode(@Param("contractCode") String contractCode);
}