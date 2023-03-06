package com.flong.springboot.modules.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.Req;
import com.flong.springboot.modules.entity.dto.ReqDto;
import com.flong.springboot.modules.entity.vo.ReqVo;
import org.apache.ibatis.annotations.Param;

public interface ReqMapper extends BaseMapper<Req> {
    IPage<ReqVo> pageList(Page<Req> page, @Param("req") ReqDto reqDto);

    ReqVo getOneById(@Param("id") int id);

    ReqVo getOneByCode(@Param("reqCode") String reqCode);

}