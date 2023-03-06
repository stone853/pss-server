package com.flong.springboot.core.mas;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.flong.springboot.base.utils.HttpUtil;
import com.flong.springboot.base.utils.MD5Utils;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.modules.entity.MobileVerif;
import com.flong.springboot.modules.entity.SmsBean;
import com.flong.springboot.modules.mapper.MobileVerifMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Random;

@Slf4j
@Component
public class SendMas {

    @Autowired
    MobileVerifMapper mobileVerifMapper;

    public String sendVerifCode(String mobile) {
        String verifCode = getStringRandom();
        try {
            log.info("=====================发送短信测试==========================");
            HttpUtil httpUtil = new HttpUtil();
            SmsBean sms = new SmsBean();

            String[] params = {verifCode};
            sms.setEcName("湖北金建商贸有限公司");
            sms.setApId("jjjxc");
            sms.setSecretKey("zaq1@WSX");
            sms.setTemplateId("cdc883d266dc406bab803248903047bf");
            sms.setMobiles(mobile);
            sms.setParams(JSON.toJSONString(params));
            sms.setSign("yCRjA9VuP");
            sms.setAddSerial("");


            StringBuffer sb = new StringBuffer();
            sb.append("湖北金建商贸有限公司");
            sb.append(sms.getApId());
            sb.append(sms.getSecretKey());
            sb.append(sms.getTemplateId());
            sb.append(sms.getMobiles());
            sb.append(sms.getParams());
            sb.append(sms.getSign());
            sb.append(sms.getAddSerial());

        //sms.setMac("2df5d6cac3422539035a00ecf3faef15");

            sms.setMac(MD5Utils.getInstance().encrypt(sb.toString()));
            //log.info(MD5.getInstance().encrypt(sb.toString()));

            String param = JSON.toJSONString(sms);
            log.info("param:"+param);

            String encode = Base64.getEncoder().encodeToString(param.getBytes("UTF-8"));
            log.info("encode:"+encode);

            // encode="eyJlY05hbWUiOiJcdTZlNTZcdTUzMTdcdTkxZDFcdTVlZmFcdTU1NDZcdThkMzhcdTY3MDlcdTk2NTBcdTUxNmNcdTUzZjgiLCJhcElkIjoiampqeGMiLCJzZWNyZXRLZXkiOiJ6YXExQFdTWCIsInNpZ24iOiJ5Q1JqQTlWdVAiLCJhZGRTZXJpYWwiOiIiLCJtb2JpbGVzIjoiMTgwNjIxMDk1MjciLCJ0ZW1wbGF0ZUlkIjoiY2RjODgzZDI2NmRjNDA2YmFiODAzMjQ4OTAzMDQ3YmYiLCJwYXJhbXMiOiJbXCIxMjM0XCJdIiwibWFjIjoiMmRmNWQ2Y2FjMzQyMjUzOTAzNWEwMGVjZjNmYWVmMTUifQ==";

            httpUtil.sendPost("http://112.35.10.201:5992/sms/tmpsubmit",encode);

            insertOrUpdateVerifCode(mobile,verifCode);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return verifCode;
    }

    private void insertOrUpdateVerifCode (String mobile,String verifCode) {
        QueryWrapper<MobileVerif> q = new QueryWrapper<>();
        q.eq("mobile",mobile);
        q.last("limit 1");
        MobileVerif m = mobileVerifMapper.selectOne(q);
        if (m == null) {
            m = new MobileVerif();
            m.setVerifCode(verifCode);
            m.setMobile(mobile);
            m.setOptTime(UserHelper.getDateTime());
            mobileVerifMapper.insert(m);
        } else {
            UpdateWrapper<MobileVerif> u = new UpdateWrapper<>();
            u.set("verif_code",verifCode);
            u.set("opt_time",UserHelper.getDateTime());
            u.eq("mobile",mobile);
            mobileVerifMapper.update(m,u);
        }
    }

    private String getStringRandom() {
        Random random = new Random();
        //把随机生成的数字转成字符串
        String str = String.valueOf(random.nextInt(9));
        for (int i = 0; i < 5; i++) {
            str += random.nextInt(9);
        }
        return str;
    }

    public static void main (String args[]) {
        SendMas s = new SendMas();
        s.sendVerifCode("18062109527");
    }
}
