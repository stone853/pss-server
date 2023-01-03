package com.flong.springboot.modules.controller;


import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.EvaIndex;
import com.flong.springboot.modules.entity.EvaScheme;
import com.flong.springboot.modules.entity.dto.EvaSchemeDto;
import com.flong.springboot.modules.service.EvaIndexService;
import com.flong.springboot.modules.service.EvaSchemeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
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
@Api(tags = "方案指标信息")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/evaindex")
public class EvaIndexController {

    @Autowired
    private EvaIndexService evaIndexService;

    @Autowired
    private HttpServletRequest request;


    @GetMapping("/list")
    public List<EvaIndex> list (@RequestHeader("token") String token,@RequestParam String foreignCode) {
        return evaIndexService.list(foreignCode);
    }


}
