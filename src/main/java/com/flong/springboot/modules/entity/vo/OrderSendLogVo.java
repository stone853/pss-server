package com.flong.springboot.modules.entity.vo;


import com.flong.springboot.modules.entity.OrderSendLog;

public class OrderSendLogVo extends OrderSendLog {
    private String optName;
    private String sendStatusName;
    private String logTitle;

    public void setLogTitle(String logTitle) {
        this.logTitle = logTitle;
    }

    public String getLogTitle() {
        return logTitle;
    }

    public void setSendStatusName(String sendStatusName) {
        this.sendStatusName = sendStatusName;
    }

    public String getSendStatusName() {
        return sendStatusName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }

    public String getOptName() {
        return optName;
    }
}