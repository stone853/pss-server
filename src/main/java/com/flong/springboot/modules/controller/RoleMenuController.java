package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.core.util.BuildConditionWrapper;
import com.flong.springboot.core.vo.Conditions;
import com.flong.springboot.modules.entity.Menu;
import com.flong.springboot.modules.entity.RoleMenu;
import com.flong.springboot.modules.service.RoleMenuService;
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
 * @Description:角色菜单控制层
 */
@Api(tags = "角色菜单信息")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/role_menu")
public class RoleMenuController {

    @Autowired
    private RoleMenuService roleMenuService;

    /**
     * 添加
     */
    @ApiOperation("增加角色菜单信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "RoleMenu",dataTypeClass = RoleMenu.class , value ="")})
    @PostMapping("/v1/add")
    public int add(@RequestHeader("token") String token,@RequestBody RoleMenu t) {
        return roleMenuService.getBaseMapper().insert(t);
    }

    /**
     * 修改
     * @param roleMenu
     */
    @PutMapping("/updateById")
    public void updateById(@RequestBody RoleMenu roleMenu) {
        roleMenuService.updateById(roleMenu);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        roleMenuService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     * @param id
     */
    @GetMapping("/getOne/{id}")
    public RoleMenu getOne(@RequestHeader("token") String token,@PathVariable("id") String id) {
        return roleMenuService.getById(id);
    }

    /**
     * 角色菜单分页，参数有多个使用下标索引进行处理.如果有两个参数(如角色菜单名和地址)：conditionList[0].fieldName=roleMenuName、 conditionList[0].fieldName=address
     * 未转码请求分页地址: http://localhost:7011/roleMenu/page?conditionList[0].fieldName=roleMenuName&conditionList[0].operation=LIKE&conditionList[0].value=周
     * 已转码请求分页地址: http://localhost:7011/roleMenu/page?conditionList[0].fieldName=roleMenuName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
     * @param page
     * @param conditions 条件
     * @return
     */
    @GetMapping("/page")
    public IPage<RoleMenu> page(Page page, Conditions conditions) {
        QueryWrapper<RoleMenu> build = BuildConditionWrapper.build(conditions.getConditionList(), RoleMenu.class);
        //build.lambda().orderByDesc(roleMenu::getCreateTime);
        //page.setSize(2);
        return roleMenuService.page(page, build);
    }


    @ApiOperation("查询所有角色菜单")
    @GetMapping("/findAll")
    public List<RoleMenu> findAll() {
        return roleMenuService.list();
    }

    @ApiOperation("删除角色菜单{id}")
    @GetMapping("/delById")
    public boolean delById(String id) {
        return roleMenuService.removeById(id);
    }


    /**
     * 添加
     */
    @ApiOperation("批量新增")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "List<RoleMenu>",dataTypeClass = List.class, value ="")})
    @PostMapping("/v1/addBatch")
    public boolean addBatch(@RequestHeader("token") String token,@RequestBody List<RoleMenu> t) {
        return roleMenuService.saveBatch(t,1000);
    }


}
