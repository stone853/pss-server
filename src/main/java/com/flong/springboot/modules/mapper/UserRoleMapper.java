package com.flong.springboot.modules.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.UserRole;
import org.springframework.stereotype.Component;

@Component
public interface UserRoleMapper extends BaseMapper<UserRole> {
    
}