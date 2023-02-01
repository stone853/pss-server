package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.ContractPurchase;
import com.flong.springboot.modules.entity.Role;
import com.flong.springboot.modules.entity.dto.ContractPurchaseDto;
import com.flong.springboot.modules.entity.dto.RoleDto;
import com.flong.springboot.modules.entity.vo.ContractPurchaseVo;
import com.flong.springboot.modules.entity.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

public interface RoleMapper extends BaseMapper<Role> {
    IPage<Role> pageList(Page<Role> page, @Param("role") RoleDto roleDto);
}