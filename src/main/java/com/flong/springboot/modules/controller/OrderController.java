package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.model.OrderTypeEnum;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.Order;
import com.flong.springboot.modules.entity.dto.OrderDto;
import com.flong.springboot.modules.entity.vo.OrderCountVo;
import com.flong.springboot.modules.entity.vo.OrderVo;
import com.flong.springboot.modules.service.OrderService;
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
 * @Description:订单控制层
 */
@Api(tags = "订单")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/orderpur")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 添加
     */
    @ApiOperation("添加采购单")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "Order",dataTypeClass = Order.class , value ="")})
    @PostMapping("/v1/add")
    public Order add(@RequestHeader("token") String token,@Validated @RequestBody Order t) {
        t.setOrderType(OrderTypeEnum.IN.getCode());
        t.setApplicant(UserHelper.getUserId(token));
        return orderService.insert(t);
    }


    @ApiOperation("添加出库单")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "Order",dataTypeClass = Order.class , value ="")})
    @PostMapping("/v1/addOutOrder")
    public Order addOutOrder(@RequestHeader("token") String token,@Validated @RequestBody Order t) {
        t.setOrderType(OrderTypeEnum.OUT.getCode());
        t.setApplicant(UserHelper.getUserId(token));
        return orderService.insert(t);
    }

    /**
     * 修改
     * @param order
     */
    @ApiOperation("只修改订单，不修改明细")
    @PutMapping("/updateById")
    public void updateOrInsert(@RequestHeader("token") String token,@Validated @RequestBody Order order) {
        order.setApplicant(UserHelper.getUserId(token));
        orderService.updateById(order);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestHeader("token") String token,@RequestBody List<String> ids) {
        orderService.removeByIds(ids);
    }

    /**
     * 查询合同详情
     *
     * @param id
     */
    @GetMapping("/getOne")
    public OrderVo getOne(@RequestHeader("token") String token, @RequestParam("id") int id) {
        return orderService.getOneById(id);
    }

    /**
     * 查询合同详情
     *
     * @param orderCode
     */
    @GetMapping("/getOneByOrderCode")
    public OrderVo getOneByOrderCode(@RequestHeader("token") String token, @RequestParam("orderCode") String orderCode) {
        return orderService.getOneByOrderCode(UserHelper.getUserId(token),orderCode);
    }

//    /**
//     * 订单分页，参数有多个使用下标索引进行处理.如果有两个参数(如订单名和地址)：conditionList[0].fieldName=orderPurchaseName、 conditionList[0].fieldName=address
//     * 未转码请求分页地址: http://localhost:7011/orderPurchase/page?conditionList[0].fieldName=orderPurchaseName&conditionList[0].operation=LIKE&conditionList[0].value=周
//     * 已转码请求分页地址: http://localhost:7011/orderPurchase/page?conditionList[0].fieldName=orderPurchaseName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
//     * @return
//     */
    @ApiOperation("查询采购订单")
    @PostMapping("/page")
    public IPage<OrderVo> page(@RequestHeader("token") String token, @RequestBody OrderDto orderDto) {
        orderDto.setOrderType(OrderTypeEnum.IN.getCode());
        orderDto.setUserId(UserHelper.getUserId(token));
        return orderService.pagePurList(orderDto);
    }

    @ApiOperation("查询各类订单条数")
    @PostMapping("/getOrderCount")
    public List<OrderCountVo> getOrderCount(@RequestHeader("token") String token) {
        return orderService.getOrderCount(UserHelper.getUserId(token));
    }


    @ApiOperation("查询出库单")
    @PostMapping("/pageOutOrder")
    public IPage<OrderVo> pageOutOrder(@RequestHeader("token") String token, @RequestBody OrderDto orderDto) {
        orderDto.setUserId(UserHelper.getUserId(token));
        orderDto.setOrderType(OrderTypeEnum.OUT.getCode());
        return orderService.pageOutList(orderDto);
    }



}
