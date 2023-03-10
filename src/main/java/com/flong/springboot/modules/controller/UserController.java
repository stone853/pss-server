package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.utils.MD5Utils;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.core.exception.BaseException;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.dto.ExchangeUserDto;
import com.flong.springboot.modules.entity.dto.UpdUserPwdDto;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.entity.vo.IndexDataVo;
import com.flong.springboot.modules.entity.vo.LoginVo;
import com.flong.springboot.modules.entity.vo.TodoTaskVo;
import com.flong.springboot.modules.entity.vo.UserVo;
import com.flong.springboot.modules.service.CustomerService;
import com.flong.springboot.modules.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author:liangjl
 * @Date:2020-08-16
 * @Description:用户控制层
 */
@Api(tags = "用户")
@Slf4j
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    CustomerService customerService;
    /**
     * 添加
     */
    @ApiOperation("增加用户信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "User",dataTypeClass = User.class , value ="")})
    @PostMapping("/v1/add")
    public User add(@RequestHeader("token") String token,@Validated @RequestBody User user) {
        return userService.insert(user);
    }

    /**
     * 修改
     * @param user
     */
    @PutMapping("/updateById")
    public void updateById(@Validated @RequestBody User user) {
        userService.updateUser(user);
    }


    /**
     * 修改
     * @param user
     */
    @PutMapping("/forbiddenUser")
    public void forbiddenUser( @RequestBody User user) {
        userService.forbiddenUser(user);
    }



    @ApiOperation("重置用于密码")
    @PutMapping("/resetPwd")
    public void resetPwd(@RequestBody User user) {
        user.setPassword(MD5Utils.encrypt("123456"));
        userService.updateById(user);
    }

    @ApiOperation("修改密码")
    @PutMapping("/updUserPwd")
    public boolean updUserPwd(@RequestBody UpdUserPwdDto updUserPwdDto) {
        return userService.updUserPwd(updUserPwdDto);
    }

    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        userService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     * @param userId
     */
    @GetMapping("/getOne/{userId}")
    public User getOne(@RequestHeader("token") String token,@PathVariable("userId") String userId) {
        return userService.getUserByUserId(userId);
    }

    /**
     * 用户分页，参数有多个使用下标索引进行处理.如果有两个参数(如用户名和地址)：conditionList[0].fieldName=userName、 conditionList[0].fieldName=address
     * 未转码请求分页地址: http://localhost:7011/user/page?conditionList[0].fieldName=userName&conditionList[0].operation=LIKE&conditionList[0].value=周
     * 已转码请求分页地址: http://localhost:7011/user/page?conditionList[0].fieldName=userName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
     * @return
     */
    @PostMapping("/page")
    public IPage<UserVo> page(@RequestHeader("token") String token, @RequestBody UserDto userDto) {
        return userService.findUserRoles(userDto);
    }

    /**
     * 通过指定Id进行查询
     *
     */
    @ApiOperation("首页数据")
    @GetMapping("/v1/findIndexData")
    public IndexDataVo findIndexData(@RequestHeader("token") String token,@RequestParam("type") String type) {
        return userService.findIndexData(type,UserHelper.getUserId(token));
    }

    /**
     * 通过指定Id进行查询
     *
     */
    @ApiOperation("代办任务")
    @GetMapping("/v1/todoTask")
    public List<TodoTaskVo> todoTask(@RequestHeader("token") String token) {
        return userService.todoTask(UserHelper.getUserId(token));
    }

    /**
     * 切换用户
     *
     */
    @ApiOperation("切换用户")
    @PostMapping("/v1/exchangeUser")
    public LoginVo exchangeUser(@RequestHeader("token") String token, @RequestBody ExchangeUserDto exchangeUserDto) {
        String mobile = exchangeUserDto.getMobile();
        String deptCode = exchangeUserDto.getDeptCode();

        if (StringUtils.isEmpty(mobile)) {
            throw new BaseException(CommMsgCode.BIZ_INTERRUPT, "切换用户-手机号不能为空");
        }

        if (StringUtils.isEmpty(deptCode)) {
            throw new BaseException(CommMsgCode.BIZ_INTERRUPT, "切换用户-部门不能为空");
        }

        QueryWrapper<User> q =new QueryWrapper<>();
        q.eq("mobile",mobile);
        q.eq("dept_code",deptCode);
        q.last("limit 1");
        User u = userService.getOne(q);

        if (u ==null) {
            throw new BaseException(CommMsgCode.BIZ_INTERRUPT, "用户名或密码错误");
        }

        if (u.getIsDelete() !=null && String.valueOf(u.getIsDelete()).equals("1")) {
            throw new BaseException(CommMsgCode.BIZ_INTERRUPT, "该用户被禁用");
        }


        LoginVo lv = new LoginVo();
        return getUserInfo(lv,u);
    }


    public LoginVo getUserInfo (LoginVo lv,User u) {
        UserVo vo = new UserVo();
        try {
            vo = userService.findOneUserRoles(new UserDto().setUserId(u.getUserId()));

            if (vo !=null) {
                lv.setRoleCode(vo.getRoleCodes());
                lv.setDeptName(vo.getDeptName());
                lv.setRoleName(vo.getRoleNames());
                lv.setUserType(vo.getUserType());
                lv.setDeptCode(vo.getDeptCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("vo:{},code:{},message:{}", vo, CommMsgCode.DAO_ERROR.getCode(), e.getMessage());
        }


        if (StringUtils.isNotEmpty(u.getUserType()) && u.getUserType().equals("2")) {
            lv.setCustomerVoList(customerService.findByMobile(u.getMobile()));
        }


        return lv.setToken(UserHelper.getToken(u.getUserId(),u.getPassword()))
                .setUserName(u.getName()).setUserId(u.getUserId());
    }

}
