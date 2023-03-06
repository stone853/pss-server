package com.flong.springboot.modules.task;


import com.alibaba.fastjson.JSON;
import com.flong.springboot.base.utils.HttpUtil;
import com.flong.springboot.base.utils.MD5Utils;
import com.flong.springboot.modules.entity.SmsBean;
import com.flong.springboot.modules.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
    private void sendMas() throws UnsupportedEncodingException {
//        log.info("=====================发送短信测试==========================");
//        HttpUtil httpUtil = new HttpUtil();
//        SmsBean sms = new SmsBean();
//        String[] params = {"1234"};
//        sms.setEcName("湖北金建商贸有限公司");
//        sms.setApId("jjjxc");
//        sms.setSecretKey("zaq1@WSX");
//        sms.setTemplateId("cdc883d266dc406bab803248903047bf");
//        sms.setMobiles("18062109527");
//        sms.setParams(JSON.toJSONString(params));
//        sms.setSign("yCRjA9VuP");
//        sms.setAddSerial("");
//
//
//        StringBuffer sb = new StringBuffer();
//        sb.append("湖北金建商贸有限公司");
//        sb.append(sms.getApId());
//        sb.append(sms.getSecretKey());
//        sb.append(sms.getTemplateId());
//        sb.append(sms.getMobiles());
//        sb.append(sms.getParams());
//        sb.append(sms.getSign());
//        sb.append(sms.getAddSerial());
//
//
//        //sms.setMac("2df5d6cac3422539035a00ecf3faef15");
//        //log.info(sb.toString());
//        try {
//            sms.setMac(MD5Utils.getInstance().encrypt(sb.toString()));
//            log.info(MD5Utils.getInstance().encrypt(sb.toString()));
//        } catch (Exception e) {
//
//        }
//
//        String param = JSON.toJSONString(sms);
//        log.info("param:"+param);
//
//        String encode = Base64.getEncoder().encodeToString(param.getBytes("UTF-8"));
//        log.info("encode:"+encode);
//
//       // encode="eyJlY05hbWUiOiJcdTZlNTZcdTUzMTdcdTkxZDFcdTVlZmFcdTU1NDZcdThkMzhcdTY3MDlcdTk2NTBcdTUxNmNcdTUzZjgiLCJhcElkIjoiampqeGMiLCJzZWNyZXRLZXkiOiJ6YXExQFdTWCIsInNpZ24iOiJ5Q1JqQTlWdVAiLCJhZGRTZXJpYWwiOiIiLCJtb2JpbGVzIjoiMTgwNjIxMDk1MjciLCJ0ZW1wbGF0ZUlkIjoiY2RjODgzZDI2NmRjNDA2YmFiODAzMjQ4OTAzMDQ3YmYiLCJwYXJhbXMiOiJbXCIxMjM0XCJdIiwibWFjIjoiMmRmNWQ2Y2FjMzQyMjUzOTAzNWEwMGVjZjNmYWVmMTUifQ==";
//
//        httpUtil.sendPost("http://112.35.10.201:5992/sms/tmpsubmit",encode);

    }

    public static void main (String args[]) {
        TimerTask t = new TimerTask();
        try {
            t.sendMas();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String cnToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        StringBuilder returnStr = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
            returnStr.append("\\u").append(Integer.toString(chars[i], 16));
        }
        return returnStr.toString();
    }
}