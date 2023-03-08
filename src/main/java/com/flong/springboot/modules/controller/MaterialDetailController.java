package com.flong.springboot.modules.controller;


import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.Dept;
import com.flong.springboot.modules.entity.EvaIndex;
import com.flong.springboot.modules.entity.MaterialDetail;
import com.flong.springboot.modules.entity.dto.MaterialDetailDto;
import com.flong.springboot.modules.entity.vo.MaterialDetailVo;
import com.flong.springboot.modules.service.EvaIndexService;
import com.flong.springboot.modules.service.MaterialDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:物料明细控制层
 */
@Api(tags = "物料明细")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/materialdetail")
public class MaterialDetailController {

    @Autowired
    private MaterialDetailService materialDetailService;

    @Autowired
    private HttpServletRequest request;


    @GetMapping("/list")
    public List<MaterialDetailVo> list (@RequestHeader("token") String token,@RequestParam String foreignCode) {
        return materialDetailService.list(foreignCode);
    }

    @ApiOperation("查询供应商能申请的需求单物料明细")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "foreignCode",dataTypeClass = String.class , value ="需求单编号"),@ApiImplicitParam(name = "supplierCode",dataTypeClass = String.class , value ="供应商编号")})
    @GetMapping("/findBySupplierCode")
    public List<MaterialDetailVo> findBySupplierCode (@RequestHeader("token") String token,@RequestParam String foreignCode,@RequestParam String supplierCode) {
        return materialDetailService.findBySupplierCode(foreignCode,supplierCode);
    }





}
