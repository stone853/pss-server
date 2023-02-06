package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.MaterialDetailLog;

import java.util.List;

import com.flong.springboot.modules.entity.vo.MaterialDetailLogVo;
import org.apache.ibatis.annotations.Param;

public interface MaterialDetailLogMapper  extends BaseMapper<MaterialDetailLog> {

    List<MaterialDetailLogVo> findAll(@Param("foreignCode") String foreignCode);

    List<MaterialDetailLogVo> findRaw(@Param("foreignCode") String foreignCode);

}