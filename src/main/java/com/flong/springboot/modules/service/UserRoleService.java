package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.modules.entity.UserRole;
import com.flong.springboot.modules.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {
        @Autowired
        UserRoleMapper userRoleMapper;



}
