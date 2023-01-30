package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.MD5Utils;
import com.flong.springboot.core.exception.BaseException;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.MsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.Role;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.UserRole;
import com.flong.springboot.modules.entity.dto.LoginDto;
import com.flong.springboot.modules.entity.dto.RoleDto;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.entity.vo.UserVo;
import com.flong.springboot.modules.mapper.UserMapper;
import com.flong.springboot.modules.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserRoleService userRoleService;

    public List<User> find_AS_R() {
        return userMapper.find_AS_R();
    }

    public User getUserByUserId (String userId){
        QueryWrapper<User> qw = new QueryWrapper<User>();
        qw.eq("user_id",userId);
        return userMapper.selectOne(qw);
    }

    public User login (LoginDto loginDto){
        QueryWrapper<User> build = new QueryWrapper<User>();
        if (loginDto.getUserId() !=null && !"".equals(loginDto.getUserId())) {
            build.eq("user_id",loginDto.getUserId());
        }
        if (loginDto.getPwd() !=null && !"".equals(loginDto.getPwd())) {
            build.eq("password",loginDto.getPwd());
        }

        if (loginDto.getMobile() !=null && !"".equals(loginDto.getMobile())) {
            build.eq("mobile",loginDto.getMobile());
        }
        return userMapper.selectOne(build);
    }

    public User insert (User u) {
        QueryWrapper<User> q = new QueryWrapper<User>();
        q.eq("mobile",u.getMobile());
        User temp = userMapper.selectOne(q);
        if (temp !=null) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"手机号已存在");
        }

        String userType = u.getUserType();
        if (StringUtils.isEmpty(userType)) {
            u.setUserType("1"); //默认企业内部
        }

        QueryWrapper<User> q1 = new QueryWrapper<User>();
        q1.eq("name",u.getName());
        List<User> listTemp = userMapper.selectList(q);
        if (listTemp !=null && listTemp.size() > 0) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"用户名已存在");
        }

        String userId = GeneratorKeyUtil.getUserNextId();
        u.setUserId(userId).setPassword(MD5Utils.encrypt("123456"));
        userMapper.insert(u);

        String roleCodes = u.getRoleCodes();
        List<UserRole> listUserRole = new ArrayList<UserRole>();
        if (StringUtils.isNotEmpty(roleCodes)) {
            String[] array = roleCodes.split(";");
            if (array.length > 0) {
                for (int i = 0; i < array.length;i ++) {
                    listUserRole.add(new UserRole().setUserId(userId)
                            .setRoleCode(array[i]));
                }
            }
        }
        //插入用户角色关系
        userRoleService.saveBatch(listUserRole);

        return u;
    }

    public IPage<User> page (UserDto userDto) {
        QueryWrapper<User> build = buildWrapper(userDto);
        return userMapper.selectPage(userDto.getPage()==null ? new Page<>() : userDto.getPage(),build);
    }


    public IPage<UserVo> findUserRoles (UserDto userDto) {
        return userMapper.findUserRoles(userDto.getPage()==null ? new Page<>():userDto.getPage(),userDto);
    }

    public UserVo findOneUserRoles (UserDto userDto) {
        return userMapper.findOneUserRoles(userDto);
    }


    public String[] getUserRoles (UserDto userDto) {
        UserVo u = userMapper.findOneUserRoles(userDto);
        if (u == null ) {
            return null;
        } else {
            String roleCodes = u.getRoleCodes();
            if (StringUtils.isEmpty(roleCodes)) {
                return null;
            }
            return roleCodes.split(",");
        }

    }


    public String getUserDeptCode (UserDto userDto) {
        UserVo u = userMapper.findOneUserRoles(userDto);
        if (u == null ) {
            return null;
        } else {
           return u.getDeptCode();
        }

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

        if (userDto.getDeptCode() !=null && !"".equals(userDto.getDeptCode())) {
            build.eq("dept_code",userDto.getDeptCode());
        }

        return build;
    }

    @Transactional
    public void updateUser (User u) {
        String roleCodes = u.getRoleCodes();
        //先修改基本信息
        this.updateById(u);
        //在修改角色
        u = this.getById(u.getId());
        //先删除
        String userId = u.getUserId();
        QueryWrapper<UserRole> q = new QueryWrapper<>();
        q.eq("user_id",userId);
        userRoleService.remove(q);
        //修改角色
        List<UserRole> listUserRole = new ArrayList<UserRole>();
        if (StringUtils.isNotEmpty(roleCodes)) {
            String[] array = roleCodes.split(";");
            if (array.length > 0) {
                for (int i = 0; i < array.length;i ++) {
                    listUserRole.add(new UserRole().setUserId(u.getUserId())
                            .setRoleCode(array[i]));
                }
            }
        }
        //插入用户角色关系
        userRoleService.saveBatch(listUserRole);
    }
}
