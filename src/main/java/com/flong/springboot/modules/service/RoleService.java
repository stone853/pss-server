package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.MD5Utils;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.Role;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.dto.RoleDto;
import com.flong.springboot.modules.entity.vo.RoleVo;
import com.flong.springboot.modules.mapper.RoleMapper;
import com.flong.springboot.modules.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {
        @Autowired
        RoleMapper roleMapper;

        public IPage<Role> page (RoleDto roleDto) {
                return roleMapper.pageList(roleDto.getPage()==null ? new Page<>() : roleDto.getPage(),roleDto);
        }

        public Role insert (Role t) {
                String roleName = t.getName();
                if (StringUtils.isEmpty(roleName)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"角色名称不能为空");
                }
                QueryWrapper<Role> q = new QueryWrapper<>();
                q.eq("name",t.getName());
                q.last("limit 1");
                Role r = this.getOne(q);
                if (r !=null) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"角色名称已存在");
                }

                t.setCode(GeneratorKeyUtil.getRoleNextId());
                t.setCreateTime(UserHelper.getDateTime());
                this.save(t);
                return t;
        }

        private QueryWrapper<Role> buildWrapper(RoleDto roleDto) {
                QueryWrapper<Role> build = new QueryWrapper<>();
                if (roleDto.getName() !=null && !"".equals(roleDto.getName())) {
                        build.like("name",roleDto.getName());
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


        public String getOneRoleByName (String roleName) {
                QueryWrapper<Role> q = new QueryWrapper<>();
                q.eq("name",roleName);
                q.last("limit 1");
                Role role = this.getOne(q);
                if (role == null) {
                        return "";
                }
                return role.getCode();
        }

}
