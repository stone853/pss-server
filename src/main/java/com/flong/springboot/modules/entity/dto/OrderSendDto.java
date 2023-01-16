package com.flong.springboot.modules.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderSendDto {


    private Page page;

    private String sendStatus;

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    private String orderCode;


    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public OrderSendDto setPage(Page page) {
        this.page = page;
        return this;
    }

    public Page getPage() {
        return this.page;
    }

}