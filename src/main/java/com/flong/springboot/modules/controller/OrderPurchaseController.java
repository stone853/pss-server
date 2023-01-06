package com.flong.springboot.modules.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.OrderPurchase;
import com.flong.springboot.modules.entity.dto.OrderPurchaseDto;
import com.flong.springboot.modules.entity.vo.OrderPurchaseVo;
import com.flong.springboot.modules.service.OrderPurchaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:订单控制层
 */
@Api(tags = "订单信息")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/orderpur")
public class OrderPurchaseController {

    @Autowired
    private OrderPurchaseService orderPurchaseService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 添加
     */
    @ApiOperation("增加订单信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "OrderPurchase",dataTypeClass = OrderPurchase.class , value ="")})
    @PostMapping("/v1/add")
    public int add(@RequestHeader("token") String token,@Validated @RequestBody OrderPurchase t) {
        List<FileBean> fileBeanList = t.getFileBeanList();
        if (fileBeanList !=null && fileBeanList.size() > 0) {
            t.setFileC(JSONArray.toJSONString(fileBeanList));
        }
        t.setApplicant(request.getSession().getAttribute("userName").toString());
        return orderPurchaseService.insert(t);
    }

    /**
     * 修改
     * @param orderPurchase
     */
    @PutMapping("/updateById")
    public void updateOrInsert(@RequestHeader("token") String token,@RequestBody OrderPurchase orderPurchase) {
        orderPurchase.setApplicant(request.getSession().getAttribute("userName").toString());
        orderPurchaseService.update(orderPurchase);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestHeader("token") String token,@RequestBody List<String> ids) {
        orderPurchaseService.removeByIds(ids);
    }

    /**
     * 查询合同详情
     *
     * @param id
     */
    @GetMapping("/getOne")
    public OrderPurchaseVo getOne(@RequestHeader("token") String token, @RequestParam("id") int id) {
        return orderPurchaseService.getOneById(id);
    }

//    /**
//     * 订单分页，参数有多个使用下标索引进行处理.如果有两个参数(如订单名和地址)：conditionList[0].fieldName=orderPurchaseName、 conditionList[0].fieldName=address
//     * 未转码请求分页地址: http://localhost:7011/orderPurchase/page?conditionList[0].fieldName=orderPurchaseName&conditionList[0].operation=LIKE&conditionList[0].value=周
//     * 已转码请求分页地址: http://localhost:7011/orderPurchase/page?conditionList[0].fieldName=orderPurchaseName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
//     * @return
//     */
    @PostMapping("/page")
    public IPage<OrderPurchaseVo> page(@RequestHeader("token") String token, @RequestBody OrderPurchaseDto orderPurchaseDto) {
        return orderPurchaseService.pageList(orderPurchaseDto);
    }



}
