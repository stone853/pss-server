package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.MD5Utils;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.Role;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.UserRole;
import com.flong.springboot.modules.entity.dto.*;
import com.flong.springboot.modules.entity.vo.IndexDataVo;
import com.flong.springboot.modules.entity.vo.TodoTaskVo;
import com.flong.springboot.modules.entity.vo.UserVo;
import com.flong.springboot.modules.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
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
    RoleService roleService;

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
        if (loginDto.getPwd() !=null && !"".equals(loginDto.getPwd())) {
            build.eq("password",loginDto.getPwd());
        }

        if (loginDto.getMobile() !=null && !"".equals(loginDto.getMobile())) {
            build.eq("mobile",loginDto.getMobile());
        }

        if (loginDto.getUserType() !=null && !"".equals(loginDto.getUserType())) {
            build.eq("user_type",loginDto.getUserType());
        } else {
            build.ne("user_type","2");
        }

        build.last("limit 1");
        return userMapper.selectOne(build);
    }

    public User insert (User u) {
        String userType = u.getUserType();
        if (StringUtils.isEmpty(userType)) {
            u.setUserType("1"); //默认企业内部
        }

        QueryWrapper<User> q = new QueryWrapper<User>();
        q.eq("mobile",u.getMobile());
        q.ne("user_type","2");
        q.last("limit 1");
        User temp = userMapper.selectOne(q);
        if (temp !=null && !u.getUserType().equals("2")) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"手机号已存在");
        }



//        QueryWrapper<User> q1 = new QueryWrapper<User>();
//        q1.eq("name",u.getName());
//        List<User> listTemp = userMapper.selectList(q1);
//        if (listTemp !=null && listTemp.size() > 0) {
//            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"用户名已存在");
//        }

        String userId = GeneratorKeyUtil.getUserNextId();
        u.setUserId(userId);
        String pwd = u.getPassword();
        if (StringUtils.isEmpty(pwd)) {
            u.setPassword(MD5Utils.encrypt("123456"));
        }

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


    /**
     * 用户禁止
     * @param u
     */
    public void forbiddenUser (User u) {
        this.updateById(u);
    }

    public IndexDataVo findIndexData (String type,String userId) {
        IndexDataDto id = new IndexDataDto();

        String deptCode = this.getUserDeptCode(new UserDto().setUserId(userId));
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(deptCode)) {
            return userMapper.selectIndexData(id);
        }

        if (type.equals("2")) {
            id.setCustCode(deptCode);
        }

        if (type.equals("3")) {
            id.setSupplierCode(deptCode);
        }

        return userMapper.selectIndexData(id);
    }

    public List<TodoTaskVo> todoTask (String userId) {
        return userMapper.todoTask(userId);
    }


    public boolean updUserPwd (UpdUserPwdDto updUserPwdDto) {
        String mobile = updUserPwdDto.getMobile();
        String oldPwd = updUserPwdDto.getOldPwd();
        String newPwd = updUserPwdDto.getNewPwd();
        if (StringUtils.isEmpty(mobile)) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"手机号不能为空");
        }
        if (StringUtils.isEmpty(oldPwd)) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"旧密码不能为空");
        }
        if (StringUtils.isEmpty(newPwd)) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"新密码不能为空");
        }

        QueryWrapper<User> q = new QueryWrapper<>();
        q.eq("mobile",mobile);
        q.eq("password",oldPwd);
        q.last("limit 1");
        User u = this.getOne(q);
        if (u == null) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"原密码错误");
        }
        //u.setPassword(newPwd);
        //this.updateById(u);
        UpdateWrapper<User> upd = new UpdateWrapper<>();
        upd.set("password",newPwd);
        upd.eq("mobile",mobile);
        this.update(upd);
        return true;
    }

    /**
     * 新增或者修改客户/供应商账户
     * @param deptCode  部门code
     * @param mobile 手机号
     * @param userType 2-客户 3-供应商
     */
    public void insertOrUpdateUser (String deptCode,String mobile,String userName,String userType) {
        try {
            if (StringUtils.isEmpty(userType)) {
                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"用户类型为空");
            }
            if (StringUtils.isEmpty(deptCode)) {
                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"部门不能为空");
            }
            if (StringUtils.isEmpty(mobile)) {
                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"手机号不能为空");
            }
            if (StringUtils.isEmpty(userName)) {
                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"姓名不能为空");
            }
            String roleName = "";
            if (userType.equals("2")) {
                roleName = "客户";
            } else {
                roleName = "供应商";
            }
            //判断改部门联系人手机号
            QueryWrapper<User> q = new QueryWrapper<>();
            q.eq("dept_code",deptCode);
            q.last("limit 1");
            User u = this.getOne(q);

            if (u == null) {
                QueryWrapper<User> q1 = new QueryWrapper<>();
                q1.eq("mobile",mobile);
                q1.ne("user_type","2");
                q1.last("limit 1");
                User u1 = this.getOne(q1);
                if (u1 !=null && !userType.equals("2")) { //客户可以随意增加
                    throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"手机号已存在");
                }

                u = new User();
                u.setUserType(userType);
                u.setMobile(mobile);
                u.setDeptCode(deptCode);
                u.setName(userName);
                u.setRoleCodes(roleService.getOneRoleByName(roleName));
                this.insert(u);
            } else {
                u.setMobile(mobile);
                u.setUserType(userType);
                u.setName(userName);
                u.setDeptCode(deptCode);
                u.setRoleCodes(roleService.getOneRoleByName(roleName));
                this.updateUser(u);
            }
        } catch (ServiceException s) {
            s.printStackTrace();
            throw s;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException("修改用户账户信息失败");
        }

    }

}
