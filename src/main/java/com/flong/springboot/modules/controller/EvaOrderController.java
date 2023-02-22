package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.EvaOrder;
import com.flong.springboot.modules.entity.dto.EvaOrderDto;
import com.flong.springboot.modules.entity.vo.EvaOrderVo;
import com.flong.springboot.modules.service.EvaOrderService;
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
 * @Description:订单评价控制层
 */
@Api(tags = "订单评价")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/eva/order")
public class EvaOrderController {

    @Autowired
    EvaOrderService evaOrderService;

    /**
     * 添加
     */
    @ApiOperation("评价订单")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "EvaOrder",dataTypeClass = EvaOrder.class , value ="")})
    @PostMapping("/v1/add")
    public EvaOrder add(@RequestHeader("token") String token,@Validated @RequestBody EvaOrder t) {
        t.setOptUser(UserHelper.getUserId(token));
        return evaOrderService.insert(t);
    }

    /**
     * 修改
     * @param evaOrder
     */
    @ApiOperation("修改订单评价")
    @PutMapping("/updateById")
    public void updateOrInsert(@RequestHeader("token") String token,@Validated @RequestBody EvaOrder evaOrder) {
        evaOrder.setOptUser(UserHelper.getUserId(token));
        evaOrderService.update(evaOrder);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @ApiOperation("删除订单评价")
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestHeader("token") String token,@RequestBody List<String> ids) {
        evaOrderService.removeByIds(ids);
    }

    /**
     * 查询合同详情
     *
     * @param id
     */
    @ApiOperation("获取单个评价详情")
    @GetMapping("/v1/getOneById")
    public EvaOrderVo getOneById(@RequestHeader("token") String token, @RequestParam("id") int id) {
        return evaOrderService.getOneById(id);
    }

//    /**
//     * 订单评价分页，参数有多个使用下标索引进行处理.如果有两个参数(如订单评价名和地址)：conditionList[0].fieldName=OrderSendName、 conditionList[0].fieldName=address
//     * 未转码请求分页地址: http://localhost:7011/OrderSend/page?conditionList[0].fieldName=OrderSendName&conditionList[0].operation=LIKE&conditionList[0].value=周
//     * 已转码请求分页地址: http://localhost:7011/OrderSend/page?conditionList[0].fieldName=OrderSendName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
//     * @return
//     */
    @PostMapping("/page")
    public IPage<EvaOrderVo> page(@RequestHeader("token") String token, @RequestBody EvaOrderDto evaOrderDto) {
        return evaOrderService.pageList(evaOrderDto);
    }





}
