package com.flong.springboot.modules.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findAll();

    List<Menu> findMenuByUserId(@Param(value = "userId") String userId);


    List<Menu> findMenuByRoleCode(@Param(value = "roleCode") String roleCode);
}