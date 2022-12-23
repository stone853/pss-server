package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.User;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> find_AS_R();

    List<User> findUserRoles(List<String> userIds);
}
