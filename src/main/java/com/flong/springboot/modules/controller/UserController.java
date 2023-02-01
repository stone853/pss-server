package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.model.ResultJsonModel;
import com.flong.springboot.base.utils.MD5Utils;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.core.exception.BaseException;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.MsgCode;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.dto.LoginDto;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.entity.vo.IndexDataVo;
import com.flong.springboot.modules.entity.vo.LoginVo;
import com.flong.springboot.modules.entity.vo.UserVo;
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
@Api(tags = "用户")
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
    public User add(@RequestHeader("token") String token,@RequestBody User user) {
        return userService.insert(user);
    }

    /**
     * 修改
     * @param user
     */
    @PutMapping("/updateById")
    public void updateById(@RequestBody User user) {
        userService.updateUser(user);
    }



    @ApiOperation("重置用于密码")
    @PutMapping("/resetPwd")
    public void resetPwd(@RequestBody User user) {
        user.setPassword(MD5Utils.encrypt("123456"));
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
    public IPage<UserVo> page(@RequestHeader("token") String token, @RequestBody UserDto userDto) {
        return userService.findUserRoles(userDto);
    }

    /**
     * 通过指定Id进行查询
     *
     */
    @ApiOperation("首页数据")
    @GetMapping("/v1/findIndexData")
    public IndexDataVo findIndexData(@RequestHeader("token") String token) {
        return userService.findIndexData();
    }

}
