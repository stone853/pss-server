package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.CheckMaterial;
import com.flong.springboot.modules.entity.dto.CheckMaterialDto;
import com.flong.springboot.modules.entity.vo.CheckMaterialVo;
import com.flong.springboot.modules.entity.vo.ContractPurchaseVo;
import com.flong.springboot.modules.service.CheckMaterialService;
import com.flong.springboot.modules.service.OptLogService;
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
 * @Description:盘点控制层
 */
@Api(tags = "盘点")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/check/material")
public class CheckMaterialController {

    @Autowired
    private CheckMaterialService checkMaterialService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private OptLogService optLogService;

    /**
     * 添加
     */
    @ApiOperation("增加盘点")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "CheckMaterial",dataTypeClass = CheckMaterial.class , value ="")})
    @PostMapping("/v1/add")
    public CheckMaterial add(@RequestHeader("token") String token,@Validated @RequestBody CheckMaterial t) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "盘点单","盘点单-新增");
        FileBean f = new FileBean();
        t.setFileC(f.fileBeanListToString(t.getFileBeanList()));

        t.setOptUser(UserHelper.getUserId(token));
        return checkMaterialService.insert(t);
    }


    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestHeader("token") String token,@RequestBody List<String> ids) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "盘点单","盘点单-删除");
        checkMaterialService.removeByIds(ids);
    }

    @ApiOperation("根据ID查询详情")
    @GetMapping("/getOne")
    public CheckMaterialVo getOne(@RequestHeader("token") String token, @RequestParam("id") int id) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "盘点单","查询详情");
        return checkMaterialService.getOneById(id);
    }

    /**
     * 查询盘点详情
     *
     * @param billCode
     */
    @ApiOperation("根据billCode查询详情")
    @GetMapping("/getOneByContractCode")
    public CheckMaterialVo getOneByCode(@RequestHeader("token") String token, @RequestParam("billCode") String billCode) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),"盘点单","查询详情");
        return checkMaterialService.getOneByCode(billCode);
    }

//    /**
//     * 盘点分页，参数有多个使用下标索引进行处理.如果有两个参数(如盘点名和地址)：conditionList[0].fieldName=CheckMaterialName、 conditionList[0].fieldName=address
//     * 未转码请求分页地址: http://localhost:7011/CheckMaterial/page?conditionList[0].fieldName=CheckMaterialName&conditionList[0].operation=LIKE&conditionList[0].value=周
//     * 已转码请求分页地址: http://localhost:7011/CheckMaterial/page?conditionList[0].fieldName=CheckMaterialName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
//     * @return
//     */
    @PostMapping("/page")
    public IPage<CheckMaterialVo> page(@RequestHeader("token") String token, @RequestBody CheckMaterialDto checkMaterialDto) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),"盘点单","盘点单查询页面");
        return checkMaterialService.pageList(checkMaterialDto);
    }




}
