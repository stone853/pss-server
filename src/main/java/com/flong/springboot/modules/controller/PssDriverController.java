package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.PssDriver;
import com.flong.springboot.modules.entity.dto.PssDriverDto;
import com.flong.springboot.modules.service.PssDriverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:liangjl
 * @Date:2020-08-16
 * @Description:司机控制层
 */
@Api(tags = "司机")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/driver")
public class PssDriverController {

    @Autowired
    private PssDriverService pssDriverService;

    /**
     * 添加
     */
    @ApiOperation("增加司机")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "PssDriver",dataTypeClass = PssDriver.class , value ="")})
    @PostMapping("/v1/add")
    public PssDriver add(@RequestHeader("token") String token,@RequestBody PssDriver pssDriver) {
        pssDriver.setDriverCode(GeneratorKeyUtil.getDriverNextCode());
        pssDriverService.save(pssDriver);
        return pssDriver;
    }

    /**
     * 修改
     * @param pssDriver
     */
    @PutMapping("/updateById")
    public void updateById(@RequestBody PssDriver pssDriver) {
        pssDriverService.updateById(pssDriver);
    }



    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        pssDriverService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     * @param pssDriverId
     */
    @GetMapping("/getOne/{PssDriverId}")
    public PssDriver getOne(@RequestHeader("token") String token,@PathVariable("pssDriverId") String pssDriverId) {
        return pssDriverService.getById(pssDriverId);
    }

    /**
     * 用户分页，参数有多个使用下标索引进行处理.如果有两个参数(如用户名和地址)：conditionList[0].fieldName=PssDriverName、 conditionList[0].fieldName=address
     * 未转码请求分页地址: http://localhost:7011/PssDriver/page?conditionList[0].fieldName=PssDriverName&conditionList[0].operation=LIKE&conditionList[0].value=周
     * 已转码请求分页地址: http://localhost:7011/PssDriver/page?conditionList[0].fieldName=PssDriverName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
     * @return
     */
    @PostMapping("/page")
    public IPage<PssDriver> page(@RequestHeader("token") String token, @RequestBody PssDriverDto pssDriver) {

        return pssDriverService.pageList(pssDriver);
    }

}
