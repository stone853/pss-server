package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.EvaSetting;
import com.flong.springboot.modules.service.EvaSettingService;
import com.flong.springboot.modules.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:liangjl
 * @Date:2020-08-16
 * @Description:评价控制层
 */
@Api(tags = "评价设置")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/evaSetting")
public class EvaSettingController {

    @Autowired
    private EvaSettingService evaSettingService;


    @Autowired
    private OrderService orderService;

    /**
     * 修改
     * @param evaSetting
     */
    @PutMapping("/set")
    public void set(@RequestBody EvaSetting evaSetting) {
        QueryWrapper<EvaSetting> q = new QueryWrapper();
        q.last("limit 1");
        EvaSetting e = evaSettingService.getOne(q);
        if (e == null) {
            evaSettingService.save(evaSetting);
        } else {
            UpdateWrapper<EvaSetting> up = new UpdateWrapper();
            up.set("after_days",evaSetting.getAfterDays());
            up.set("cycle",evaSetting.getCycle());
            evaSettingService.update(up);
        }

        //推送
        orderService.pushEvaOrder();
    }




    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        evaSettingService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     */
    @GetMapping("/getOne")
    public EvaSetting getOne(@RequestHeader("token") String token) {
        QueryWrapper<EvaSetting> q = new QueryWrapper();
        q.last("limit 1");
        return evaSettingService.getOne(q);
    }


}
