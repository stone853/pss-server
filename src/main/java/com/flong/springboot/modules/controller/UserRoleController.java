package com.flong.springboot.modules.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.core.util.BuildConditionWrapper;
import com.flong.springboot.core.vo.Conditions;
import com.flong.springboot.modules.entity.UserRole;
import com.flong.springboot.modules.service.UserRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:jinshi
 * @Date:2020-08-16
 * @Description:用户角色控制层
 */
@Api(tags = "用户角色")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/user_role")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 添加
     */
    @ApiOperation("增加用户角色信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "userRole",dataTypeClass = UserRole.class , value ="")})
    @PostMapping("/v1/add")
    public int add(@RequestHeader("token") String token,@Validated @RequestBody UserRole t) {
        return userRoleService.getBaseMapper().insert(t);
    }

    /**
     * 添加
     */
    @ApiOperation("批量新增")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "List<UserRole>",dataTypeClass = List.class, value ="")})
    @PostMapping("/v1/addBatch")
    public boolean addBatch(@RequestHeader("token") String token,@Validated @RequestBody List<UserRole> t) {
        if (t ==null || t.size() == 0) {
            return true;
        }
        return userRoleService.insertBatch(t);
    }

    /**
     * 修改
     * @param userRole
     */
    @PutMapping("/updateById")
    public void updateById(@Validated @RequestBody UserRole userRole) {
        userRoleService.updateById(userRole);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        userRoleService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     * @param id
     */
    @GetMapping("/getOne/{userRoleId}")
    public UserRole getOne(@RequestHeader("token") String token,String id) {
        return userRoleService.getById(id);
    }


    @GetMapping("/page")
    public IPage<UserRole> page(Page page, Conditions conditions) {
        QueryWrapper<UserRole> build = BuildConditionWrapper.build(conditions.getConditionList(), UserRole.class);
        //build.lambda().orderByDesc(userRole::getCreateTime);
        //page.setSize(2);
        return userRoleService.page(page, build);
    }






}
