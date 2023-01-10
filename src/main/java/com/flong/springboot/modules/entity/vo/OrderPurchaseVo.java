package com.flong.springboot.modules.entity.vo;


import com.alibaba.fastjson.JSONArray;
import com.flong.springboot.modules.entity.OrderPurchase;

public class OrderPurchaseVo extends OrderPurchase {
        private String supplierName;
        private String orderStatusNote;
        private String sendTypeNote;
        private String contractName;


        private String payTypeNote;

    public void setPayTypeNote(String payTypeNote) {
        this.payTypeNote = payTypeNote;
    }

    public String getPayTypeNote() {
        return payTypeNote;
    }

    private JSONArray jsonArray;


    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setOrderStatusNote(String orderStatusNote) {
        this.orderStatusNote = orderStatusNote;
    }

    public String getOrderStatusNote() {
        return orderStatusNote;
    }

    public void setSendTypeNote(String sendTypeNote) {
        this.sendTypeNote = sendTypeNote;
    }

    public String getSendTypeNote() {
        return sendTypeNote;
    }


    public void setContractName(String contractName) {
        this.contractName = contractName;
    }


    public String getContractName() {
        return contractName;
    }
}
