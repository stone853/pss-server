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
import com.flong.springboot.core.mas.SendMas;
import com.flong.springboot.modules.entity.MobileVerif;
import com.flong.springboot.modules.entity.Role;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.UserRole;
import com.flong.springboot.modules.entity.dto.*;
import com.flong.springboot.modules.entity.vo.IndexDataVo;
import com.flong.springboot.modules.entity.vo.TodoTaskVo;
import com.flong.springboot.modules.entity.vo.UserVo;
import com.flong.springboot.modules.mapper.MobileVerifMapper;
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

    @Autowired
    SendMas sendMas;

    @Autowired
    MobileVerifMapper mobileVerifMapper;

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
            u.setUserType("1"); //??????????????????
        }

        QueryWrapper<User> q = new QueryWrapper<User>();
        q.eq("mobile",u.getMobile());
        q.ne("user_type","2");
        q.last("limit 1");
        User temp = userMapper.selectOne(q);
        if (temp !=null && !u.getUserType().equals("2")) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"??????????????????");
        }



//        QueryWrapper<User> q1 = new QueryWrapper<User>();
//        q1.eq("name",u.getName());
//        List<User> listTemp = userMapper.selectList(q1);
//        if (listTemp !=null && listTemp.size() > 0) {
//            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"??????????????????");
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
        //????????????????????????
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
        //?????????????????????
        this.updateById(u);
        //???????????????
        u = this.getById(u.getId());
        //?????????
        String userId = u.getUserId();
        QueryWrapper<UserRole> q = new QueryWrapper<>();
        q.eq("user_id",userId);
        userRoleService.remove(q);
        //????????????
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
        //????????????????????????
        userRoleService.saveBatch(listUserRole);
    }


    /**
     * ????????????
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
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"?????????????????????");
        }
        if (StringUtils.isEmpty(oldPwd)) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"?????????????????????");
        }
        if (StringUtils.isEmpty(newPwd)) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"?????????????????????");
        }

        QueryWrapper<User> q = new QueryWrapper<>();
        q.eq("mobile",mobile);
        q.eq("password",oldPwd);
        q.last("limit 1");
        User u = this.getOne(q);
        if (u == null) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"???????????????");
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
     * ????????????????????????/???????????????
     * @param deptCode  ??????code
     * @param mobile ?????????
     * @param userType 2-?????? 3-?????????
     */
    public void insertOrUpdateUser (String deptCode,String mobile,String userName,String userType) {
        try {
            if (StringUtils.isEmpty(userType)) {
                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"??????????????????");
            }
            if (StringUtils.isEmpty(deptCode)) {
                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"??????????????????");
            }
            if (StringUtils.isEmpty(mobile)) {
                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"?????????????????????");
            }
            if (StringUtils.isEmpty(userName)) {
                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"??????????????????");
            }
            String roleName = "";
            if (userType.equals("2")) {
                roleName = "??????";
            } else {
                roleName = "?????????";
            }
            //?????????????????????????????????
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
                if (u1 !=null && !userType.equals("2")) { //????????????????????????
                    throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"??????????????????");
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
            throw new ServiceException("??????????????????????????????");
        }

    }

    public String getVerifCode(String mobile) {
        QueryWrapper<User> q = new QueryWrapper<>();
        q.eq("mobile",mobile);
        q.last("limit 1");
        User u = this.getOne(q);
        if (u == null) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"????????????????????????");
        }
        return sendMas.sendVerifCode(mobile);
    }

    public void updPwdByVerifCode (ResetPwdDto rpd ) {
        String verifCode = rpd.getVerifCode();
        String mobile = rpd.getMobile();
        String pwd = rpd.getNewPwd();
        if (StringUtils.isEmpty(verifCode)) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"?????????????????????");
        }
        if (StringUtils.isEmpty(pwd)) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"??????????????????");
        }
        if (StringUtils.isEmpty(mobile)) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"?????????????????????");
        }

        QueryWrapper<User> qu = new QueryWrapper<>();
        qu.eq("mobile",mobile);
        qu.last("limit 1");
        User u = this.getOne(qu);
        if (u == null) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"????????????????????????");
        }

        MobileVerif m = mobileVerifMapper.isValidate(mobile,verifCode);
        if (m == null) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"???????????????");
        }

        UpdateWrapper<User> upd = new UpdateWrapper<>();
        upd.set("password",pwd);
        upd.eq("mobile",mobile);
        this.update(upd);
    }
}
