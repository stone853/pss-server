package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.MaterialDetailSend;
import com.flong.springboot.modules.entity.vo.MaterialDetailSendVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface MaterialDetailSendMapper extends BaseMapper<MaterialDetailSend> {
    List<MaterialDetailSendVo> findAll(@Param("foreignCode") String foreignCode);

    List<MaterialDetailSendVo> getByOrderSendCode(@Param("foreignCode") String foreignCode);


}