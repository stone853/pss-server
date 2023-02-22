package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.utils.MD5Utils;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.core.exception.BaseException;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.dto.LoginDto;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.entity.vo.LoginVo;
import com.flong.springboot.modules.entity.vo.UserVo;
import com.flong.springboot.modules.service.CustomerService;
import com.flong.springboot.modules.service.OptLogService;
import com.flong.springboot.modules.service.UserRoleService;
import com.flong.springboot.modules.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.flong.springboot.core.exception.CommMsgCode.NOT_SUPPORTED;

/**
 * @Author:liangjl
 * @Date:2020-08-16
 * @Description:用户控制层
 */
@Api(tags = "登录")
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    CustomerService customerService;

    @Autowired
    private OptLogService optLogService;

    @Autowired
    private HttpServletRequest request;


    @ApiOperation("登陆")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "LoginDto",dataTypeClass = LoginDto.class , value ="")})
    @PostMapping("/v1/login")
    public LoginVo login(@RequestBody LoginDto loginDto){
        User u = userService.login(loginDto);
        LoginVo lv = new LoginVo();
        if (u ==null) {
            throw new BaseException(CommMsgCode.BIZ_INTERRUPT, "用户名或密码错误");
        }

        if (u.getIsDelete() !=null && String.valueOf(u.getIsDelete()).equals("1")) {
            throw new BaseException(CommMsgCode.BIZ_INTERRUPT, "该用户被禁用");
        }



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

        String userType = loginDto.getUserType();
        if (StringUtils.isNotEmpty(userType) && userType.equals("2")) {
            lv.setCustomerVoList(customerService.findByMobile(loginDto.getMobile()));
        }

        optLogService.insertOptLog(u.getUserId(),UserHelper.getRealRequestIp(request),
                "登录","用户-"+u.getName());

        return lv.setToken(UserHelper.getToken(u.getUserId(),u.getPassword()))
                .setUserName(u.getName()).setUserId(u.getUserId());
    }

}
