package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.MaterialMgt;
import com.flong.springboot.modules.entity.dto.MaterialMgtDto;
import com.flong.springboot.modules.entity.vo.MaterialMgtVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialMgtMapper extends BaseMapper<MaterialMgt> {
    public List<MaterialMgtVo> pageList(Page<MaterialMgt> page, @Param("materialMgt")MaterialMgtDto materialMgt);
}