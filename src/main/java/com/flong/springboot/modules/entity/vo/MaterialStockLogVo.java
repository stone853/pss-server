package com.flong.springboot.modules.entity.vo;



public class MaterialStockLogVo {
    private String  orderType;
    private String orderTypeName;
    private String sendType;
    private String sendTypeName;
    private String applicationDate;
    private String orderCode;
    private String supplierCode;
    private String supplierName;
    private String applicant;
    private String userName;
    private String materialCode;
    private String materialName;
    private Integer quantityToIn;
    private Integer quantityIn;
    private Integer quantityToOut;
    private Integer quantityOut;

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setQuantityIn(Integer quantityIn) {
        this.quantityIn = quantityIn;
    }

    public String getUserName() {
        return userName;
    }

    public void setQuantityToOut(Integer quantityToOut) {
        this.quantityToOut = quantityToOut;
    }

    public Integer getQuantityToOut() {
        return quantityToOut;
    }

    public void setQuantityOut(Integer quantityOut) {
        this.quantityOut = quantityOut;
    }

    public Integer getQuantityToIn() {
        return quantityToIn;
    }

    public void setQuantityToIn(Integer quantityToIn) {
        this.quantityToIn = quantityToIn;
    }

    public Integer getQuantityOut() {
        return quantityOut;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getQuantityIn() {
        return quantityIn;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getSendType() {
        return sendType;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setOrderTypeName(String orderTypeName) {
        this.orderTypeName = orderTypeName;
    }

    public String getOrderTypeName() {
        return orderTypeName;
    }

    public void setSendTypeName(String sendTypeName) {
        this.sendTypeName = sendTypeName;
    }

    public String getSendTypeName() {
        return sendTypeName;
    }
}