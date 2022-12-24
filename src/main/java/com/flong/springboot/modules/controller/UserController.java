package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.model.ResultJsonModel;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.core.exception.BaseException;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.MsgCode;
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
@Api(tags = "用户信息")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 添加
     */
    @ApiOperation("增加用户信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "User",dataTypeClass = User.class , value ="")})
    @PostMapping("/v1/add")
    public int add(@RequestHeader("token") String token,@RequestBody User t) {
        return userService.insert(t);
    }

    /**
     * 修改
     * @param user
     */
    @PutMapping("/updateById")
    public void updateById(@RequestBody User user) {
        userService.updateById(user);
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
    public IPage<User> page(@RequestHeader("token") String token,@RequestBody UserDto userDto) {
        IPage<User> page = userService.page(userDto);
        if (page !=null && page.getRecords().size() >0 ) {
            List<String> userIds = page.getRecords().stream().map(User::getUserId).collect(Collectors.toList());
            return page.setRecords(userService.getBaseMapper().findUserRoles(userIds));
        }
        return page;
    }

    @ApiOperation("登陆")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "UserDto",dataTypeClass = UserDto.class , value ="")})
    @PostMapping("/v1/login")
    public LoginVo login(@RequestBody LoginDto loginDto){
        User u = userService.getUserByUserIdPwd(loginDto);
        LoginVo lv = new LoginVo();
        if (u ==null) {
            throw new BaseException(CommMsgCode.BIZ_INTERRUPT, "用户名或密码错误");
        }
        return lv.setToken(UserHelper.getToken(u.getUserId(),u.getMobile()));
    }

}
