package com.flong.springboot.modules.entity.vo;


import com.alibaba.fastjson.JSONArray;
import com.flong.springboot.modules.entity.OrderSend;

import java.util.List;

public class OrderSendVo extends OrderSend {

    private JSONArray jsonArray;

    private JSONArray acptJsonArray;

    private String sendStatusName;


    private String driverName;

    private String carNo;

    private String telNo;

    private String carInfo;

    private String shipAddr;

    private String acptFileC;

    private double priceTax;

    private String acptResult;

    private String userName;


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setAcptResult(String acptResult) {
        this.acptResult = acptResult;
    }

    public String getAcptResult() {
        return acptResult;
    }

    public void setPriceTax(double priceTax) {
        this.priceTax = priceTax;
    }

    public double getPriceTax() {
        return priceTax;
    }

    public void setAcptFileC(String acptFileC) {
        this.acptFileC = acptFileC;
    }

    public String getAcptFileC() {
        return acptFileC;
    }

    private List<MaterialDetailSendVo> materialSendVo;

    public void setAcptJsonArray(JSONArray acptJsonArray) {
        this.acptJsonArray = acptJsonArray;
    }

    public JSONArray getAcptJsonArray() {
        return acptJsonArray;
    }



    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public String getCarInfo() {
        return carInfo;
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
