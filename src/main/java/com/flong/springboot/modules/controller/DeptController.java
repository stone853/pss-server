package com.flong.springboot.modules.controller;



import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.Dept;
import com.flong.springboot.modules.service.DeptService;

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
 * @Description:部门控制层
 */
@Api(tags = "部门")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 添加
     */
    @ApiOperation("增加部门信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "dept",dataTypeClass = Dept.class , value ="")})
    @PostMapping("/v1/add")
    public int add(@RequestHeader("token") String token,@RequestBody Dept t) {
        t.setDeptCode(GeneratorKeyUtil.getDeptNextId());
        return deptService.insert(t);
    }

    /**
     * 修改
     * @param dept
     */
    @PutMapping("/updateById")
    public void updateById(@RequestBody Dept dept) {
        deptService.updateById(dept);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        deptService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     * @param deptId
     */
    @GetMapping("/getOne/{deptId}")
    public Dept getOne(@RequestHeader("token") String token,String deptId) {
        return deptService.getDeptById(deptId);
    }

//    /**
//     * 部门分页，参数有多个使用下标索引进行处理.如果有两个参数(如部门名和地址)：conditionList[0].fieldName=deptName、 conditionList[0].fieldName=address
//     * 未转码请求分页地址: http://localhost:7011/dept/page?conditionList[0].fieldName=deptName&conditionList[0].operation=LIKE&conditionList[0].value=周
//     * 已转码请求分页地址: http://localhost:7011/dept/page?conditionList[0].fieldName=deptName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
//     * @param page
//     * @param conditions 条件
//     * @return
//     */
//    @GetMapping("/page")
//    public IPage<dept> page(Page page, Conditions conditions) {
//        QueryWrapper<dept> build = BuildConditionWrapper.build(conditions.getConditionList(), dept.class);
//        //build.lambda().orderByDesc(dept::getCreateTime);
//        //page.setSize(2);
//        return deptService.page(page, build);
//    }

    @ApiOperation("查询所有部门信息")
    @GetMapping("/findAlldept")
    public List<Dept> findAlldept() {
        return deptService.findAll();
    }







}
