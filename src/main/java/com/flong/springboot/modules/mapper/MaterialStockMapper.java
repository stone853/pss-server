package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.MaterialStock;

import java.util.List;

import com.flong.springboot.modules.entity.dto.MaterialStockDto;
import com.flong.springboot.modules.entity.vo.MaterialStockDetailVo;
import com.flong.springboot.modules.entity.vo.MaterialStockVo;
import org.apache.ibatis.annotations.Param;

public interface MaterialStockMapper  extends BaseMapper<MaterialStock> {
    List<MaterialStockVo> findAll(@Param("materialStockDto") MaterialStockDto materialStockDto);

    List<MaterialStockDetailVo> findStockDetail(@Param("materialStockDto") MaterialStockDto materialStockDto);
}