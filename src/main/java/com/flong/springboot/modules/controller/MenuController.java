package com.flong.springboot.modules.controller;



import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.Menu;
import com.flong.springboot.modules.entity.UserRole;
import com.flong.springboot.modules.service.MenuService;
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
 * @Description:菜单控制层
 */
@Api(tags = "菜单")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 添加
     */
    @ApiOperation("增加菜单信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "Menu",dataTypeClass = Menu.class , value ="")})
    @PostMapping("/v1/add")
    public int add(@RequestHeader("token") String token,@RequestBody Menu t) {
        return menuService.insert(t);
    }

    /**
     * 修改
     * @param Menu
     */
    @PutMapping("/updateById")
    public void updateById(@RequestBody Menu Menu) {
        menuService.updateById(Menu);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        menuService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     * @param menuId
     */
    @GetMapping("/getOne/{MenuId}")
    public Menu getOne(@RequestHeader("token") String token,String menuId) {
        return menuService.getMenuById(menuId);
    }

//    /**
//     * 菜单分页，参数有多个使用下标索引进行处理.如果有两个参数(如菜单名和地址)：conditionList[0].fieldName=MenuName、 conditionList[0].fieldName=address
//     * 未转码请求分页地址: http://localhost:7011/Menu/page?conditionList[0].fieldName=MenuName&conditionList[0].operation=LIKE&conditionList[0].value=周
//     * 已转码请求分页地址: http://localhost:7011/Menu/page?conditionList[0].fieldName=MenuName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
//     * @param page
//     * @param conditions 条件
//     * @return
//     */
//    @GetMapping("/page")
//    public IPage<Menu> page(Page page, Conditions conditions) {
//        QueryWrapper<Menu> build = BuildConditionWrapper.build(conditions.getConditionList(), Menu.class);
//        //build.lambda().orderByDesc(Menu::getCreateTime);
//        //page.setSize(2);
//        return menuService.page(page, build);
//    }

    @ApiOperation("查询所有菜单信息")
    @GetMapping("/findAllMenu")
    public List<Menu> findAllMenu() {
        return menuService.findAll();
    }

    @ApiOperation("查询用户对应菜单")
    @GetMapping("/queryMenusByUserId")
    public List<Menu> queryMenusByUserId(@RequestHeader("token") String token,String userId) {
        return menuService.queryMenusByUserId(userId);
    }

    @ApiOperation("查询角色对应菜单")
    @GetMapping("/findMenuByRoleCode")
    public List<Menu> queryMenusByRoleCode(@RequestHeader("token") String token,String roleCode) {
        return menuService.findMenuByRoleCode(roleCode);
    }




}
