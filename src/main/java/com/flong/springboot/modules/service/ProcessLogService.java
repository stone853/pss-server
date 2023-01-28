package com.flong.springboot.modules.service;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.modules.entity.ProcessLog;
import com.flong.springboot.modules.mapper.ProcessLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessLogService extends ServiceImpl<ProcessLogMapper, ProcessLog> {

        @Autowired
        ProcessLogMapper processLogMapper;




}
