package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.CheckMaterial;
import com.flong.springboot.modules.entity.dto.CheckMaterialDto;
import com.flong.springboot.modules.entity.vo.CheckMaterialVo;
import org.apache.ibatis.annotations.Param;

public interface CheckMaterialMapper extends BaseMapper<CheckMaterial> {

    IPage<CheckMaterialVo> pageList(Page<CheckMaterial> page, @Param("checkMaterialDto") CheckMaterialDto checkMaterialDto);

    CheckMaterialVo getOneById(@Param("id") int id);

    CheckMaterialVo getOneByCode(@Param("billCode") String billCode);
}