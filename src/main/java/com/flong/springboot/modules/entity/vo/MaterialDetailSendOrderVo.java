package com.flong.springboot.modules.entity.vo;

import com.alibaba.fastjson.JSONArray;
import com.flong.springboot.modules.entity.MaterialDetailLog;


public class MaterialDetailSendOrderVo {

    private String orderCode;

    private String materialCode;

    private int orderQuantity;

    private int sendQuantity;

    private int remainQuantity;

    private String materialName;
    private String materialModel;
    private String measureUnit;
    private String brand;

    private int acptQuantity;

    private String acptRemark;

    private String estimatedDeliveryTime;

    public void setAcptRemark(String acptRemark) {
        this.acptRemark = acptRemark;
    }

    public String getAcptRemark() {
        return acptRemark;
    }

    public void setAcptQuantity(int acptQuantity) {
        this.acptQuantity = acptQuantity;
    }

    public int getAcptQuantity() {
        return acptQuantity;
    }

    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setMaterialModel(String materialModel) {
        this.materialModel = materialModel;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialModel() {
        return materialModel;
    }

    public String getMaterialName() {
        return materialName;
    }

    public String getBrand() {
        return brand;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }


    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public void setSendQuantity(int sendQuantity) {
        this.sendQuantity = sendQuantity;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public void setRemainQuantity(int remainQuantity) {
        this.remainQuantity = remainQuantity;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public int getRemainQuantity() {
        return remainQuantity;
    }

    public int getSendQuantity() {
        return sendQuantity;
    }
}