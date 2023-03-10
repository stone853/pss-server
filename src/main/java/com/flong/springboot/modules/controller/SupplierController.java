package com.flong.springboot.modules.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.utils.FileUtil;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.config.PssConfig;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.Supplier;
import com.flong.springboot.modules.entity.dto.SupplierDto;
import com.flong.springboot.modules.entity.vo.SupplierVo;
import com.flong.springboot.modules.service.SupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:供应商控制层
 */
@Api(tags = "供应商")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @Autowired
    PssConfig pssConfig;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    /**
     * 添加
     */
    @ApiOperation("增加供应商信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "Supplier",dataTypeClass = Supplier.class , value ="")})
    @PostMapping("/v1/add")
    public Supplier add(@RequestHeader("token") String token,@Validated  @RequestBody Supplier t) {
        List<FileBean> fileBeanList = t.getFileBeanList();
        t.setFileC(JSONArray.toJSONString(fileBeanList));

        return supplierService.insert(t);
    }

    /**
     * 修改
     * @param supplier
     */
    @PutMapping("/updateById")
    public void updateById(@Validated @RequestBody Supplier supplier) {
        List<FileBean> fileBeanList = supplier.getFileBeanList();
        supplier.setFileC(JSONArray.toJSONString(fileBeanList));

        supplierService.update(supplier);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        supplierService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     * @param supplierCode
     */
    @GetMapping("/getOne/{supplierCode}")
    public Supplier getOne(@RequestHeader("token") String token,@PathVariable("supplierCode") String supplierCode) {
        return supplierService.getOneByCode(supplierCode);
    }

    /**
     * 供应商分页，参数有多个使用下标索引进行处理.如果有两个参数(如供应商名和地址)：conditionList[0].fieldName=SupplierName、 conditionList[0].fieldName=address
     * 未转码请求分页地址: http://localhost:7011/Supplier/page?conditionList[0].fieldName=SupplierName&conditionList[0].operation=LIKE&conditionList[0].value=周
     * 已转码请求分页地址: http://localhost:7011/Supplier/page?conditionList[0].fieldName=SupplierName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
     * @return
     */
    @PostMapping("/page")
    public IPage<SupplierVo> page(@RequestHeader("token") String token, @RequestBody SupplierDto supplierDto) {
        return supplierService.pageList(supplierDto);
    }




}
