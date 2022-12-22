package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.modules.entity.RoleMenu;
import com.flong.springboot.modules.mapper.RoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleMenuService extends ServiceImpl<RoleMenuMapper, RoleMenu> {
        @Autowired
        RoleMenuMapper rolemenuMapper;



}
