package com.flong.springboot.modules.controller;


import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.vo.MaterialDetailLogVo;
import com.flong.springboot.modules.service.MaterialDetailLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:物料流水明细控制层
 */
@Api(tags = "物料流水明细信息")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/material/detail/log")
public class MaterialDetailLogController {

    @Autowired
    private MaterialDetailLogService materialDetailLogService;

    @Autowired
    private HttpServletRequest request;


    @GetMapping("/v1/list")
    public List<MaterialDetailLogVo> list (@RequestHeader("token") String token, @RequestParam String foreignCode) {
        return materialDetailLogService.list(foreignCode);
    }




}
