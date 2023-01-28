package com.flong.springboot.modules.entity.vo;


import com.alibaba.fastjson.JSONArray;

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

    private String carInfo;

    private String acptTime;

    private String sendTime;

    private String shipAddr;

    private List<MaterialDetailSendVo> materialSendVo;

    public void setAcptTime(String acptTime) {
        this.acptTime = acptTime;
    }

    public String getAcptTime() {
        return acptTime;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setShipAddr(String shipAddr) {
        this.shipAddr = shipAddr;
    }

    public String getShipAddr() {
        return shipAddr;
    }

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
