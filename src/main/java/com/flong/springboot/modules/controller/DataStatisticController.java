package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.utils.MD5Utils;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.DataStatistic;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.entity.vo.IndexDataVo;
import com.flong.springboot.modules.entity.vo.UserVo;
import com.flong.springboot.modules.service.DataStatisticService;
import com.flong.springboot.modules.service.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:liangjl
 * @Date:2020-08-16
 * @Description:数据统计
 */
@Api(tags = "数据统计")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/data/statistic")
public class DataStatisticController {

    @Autowired
    private DataStatisticService dsService;

    @Autowired
    private UserService userService;

    /**
     * 通过指定Id进行查询
     *
     */
    @ApiOperation("大屏数据")
    @GetMapping("/v1/BIData")
    public Map<String,List> BIData(@RequestHeader("token") String token) {
        Map map = new HashMap();
        List<Map> salesRanking = dsService.salesRanking();
        List<Map> materialTop5 = dsService.materialTop5();
        List<Map> salesAnalysis = dsService.salesAnalysis();
        List<Map> sendRecords = dsService.sendRecords();
        List<Map> sendMaterialTop5 = dsService.sendMaterialTop5();
        List<Map> customerInfo = dsService.customerInfo();
        List<Map> supplierCount = dsService.supplierCount();
        IndexDataVo indexDataVo = userService.findIndexData("1", UserHelper.getUserId(token));

        double amount = 0;
        double totalSales=0;
        //发送前五特殊处理
        for (int i =0; i < materialTop5.size();i ++) {
            Map<String,Object> top = materialTop5.get(i);
            if(top !=null && top.get("totalSales") != null) {
                totalSales = Double.parseDouble(top.get("totalSales").toString());
            }
            amount += Double.parseDouble(top.get("amount").toString());
        }
        Map<String,Object> other = new HashMap<>();
        other.put("materialCode","other");
        other.put("materialName","其他");
        other.put("amount",totalSales-amount);
        other.put("totalSales",totalSales);
        materialTop5.add(other);
        //特殊处理完毕


        map.put("salesRanking",salesRanking); //客户销售金额排行
        map.put("materialTop5",materialTop5); //销售材料金额前五占比
        map.put("salesAnalysis",salesAnalysis);//销售合同分析
        map.put("sendRecords",sendRecords); //发货记录
        map.put("sendMaterialTop5",sendMaterialTop5);//发货前五名物料
        map.put("indexDataVo",indexDataVo);//发货前五名物料
        map.put("customerInfo",customerInfo);//客户信息
        map.put("supplierCount",supplierCount);//供应商信息
        return map;
    }

}
