package com.flong.springboot.modules.controller;



import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.CommonConstant;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.PssProcess;
import com.flong.springboot.modules.entity.Role;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.dto.PssProcessDto;
import com.flong.springboot.modules.entity.vo.ProcessInfo;
import com.flong.springboot.modules.service.PssProcessService;
import com.flong.springboot.modules.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:liangjl
 * @Date:2020-08-16
 * @Description:流程控制层
 */
@Api(tags = "流程")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/pss/process")
public class TssProcessController {

    @Autowired
    private PssProcessService pssProcessService;

    
    /**
     * 审批销售合同流程
     * @param pssProcessDto
     */
    @ApiOperation("销售合同审批")
    @PutMapping("/subProcessContractSale")
    public void subProcessContractSale(@RequestHeader("token") String token, @Validated @RequestBody PssProcessDto pssProcessDto) {
        pssProcessDto.setUserId(UserHelper.getUserId(token));
        pssProcessService.contractSaleProcess(CommonConstant.CONTRACT_SALE_PROCESS_TYPE,pssProcessDto);
    }

    /**
     * 审批采购合同流程
     * @param pssProcessDto
     */
    @ApiOperation("采购合同审批")
    @PutMapping("/subProcessContractPur")
    public void subProcessContractPur(@RequestHeader("token") String token, @Validated @RequestBody PssProcessDto pssProcessDto) {
        pssProcessDto.setUserId(UserHelper.getUserId(token));
        pssProcessService.contractPurProcess(CommonConstant.CONTRACT_PUR_PROCESS_TYPE,pssProcessDto);
    }

    /**
     * 审批采购订单流程
     * @param pssProcessDto
     */
    @ApiOperation("采购订单审批")
    @PutMapping("/subProcessOrderPur")
    public void subProcessOrderPur(@RequestHeader("token") String token, @Validated @RequestBody PssProcessDto pssProcessDto) {
        pssProcessDto.setUserId(UserHelper.getUserId(token));
        pssProcessService.orderPurProcess(CommonConstant.ORDER_PUR_TYPE,pssProcessDto);
    }

    /**
     * 审批出库订单流程
     * @param token
     * @param pssProcessDto
     */
    @ApiOperation("出库单审批")
    @PutMapping("/subProcessOrderOut")
    public void subProcessOrderOut(@RequestHeader("token") String token, @Validated @RequestBody PssProcessDto pssProcessDto) {
        pssProcessDto.setUserId(UserHelper.getUserId(token));
        pssProcessService.orderOutProcess(CommonConstant.ORDER_OUT_TYPE,pssProcessDto);
    }

    @ApiOperation("获取流程信息")
    @GetMapping("/getProcessInfo")
    public ProcessInfo getProcessInfo(@RequestHeader("token") String token, @RequestParam("processId") String processId) {
        return pssProcessService.getProcessInfo(processId);
    }



}
