package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.Dept;

import java.util.List;

public interface DeptMapper extends BaseMapper<Dept> {
    List<Dept> findAll();
}