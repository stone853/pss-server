package com.flong.springboot.modules.entity;

import lombok.Data;

@Data
public class SmsBean {
    private String ecName;
    private String apId;
    private String mobiles;
    private String content;
    private String sign;
    private String addSerial;
    private String mac;
    private String secretKey;
}
