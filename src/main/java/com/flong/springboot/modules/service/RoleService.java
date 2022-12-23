package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.MD5Utils;
import com.flong.springboot.modules.entity.Role;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.dto.RoleDto;
import com.flong.springboot.modules.mapper.RoleMapper;
import com.flong.springboot.modules.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {
        @Autowired
        RoleMapper roleMapper;

        public IPage<Role> page (RoleDto roleDto) {
                QueryWrapper<Role> build = buildWrapper(roleDto);
                return roleMapper.selectPage(roleDto.getPage()==null ? new Page<>() : roleDto.getPage(),build);
        }

        private QueryWrapper<Role> buildWrapper(RoleDto roleDto) {
                QueryWrapper<Role> build = new QueryWrapper<>();
                if (roleDto.getName() !=null && !"".equals(roleDto.getName())) {
                        build.eq("name",roleDto.getName());
                }
                if (roleDto.getIsDelete() !=null && !"".equals(roleDto.getIsDelete())) {
                        build.eq("is_delete",roleDto.getIsDelete());
                }
                if (roleDto.getId() !=null && !"".equals(roleDto.getId())) {
                        build.eq("id",roleDto.getId());
                }

                if (roleDto.getCreateUser() !=null && !"".equals(roleDto.getCreateUser())) {
                        build.eq("create_user",roleDto.getCreateUser());
                }
                return build;
        }


}