package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.MaterialDetail;
import com.flong.springboot.modules.entity.vo.MaterialDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialDetailMapper extends BaseMapper<MaterialDetail> {

    List<MaterialDetailVo> findAll(@Param("foreignCode") String foreignCode);

    List<MaterialDetailVo> findBySupplierCode(@Param("foreignCode") String foreignCode,@Param("supplierCode") String supplierCode);
}