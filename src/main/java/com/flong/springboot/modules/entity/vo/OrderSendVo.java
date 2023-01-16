package com.flong.springboot.modules.entity.vo;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.MaterialDetailSend;
import com.flong.springboot.modules.entity.OrderPurchase;
import com.flong.springboot.modules.entity.OrderSend;

import java.util.List;

public class OrderSendVo {

    private Integer id;

    private String orderSendCode;
    private String orderCode;

    private String driverCode;

    private String estimatedDeliveryTime;

    private String remark;

    private String fileC;

    private String sendStatus;


    private JSONArray jsonArray;

    private String sendStatusName;


    private String driverName;

    private String carNo;

    private String telNo;

    private List<MaterialDetailSendVo> materialSendVo;

    public OrderSendVo setMaterialSendVo(List<MaterialDetailSendVo> materialSendVo) {
        this.materialSendVo = materialSendVo;
        return this;
    }

    public List<MaterialDetailSendVo> getMaterialSendVo() {
        return materialSendVo;
    }


    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarNo() {
        return carNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getTelNo() {
        return telNo;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode == null ? null : driverCode.trim();
    }

    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime == null ? null : estimatedDeliveryTime.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getFileC() {
        return fileC;
    }

    public void setFileC(String fileC) {
        this.fileC = fileC == null ? null : fileC.trim();
    }






    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setSendStatusName(String sendStatusName) {
        this.sendStatusName = sendStatusName;
    }

    public String getSendStatusName() {
        return sendStatusName;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }
}
