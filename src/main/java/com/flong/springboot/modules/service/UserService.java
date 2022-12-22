package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.MD5Utils;
import com.flong.springboot.modules.entity.Role;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.dto.RoleDto;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
        @Autowired
    UserMapper userMapper;
    public List<User> find_AS_R() {
        return userMapper.find_AS_R();
    }

    public User getUserByUserId (String userId){
        QueryWrapper<User> qw = new QueryWrapper<User>();
        qw.eq("user_id",userId);
        return userMapper.selectOne(qw);
    }

    public int insert (User u) {
            return userMapper.insert(u.setPassword(MD5Utils.encrypt(u.getPassword())));
        }

    public IPage<User> page (UserDto userDto) {
        QueryWrapper<User> build = buildWrapper(userDto);
        return userMapper.selectPage(userDto.getPage()==null ? new Page<>() : userDto.getPage(),build);
    }

    private QueryWrapper<User> buildWrapper(UserDto userDto) {
        QueryWrapper<User> build = new QueryWrapper<>();
        if (userDto.getName() !=null && !"".equals(userDto.getName())) {
            build.eq("name",userDto.getName());
        }
        if (userDto.getIsDelete() !=null && !"".equals(userDto.getIsDelete())) {
            build.eq("is_delete",userDto.getIsDelete());
        }
        if (userDto.getMobile() !=null && !"".equals(userDto.getMobile())) {
            build.eq("mobile",userDto.getMobile());
        }

        return build;
    }
}
