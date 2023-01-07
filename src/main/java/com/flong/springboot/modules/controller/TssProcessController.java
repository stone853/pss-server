package com.flong.springboot.modules.controller;



import com.flong.springboot.core.constant.CommonConstant;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.PssProcess;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.dto.PssProcessDto;
import com.flong.springboot.modules.service.PssProcessService;
import com.flong.springboot.modules.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:liangjl
 * @Date:2020-08-16
 * @Description:流程控制层
 */
@Api(tags = "流程信息")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/pss/process")
public class TssProcessController {

    @Autowired
    private PssProcessService pssProcessService;

    
    /**
     * 提交销售合同流程
     * @param pssProcessDto
     */
    @PutMapping("/subProcessContractSale")
    public void subProcessContractSale(@RequestHeader("token") String token, @Validated @RequestBody PssProcessDto pssProcessDto) {
        pssProcessService.subForApprove(CommonConstant.CONTRACT_SALE_PROCESS_TYPE,pssProcessDto);
    }



}
