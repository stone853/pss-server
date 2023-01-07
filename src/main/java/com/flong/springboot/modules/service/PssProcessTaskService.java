package com.flong.springboot.modules.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.modules.entity.PssProcessTask;
import com.flong.springboot.modules.mapper.PssProcessMapper;
import com.flong.springboot.modules.mapper.PssProcessTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PssProcessTaskService extends ServiceImpl<PssProcessTaskMapper, PssProcessTask> {
        @Autowired
        PssProcessTaskMapper PssProcessTask;

}
