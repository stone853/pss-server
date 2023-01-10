package com.flong.springboot.modules.service;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.modules.entity.StatusButton;
import com.flong.springboot.modules.mapper.RoleMapper;
import com.flong.springboot.modules.mapper.StatusButtonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusButtonService extends ServiceImpl<StatusButtonMapper, StatusButton> {
        @Autowired
        StatusButtonMapper statusButtonMapper;




}
