package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.MobileVerif;
import com.flong.springboot.modules.entity.vo.OrderCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MobileVerifMapper extends BaseMapper<MobileVerif> {
    MobileVerif isValidate(@Param("mobile") String mobile, @Param("verifCode") String verifCode);
}