package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.dto.IndexDataDto;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.entity.vo.IndexDataVo;
import com.flong.springboot.modules.entity.vo.TodoTaskVo;
import com.flong.springboot.modules.entity.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    List<User> find_AS_R();

    IPage<UserVo> findUserRoles(Page<User> page,@Param("user")UserDto userDto);

    UserVo findOneUserRoles(@Param("user")UserDto userDto);

    IndexDataVo selectIndexData(@Param("indexDataDto") IndexDataDto indexDataDto);

    List<TodoTaskVo> todoTask(@Param("userId")String userId);
}
