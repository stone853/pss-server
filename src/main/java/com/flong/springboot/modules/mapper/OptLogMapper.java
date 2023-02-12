package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.OptLog;
import com.flong.springboot.modules.entity.dto.OptLogDto;
import com.flong.springboot.modules.entity.vo.CheckMaterialVo;
import com.flong.springboot.modules.entity.vo.OptLogVo;
import org.apache.ibatis.annotations.Param;


public interface OptLogMapper extends BaseMapper<OptLog> {

    IPage<CheckMaterialVo> pageList(Page<OptLogVo> page, @Param("optLogDto") OptLogDto optLogDto);
}