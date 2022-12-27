package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.core.util.BuildConditionWrapper;
import com.flong.springboot.core.vo.Conditions;
import com.flong.springboot.modules.entity.Role;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.dto.RoleDto;
import com.flong.springboot.modules.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:jinshi
 * @Date:2020-08-16
 * @Description:角色控制层
 */
@Api(tags = "角色信息")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * 添加
     */
    @ApiOperation("增加角色信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "Role",dataTypeClass = Role.class , value ="")})
    @PostMapping("/v1/add")
    public int add(@RequestHeader("token") String token,@RequestBody Role t) {
        t.setCode(GeneratorKeyUtil.getRoleNextId());
        t.setCreateTime(UserHelper.getDateTime());
        return roleService.getBaseMapper().insert(t);
    }

    /**
     * 修改
     * @param role
     */
    @PutMapping("/updateById")
    public void updateById(@RequestBody Role role) {
        roleService.updateById(role);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        roleService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     * @param roleId
     */
    @GetMapping("/getOne/{roleId}")
    public Role getOne(@RequestHeader("token") String token,@PathVariable("roleId") String roleId) {
        return roleService.getById(roleId);
    }

    /**
     * 角色分页，参数有多个使用下标索引进行处理.如果有两个参数(如角色名和地址)：conditionList[0].fieldName=roleName、 conditionList[0].fieldName=address
     * 未转码请求分页地址: http://localhost:7011/role/page?conditionList[0].fieldName=roleName&conditionList[0].operation=LIKE&conditionList[0].value=周
     * 已转码请求分页地址: http://localhost:7011/role/page?conditionList[0].fieldName=roleName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
     * @param
     * @param
     * @return
     */
    @PostMapping("/page")
    public IPage<Role> page(@RequestHeader("token") String token,@RequestBody(required=false) RoleDto roleDto) {
        return roleService.page(roleDto);
    }


    @ApiOperation("查询所有角色")
    @GetMapping("/findAll")
    public List<Role> findAll() {
        return roleService.list();
    }

    @ApiOperation("删除角色{id}")
    @GetMapping("/delById")
    public boolean delById(String id) {
        return roleService.removeById(id);
    }



}
