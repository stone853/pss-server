package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.EvaScheme;
import com.flong.springboot.modules.entity.dto.EvaSchemeDto;
import com.flong.springboot.modules.entity.vo.EvaSchemeVo;
import org.apache.ibatis.annotations.Param;

public interface EvaSchemeMapper extends BaseMapper<EvaScheme> {

    IPage<EvaSchemeVo> pageList(Page<EvaScheme> page, @Param("evaSchemeDto") EvaSchemeDto evaSchemeDto);
}