package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.ContractSale;
import com.flong.springboot.modules.entity.dto.ContractSaleDto;
import com.flong.springboot.modules.entity.vo.ContractSaleVo;
import org.apache.ibatis.annotations.Param;

public interface ContractSaleMapper extends BaseMapper<ContractSale> {
    public IPage<ContractSaleVo> pageList(Page<ContractSale> page, @Param("contractSale") ContractSaleDto contractSale);

}