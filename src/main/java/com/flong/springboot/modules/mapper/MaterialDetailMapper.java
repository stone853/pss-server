package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.MaterialDetail;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.dto.ContractPurchaseDto;
import com.flong.springboot.modules.entity.dto.MaterialDetailDto;
import com.flong.springboot.modules.entity.dto.MaterialMgtDto;
import com.flong.springboot.modules.entity.vo.MaterialDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MaterialDetailMapper extends BaseMapper<MaterialDetail> {

    List<MaterialDetailVo> findAll(@Param("foreignCode") String foreignCode);

}