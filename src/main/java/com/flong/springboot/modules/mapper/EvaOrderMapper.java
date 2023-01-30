package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.EvaOrder;
import com.flong.springboot.modules.entity.dto.EvaOrderDto;
import com.flong.springboot.modules.entity.vo.EvaOrderVo;
import org.apache.ibatis.annotations.Param;

public interface EvaOrderMapper extends BaseMapper<EvaOrder> {
    IPage<EvaOrderVo> pageList(Page<EvaOrder> page, @Param("evaOrder") EvaOrderDto evaOrderDto);

    EvaOrderVo getOneById(@Param("id") int id);
}