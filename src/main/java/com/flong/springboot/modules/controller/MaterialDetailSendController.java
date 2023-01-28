package com.flong.springboot.modules.controller;


import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.vo.MaterialDetailSendVo;
import com.flong.springboot.modules.service.MaterialDetailSendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:发货单物料明细
 */
@Api(tags = "发货单物料明细")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/material/send")
public class MaterialDetailSendController {

    @Autowired
    private MaterialDetailSendService materialDetailSendService;


    @ApiOperation("发货单物料明细")
    @GetMapping("/getByOrderSendCode")
    public List<MaterialDetailSendVo> getByOrderSendCode (@RequestHeader("token") String token, @RequestParam String foreignCode) {
        return materialDetailSendService.getByOrderSendCode(foreignCode);
    }




}
