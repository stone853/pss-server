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
import com.flong.springboot.modules.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:liangjl
 * @Date:2020-08-16
 * @Description:用户控制层
 */
@Api(tags = "登录信息")
@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @ApiOperation("登陆")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "LoginDto",dataTypeClass = LoginDto.class , value ="")})
    @PostMapping("/v1/login")
    public LoginVo login(@RequestBody LoginDto loginDto){
        User u = userService.login(loginDto);
        LoginVo lv = new LoginVo();
        if (u ==null) {
            throw new BaseException(CommMsgCode.BIZ_INTERRUPT, "用户名或密码错误");
        }
        return lv.setToken(UserHelper.getToken(u.getMobile(),u.getPassword())).setUserName(u.getName());
    }

}
