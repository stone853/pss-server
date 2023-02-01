package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.ProcessLog;
import com.flong.springboot.modules.entity.dto.MaterialStockDto;
import com.flong.springboot.modules.entity.vo.MaterialStockVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ProcessLogMapper  extends BaseMapper<ProcessLog> {
    List<ProcessLog> findAll(@Param("processId") String processId);
}