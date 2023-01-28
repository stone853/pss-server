package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.EvaIndex;

import java.util.List;


public interface EvaIndexMapper extends BaseMapper<EvaIndex> {
     List<EvaIndex> findIndexList();

}