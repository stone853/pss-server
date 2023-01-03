package com.flong.springboot.modules.controller;


import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.EvaIndex;
import com.flong.springboot.modules.entity.MaterialDetail;
import com.flong.springboot.modules.service.EvaIndexService;
import com.flong.springboot.modules.service.MaterialDetailService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:方案控制层
 */
@Api(tags = "物料明细信息")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/materialdetail")
public class MaterialDetailController {

    @Autowired
    private MaterialDetailService materialDetailService;

    @Autowired
    private HttpServletRequest request;


    @GetMapping("/list")
    public List<MaterialDetail> list (@RequestHeader("token") String token,@RequestParam String foreignCode) {
        return materialDetailService.list(foreignCode);
    }


}
