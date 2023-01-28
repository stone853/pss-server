package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.OrderSend;
import com.flong.springboot.modules.entity.dto.OrderSendDto;
import com.flong.springboot.modules.entity.dto.OrderSendMaterialDto;
import com.flong.springboot.modules.entity.dto.UpdSendStatus;
import com.flong.springboot.modules.entity.vo.MaterialDetailSendOrderVo;
import com.flong.springboot.modules.entity.vo.OrderSendVo;
import com.flong.springboot.modules.service.OrderSendService;
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
 * @Description:发货控制层
 */
@Api(tags = "发货单")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/order/send")
public class OrderSendController {

    @Autowired
    private OrderSendService orderSendService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 添加
     */
    @ApiOperation("增加发货信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "orderSend",dataTypeClass = OrderSend.class , value ="")})
    @PostMapping("/v1/add")
    public OrderSend add(@RequestHeader("token") String token,@RequestBody OrderSend t) {
        FileBean f = new FileBean();
        t.setFileC(f.fileBeanListToString(t.getFileBeanList()));

        return orderSendService.insert(t);
    }

    /**
     * 修改
     * @param orderSend
     */
    @ApiOperation("只修改发货单，不修改明细")
    @PutMapping("/updateById")
    public void updateOrInsert(@RequestHeader("token") String token,@RequestBody OrderSend orderSend) {
        orderSendService.updateById(orderSend);
    }


    /**
     * 修改
     * @param updSendStatus
     */
    @ApiOperation("订单验收或驳回 sendStatus 3 验收 2 驳回")
    @PutMapping("/updSendStatus")
    public void updSendStatus(@RequestHeader("token") String token,@RequestBody UpdSendStatus updSendStatus) {
        updSendStatus.setUserId(UserHelper.getUserId(token));
        orderSendService.updSendStatus(updSendStatus);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestHeader("token") String token,@RequestBody List<String> ids) {
        orderSendService.removeByIds(ids);
    }

    /**
     * 查询合同详情
     *
     * @param id
     */
    @GetMapping("/getOne")
    public OrderSendVo getOne(@RequestHeader("token") String token, @RequestParam("id") int id) {
        return orderSendService.getOneById(id);
    }

//    /**
//     * 发货分页，参数有多个使用下标索引进行处理.如果有两个参数(如发货名和地址)：conditionList[0].fieldName=OrderSendName、 conditionList[0].fieldName=address
//     * 未转码请求分页地址: http://localhost:7011/OrderSend/page?conditionList[0].fieldName=OrderSendName&conditionList[0].operation=LIKE&conditionList[0].value=周
//     * 已转码请求分页地址: http://localhost:7011/OrderSend/page?conditionList[0].fieldName=OrderSendName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
//     * @return
//     */
    @PostMapping("/page")
    public IPage<OrderSendVo> page(@RequestHeader("token") String token, @RequestBody OrderSendDto orderSendDto) {
        return orderSendService.pageList(orderSendDto);
    }


    @ApiOperation("查询订单下的物料明细（订单数量-已发数量-剩余数量）")
    @PostMapping("/getOrderMaterial")
    public List<MaterialDetailSendOrderVo> getOrderMaterial(@RequestHeader("token") String token, @RequestBody OrderSendMaterialDto orderSendMaterialDto) {
        return orderSendService.getOrderMaterial(orderSendMaterialDto);
    }


    @ApiOperation("查询发货单下的物料明细（订单数量-已发数量-剩余数量）")
    @PostMapping("/getSendMaterial")
    public List<MaterialDetailSendOrderVo> getSendMaterial(@RequestHeader("token") String token, @RequestBody OrderSendMaterialDto orderSendMaterialDto) {
        return orderSendService.getSendMaterial(orderSendMaterialDto);
    }


    /**
     * 查询订单下送货单是否都已完成送货
     *
     * @param orderCode
     */
    @ApiOperation("是否全部送货")
    @GetMapping("/isSendAll")
    public boolean isSendAll(@RequestHeader("token") String token, @RequestParam("orderCode") String orderCode) {
        return orderSendService.isSendAll(orderCode);
    }

    @ApiOperation("是否全部验收")
    @GetMapping("/isCheckSendAll")
    public boolean isCheckSendAll(@RequestHeader("token") String token, @RequestParam("orderCode") String orderCode) {
        return orderSendService.isCheckSendAll(orderCode);
    }


}
