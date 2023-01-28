package com.flong.springboot.modules.controller;


import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.EvaScheme;
import com.flong.springboot.modules.entity.dto.EvaSchemeDto;
import com.flong.springboot.modules.service.EvaSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:方案控制层
 */
@Api(tags = "方案信息")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/evascheme")
public class EvaSchemeController {

    @Autowired
    private EvaSchemeService evaSchemeService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 添加
     */
    @ApiOperation("增加方案信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "EvaScheme",dataTypeClass = EvaScheme.class , value ="")})
    @PostMapping("/v1/add")
    public int add(@RequestHeader("token") String token,@Validated @RequestBody EvaScheme t) {

        t.setUpdUser(UserHelper.getUserId(token));
        return evaSchemeService.insert(t);
    }

    /**
     * 修改
     * @param evaScheme
     */
    @PutMapping("/updateById")
    public void updateOrInsert(@RequestHeader("token") String token,@RequestBody EvaScheme evaScheme) {
        evaSchemeService.update(evaScheme);
    }

    /**
     * 开启方案
     * @param evaScheme
     */
    @PutMapping("/openScheme")
    public void openScheme(@RequestHeader("token") String token,@RequestBody EvaScheme evaScheme) {
        evaSchemeService.openScheme(evaScheme);
    }

    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestHeader("token") String token,@RequestBody List<String> ids) {
        evaSchemeService.removeByIds(ids);
    }

//    /**
//     * 通过指定Id进行查询
//     *
//     * @param custCode
//     */
//    @GetMapping("/getOne/{custCode}")
//    public evascheme getOne(@RequestHeader("token") String token,@PathVariable("custCode") String custCode) {
//        return EvaSchemeService.getOneByCode(custCode);
//    }
//
//    /**
//     * 方案分页，参数有多个使用下标索引进行处理.如果有两个参数(如方案名和地址)：conditionList[0].fieldName=evaschemeName、 conditionList[0].fieldName=address
//     * 未转码请求分页地址: http://localhost:7011/evascheme/page?conditionList[0].fieldName=evaschemeName&conditionList[0].operation=LIKE&conditionList[0].value=周
//     * 已转码请求分页地址: http://localhost:7011/evascheme/page?conditionList[0].fieldName=evaschemeName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
//     * @return
//     */
    @PostMapping("/page")
    public List<EvaScheme> page(@RequestHeader("token") String token, @RequestBody EvaSchemeDto evaSchemeDto) {
        return evaSchemeService.list(evaSchemeDto);
    }


}
