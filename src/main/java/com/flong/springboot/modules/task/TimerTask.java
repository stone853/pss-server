package com.flong.springboot.modules.task;


import com.alibaba.fastjson.JSON;
import com.flong.springboot.base.utils.HttpUtil;
import com.flong.springboot.base.utils.MD5Utils;
import com.flong.springboot.modules.entity.SmsBean;
import com.flong.springboot.modules.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Base64;


@Slf4j
@Component
@EnableScheduling    //开启定时任务
public class TimerTask {
    @Autowired
    OrderService orderService;

    //容器启动后,延迟10秒后再执行一次定时器,以后每10秒再执行一次该定时器。
    @Scheduled(initialDelay = 10000, fixedRate = 1000*60*60*24)
    private void myTasks3() {
        log.info("定时任务");
        orderService.pushEvaOrder();
    }

    @Scheduled(initialDelay = 10000, fixedRate = 1000*60*60*24)
    private void sendMas() {
        log.info("=====================发送短信测试==========================");
        HttpUtil httpUtil = new HttpUtil();
        SmsBean sms = new SmsBean();
        sms.setEcName("湖北金建商贸有限公司");
        sms.setApId("jjjxc");
        sms.setSecretKey("zaq1@WSX");
        sms.setMobiles("18062109527");
        sms.setContent("测试");
        sms.setSign("yCRjA9VuP");
        sms.setAddSerial("");

        StringBuilder sb = new StringBuilder();
        sb.append(sms.getEcName());
        sb.append(sms.getApId());
        sb.append(sms.getSecretKey());
        sb.append(sms.getMobiles());
        sb.append(sms.getContent());
        sb.append(sms.getSign());
        sb.append(sms.getAddSerial());

        String selfMac = MD5Utils.encrypt(sb.toString());
        log.info("selfMac:"+selfMac);
        sms.setMac(selfMac);
        String param = JSON.toJSONString(sms);
        log.info("param:"+param);

        String encode = Base64.getEncoder().encodeToString(param.getBytes());
        log.info("encode:"+encode);

        httpUtil.sendPost("http://112.35.10.201:5992/sms/norsubmit",encode);

    }
}