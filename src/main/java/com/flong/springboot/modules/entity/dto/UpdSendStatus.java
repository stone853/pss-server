package com.flong.springboot.modules.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.MaterialDetailSend;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdSendStatus {
    @JsonIgnore
    private String userId;

    @NotNull(message =  "发送单编号不能为空" )
    private String orderSendCode;

    @NotNull(message =  "修改状态不能为空" )
    private String sendStatus;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getSendStatus() {
        return sendStatus;
    }


    public void setOrderSendCode(String orderSendCode) {
        this.orderSendCode = orderSendCode;
    }

    public String getOrderSendCode() {
        return orderSendCode;
    }
}