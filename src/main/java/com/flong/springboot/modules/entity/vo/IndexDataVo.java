package com.flong.springboot.modules.entity.vo;

public class IndexDataVo {
    private int contractSaleCount;
    private int contractPurCount;
    private int orderPurCount;
    private int orderPurCountYear;
    private int orderPurCountToday;
    private int orderPurCountFinish;
    private int orderPurCountCheck;
    private int orderPurCountToSend;
    private int userCount;
    private int supplierCount;

    private int customerCount;

    public void setContractPurCount(int contractPurCount) {
        this.contractPurCount = contractPurCount;
    }

    public int getContractPurCount() {
        return contractPurCount;
    }

    public void setContractSaleCount(int contractSaleCount) {
        this.contractSaleCount = contractSaleCount;
    }

    public int getContractSaleCount() {
        return contractSaleCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setOrderPurCount(int orderPurCount) {
        this.orderPurCount = orderPurCount;
    }

    public int getOrderPurCount() {
        return orderPurCount;
    }

    public void setOrderPurCountCheck(int orderPurCountCheck) {
        this.orderPurCountCheck = orderPurCountCheck;
    }

    public int getOrderPurCountCheck() {
        return orderPurCountCheck;
    }

    public void setOrderPurCountFinish(int orderPurCountFinish) {
        this.orderPurCountFinish = orderPurCountFinish;
    }

    public int getOrderPurCountFinish() {
        return orderPurCountFinish;
    }

    public void setOrderPurCountToday(int orderPurCountToday) {
        this.orderPurCountToday = orderPurCountToday;
    }

    public int getOrderPurCountToday() {
        return orderPurCountToday;
    }

    public void setOrderPurCountToSend(int orderPurCountToSend) {
        this.orderPurCountToSend = orderPurCountToSend;
    }

    public int getOrderPurCountToSend() {
        return orderPurCountToSend;
    }

    public void setOrderPurCountYear(int orderPurCountYear) {
        this.orderPurCountYear = orderPurCountYear;
    }

    public int getOrderPurCountYear() {
        return orderPurCountYear;
    }

    public void setSupplierCount(int supplierCount) {
        this.supplierCount = supplierCount;
    }

    public int getSupplierCount() {
        return supplierCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getUserCount() {
        return userCount;
    }
}
