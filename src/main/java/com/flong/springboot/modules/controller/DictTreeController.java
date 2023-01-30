package com.flong.springboot.modules.controller;



import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.core.enums.DictTreeEnum;
import com.flong.springboot.modules.entity.DictTree;
import com.flong.springboot.modules.service.DictTreeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author:jinshi
 * @Date:2020-08-16
 * @Description:字典树控制层
 */
@Api(tags = "字典树")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/dictTree")
public class DictTreeController {

    @Autowired
    private DictTreeService dictTreeService;

    /**
     * 添加
     */
    @ApiOperation("增加物料类型Tree")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "dictTree",dataTypeClass = DictTree.class , value ="")})
    @PostMapping("/v1/addMaterialType")
    public int addMaterialType(@RequestHeader("token") String token,@Validated @RequestBody DictTree t) {
        t.setNodeType(DictTreeEnum.MATERIAL.getCode()).setRemark("物料类别").setType(DictTreeEnum.MATERIAL.gettype());
        return dictTreeService.insert(t);
    }

    @ApiOperation("增加客户类型Tree")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "dictTree",dataTypeClass = DictTree.class , value ="")})
    @PostMapping("/v1/addCustomerType")
    public int addCustomerType(@RequestHeader("token") String token, @Validated @RequestBody DictTree t) {
        t.setNodeType(DictTreeEnum.CUSTOMER.getCode()).setRemark("客户类别").setType(DictTreeEnum.CUSTOMER.gettype());;
        return dictTreeService.insert(t);
    }

    @ApiOperation("增加供应商类型Tree")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "dictTree",dataTypeClass = DictTree.class , value ="")})
    @PostMapping("/v1/addSupplierType")
    public int addSupplierType(@RequestHeader("token") String token,@Validated @RequestBody DictTree t) {
        t.setNodeType(DictTreeEnum.SUPPLIER.getCode()).setRemark("供应商类别").setType(DictTreeEnum.SUPPLIER.gettype());;
        return dictTreeService.insert(t);
    }

    /**
     * 修改
     * @param dictTree
     */
    @PutMapping("/updateById")
    public void updateById(@RequestBody DictTree dictTree) {
        dictTreeService.updateById(dictTree);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        dictTreeService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     * @param dictTreeId
     */
    @GetMapping("/getOne/{dictTreeId}")
    public DictTree getOne(@RequestHeader("token") String token,String dictTreeId) {
        return dictTreeService.getById(dictTreeId);
    }


    @ApiOperation("查询所有字典树信息")
    @GetMapping("/findAllDictTree")
    public List<DictTree> findAllDictTree() {
        return dictTreeService.findAll();
    }



    @ApiOperation("查询物料树")
    @GetMapping("/findMaterialTree")
    public List<DictTree> findMaterialTree(@RequestHeader("token") String token) {
        return dictTreeService.findByType(DictTreeEnum.MATERIAL.gettype());
    }

    @ApiOperation("查询客户树")
    @GetMapping("/findCustomerTree")
    public List<DictTree> findCustomerTree(@RequestHeader("token") String token) {
        return dictTreeService.findByType(DictTreeEnum.CUSTOMER.gettype());
    }

    @ApiOperation("查询供应商树")
    @GetMapping("/findSupplierTree")
    public List<DictTree> findSupplierTree(@RequestHeader("token") String token) {
        return dictTreeService.findByType(DictTreeEnum.SUPPLIER.gettype());
    }




}
