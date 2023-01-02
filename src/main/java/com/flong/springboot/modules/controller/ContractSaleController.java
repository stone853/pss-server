package com.flong.springboot.modules.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.ContractSale;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.dto.ContractSaleDto;
import com.flong.springboot.modules.entity.dto.CustomerDto;
import com.flong.springboot.modules.entity.vo.ContractSaleVo;
import com.flong.springboot.modules.entity.vo.CustomerVo;
import com.flong.springboot.modules.service.ContractSaleService;
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
 * @Date:2022-12-25
 * @Description:销售合同控制层
 */
@Api(tags = "销售合同信息")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/contractsale")
public class ContractSaleController {

    @Autowired
    private ContractSaleService contractSaleService;

    /**
     * 添加
     */
    @ApiOperation("增加销售合同信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "ContractSale",dataTypeClass = ContractSale.class , value ="")})
    @PostMapping("/v1/add")
    public int add(@RequestHeader("token") String token,@Validated @RequestBody ContractSale t) {
        List<FileBean> fileBeanList = t.getFileBeanList();
        if (fileBeanList !=null && fileBeanList.size() > 0) {
            t.setFileC(JSONArray.toJSONString(fileBeanList));
        }
        return contractSaleService.insert(t);
    }

    /**
     * 修改
     * @param contractSale
     */
    @PutMapping("/updateById")
    public void updateOrInsert(@RequestBody ContractSale contractSale) {
        List<FileBean> fileBeanList = contractSale.getFileBeanList();
        if (fileBeanList !=null && fileBeanList.size() > 0) {
            contractSale.setFileC(JSONArray.toJSONString(fileBeanList));
        }
        contractSaleService.update(contractSale);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        contractSaleService.removeByIds(ids);
    }

//    /**
//     * 通过指定Id进行查询
//     *
//     * @param custCode
//     */
//    @GetMapping("/getOne/{custCode}")
//    public contractsale getOne(@RequestHeader("token") String token,@PathVariable("custCode") String custCode) {
//        return contractSaleService.getOneByCode(custCode);
//    }
//
//    /**
//     * 销售合同分页，参数有多个使用下标索引进行处理.如果有两个参数(如销售合同名和地址)：conditionList[0].fieldName=contractsaleName、 conditionList[0].fieldName=address
//     * 未转码请求分页地址: http://localhost:7011/contractsale/page?conditionList[0].fieldName=contractsaleName&conditionList[0].operation=LIKE&conditionList[0].value=周
//     * 已转码请求分页地址: http://localhost:7011/contractsale/page?conditionList[0].fieldName=contractsaleName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
//     * @return
//     */
    @PostMapping("/page")
    public IPage<ContractSaleVo> page(@RequestHeader("token") String token, @RequestBody ContractSaleDto contractSaleDto) {
        return contractSaleService.pageList(contractSaleDto);
    }


}
