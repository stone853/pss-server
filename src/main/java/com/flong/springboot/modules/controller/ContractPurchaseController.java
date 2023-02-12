package com.flong.springboot.modules.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.ContractPurchase;
import com.flong.springboot.modules.entity.ContractSale;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.dto.ContractPurchaseDto;
import com.flong.springboot.modules.entity.dto.ContractSaleDto;
import com.flong.springboot.modules.entity.vo.ContractPurchaseVo;
import com.flong.springboot.modules.entity.vo.ContractSaleVo;
import com.flong.springboot.modules.service.ContractPurchaseService;
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
 * @Description:采购合同控制层
 */
@Api(tags = "采购合同")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/contractpur")
public class ContractPurchaseController {

    @Autowired
    private ContractPurchaseService contractPurchaseService;

    @Autowired
    private HttpServletRequest request;


    @Autowired
    private OptLogService optLogService;
    /**
     * 添加
     */
    @ApiOperation("增加采购合同信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "ContractPurchase",dataTypeClass = ContractPurchase.class , value ="")})
    @PostMapping("/v1/add")
    public ContractPurchase add(@RequestHeader("token") String token,@Validated @RequestBody ContractPurchase t) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "采购合同","采购合同-新增");

        List<FileBean> fileBeanList = t.getFileBeanList();
        if (fileBeanList !=null && fileBeanList.size() > 0) {
            t.setFileC(JSONArray.toJSONString(fileBeanList));
        }
        t.setCreateUser(UserHelper.getUserId(token));
        return contractPurchaseService.insert(t);
    }

    /**
     * 修改
     * @param contractPurchase
     */
    @PutMapping("/updateById")
    public void updateOrInsert(@RequestHeader("token") String token,@Validated @RequestBody ContractPurchase contractPurchase) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "采购合同","采购合同-修改");

        List<FileBean> fileBeanList = contractPurchase.getFileBeanList();
        if (fileBeanList !=null && fileBeanList.size() > 0) {
            contractPurchase.setFileC(JSONArray.toJSONString(fileBeanList));
        }
        contractPurchaseService.insert(contractPurchase);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestHeader("token") String token,@RequestBody List<String> ids) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "采购合同","采购合同-删除");

        contractPurchaseService.removeByIds(ids);
    }

    /**
     * 查询合同详情
     *
     * @param id
     */
    @GetMapping("/getOne")
    public ContractPurchaseVo getOne(@RequestHeader("token") String token, @RequestParam("id") int id) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "采购合同","采购合同-查详情");
        return contractPurchaseService.getOneById(id);
    }

    /**
     * 查询合同详情
     *
     * @param contractCode
     */
    @GetMapping("/getOneByContractCode")
    public ContractPurchaseVo getOneByCode(@RequestHeader("token") String token, @RequestParam("contractCode") String contractCode) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "采购合同","采购合同-查详情");
        return contractPurchaseService.getOneByCode(UserHelper.getUserId(token),contractCode);
    }

//    /**
//     * 采购合同分页，参数有多个使用下标索引进行处理.如果有两个参数(如采购合同名和地址)：conditionList[0].fieldName=ContractPurchaseName、 conditionList[0].fieldName=address
//     * 未转码请求分页地址: http://localhost:7011/ContractPurchase/page?conditionList[0].fieldName=ContractPurchaseName&conditionList[0].operation=LIKE&conditionList[0].value=周
//     * 已转码请求分页地址: http://localhost:7011/ContractPurchase/page?conditionList[0].fieldName=ContractPurchaseName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
//     * @return
//     */
    @PostMapping("/page")
    public IPage<ContractPurchaseVo> page(@RequestHeader("token") String token, @RequestBody ContractPurchaseDto contractPurchaseDto) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "采购合同","采购合同-首页");
        contractPurchaseDto.setUserId(UserHelper.getUserId(token));
        return contractPurchaseService.pageList(contractPurchaseDto);
    }



}
