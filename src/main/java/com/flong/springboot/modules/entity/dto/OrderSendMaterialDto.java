package com.flong.springboot.modules.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderSendMaterialDto {



    private String orderCode;

    private List<String> materialCode;

    private String orderSendCode;

    public OrderSendMaterialDto setOrderSendCode(String orderSendCode) {
        this.orderSendCode = orderSendCode;
        return this;
    }

    public String getOrderSendCode() {
        return orderSendCode;
    }

    public OrderSendMaterialDto setOrderCode(String orderCode) {
        this.orderCode = orderCode;
        return this;
    }

    public String getOrderCode() {
        return orderCode;
    }


    public void setMaterialCode(List<String> materialCode) {
        this.materialCode = materialCode;
    }

    public List<String> getMaterialCode() {
        return materialCode;
    }
}