package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.Feedback;
import com.flong.springboot.modules.entity.dto.FeedbackDto;
import com.flong.springboot.modules.entity.vo.FeedbackVo;
import org.apache.ibatis.annotations.Param;


public interface FeedbackMapper extends BaseMapper<Feedback> {

    IPage<FeedbackVo> pageList(Page<Feedback> page, @Param("feedbackDto") FeedbackDto feedbackDto);

    FeedbackVo getOneById(@Param("id") int id);
}