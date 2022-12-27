package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.Dept;
import com.flong.springboot.modules.entity.DictTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DictTreeMapper extends BaseMapper<DictTree> {
    public int getMaxCode(@Param("type") String type);
}