package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.ContractPurchase;
import com.flong.springboot.modules.entity.MaterialStock;

import java.util.List;

import com.flong.springboot.modules.entity.dto.MaterialStockDto;
import com.flong.springboot.modules.entity.dto.MaterialStockLogDto;
import com.flong.springboot.modules.entity.vo.ContractPurchaseVo;
import com.flong.springboot.modules.entity.vo.MaterialStockDetailVo;
import com.flong.springboot.modules.entity.vo.MaterialStockLogVo;
import com.flong.springboot.modules.entity.vo.MaterialStockVo;
import org.apache.ibatis.annotations.Param;

public interface MaterialStockMapper  extends BaseMapper<MaterialStock> {
    List<MaterialStockVo> findAll(@Param("materialStockDto") MaterialStockDto materialStockDto);
    IPage<MaterialStockDetailVo> pageList(Page<MaterialStock> page, @Param("materialStockDto") MaterialStockDto materialStockDto);

    MaterialStockDetailVo getOneByCode(@Param("materialCode") String materialCode);

    IPage<MaterialStockLogVo> pageMaterialLogList(Page<MaterialStockLogVo> page, @Param("materialStockLogDto") MaterialStockLogDto materialStockLogDto);
}
