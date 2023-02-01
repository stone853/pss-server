package com.flong.springboot.modules.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.modules.entity.ProcessLog;
import com.flong.springboot.modules.entity.Role;
import com.flong.springboot.modules.entity.dto.RoleDto;
import com.flong.springboot.modules.mapper.ProcessLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessLogService extends ServiceImpl<ProcessLogMapper, ProcessLog> {

        @Autowired
        ProcessLogMapper processLogMapper;


        public List<ProcessLog> findAll (String processId) {
                return processLogMapper.findAll(processId);
        }


}
