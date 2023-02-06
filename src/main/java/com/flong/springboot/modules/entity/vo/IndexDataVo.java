package com.flong.springboot.modules.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel("首页数据")
@Data
public class IndexDataVo {
    @ApiModelProperty("累计销售合同数量")
    private int contractSaleCount;
    @ApiModelProperty("累计采购合同数量")
    private int contractPurCount;
    @ApiModelProperty("累计采购订单数量")
    private int orderPurCount;
    @ApiModelProperty("今年以来采购订单数量")
    private int orderPurCountYear;
    @ApiModelProperty("今日订单数量")
    private int orderPurCountToday;
    @ApiModelProperty("今日订单完成数量")
    private int orderPurCountFinish;
    @ApiModelProperty("累计审批中订单数量")
    private int orderPurCountCheck;
    @ApiModelProperty("累计待发货订单数量")
    private int orderPurCountToSend;
    @ApiModelProperty("用户数量")
    private int userCount;
    @ApiModelProperty("供应商数量")
    private int supplierCount;
    @ApiModelProperty("客户数量")
    private int customerCount;
    @ApiModelProperty("累计销售合同金额")
    private double contractSaleAmount;
    @ApiModelProperty("累计采购合同金额")
    private double contractPurAmount;
    @ApiModelProperty("累计采购订单金额")
    private double orderPurAmount;
    @ApiModelProperty("今年以来销售合同金额")
    private double contractSaleAmountYear;
    @ApiModelProperty("今年以来采购合同金额")
    private double contractPurAmountYear;
    @ApiModelProperty("今日完成采购订单数量")
    private int orderPurCountFinishToday;
    public void setOrderPurCountFinishToday(int orderPurCountFinishToday) {
        this.orderPurCountFinishToday = orderPurCountFinishToday;
    }

    public int getOrderPurCountFinishToday() {
        return orderPurCountFinishToday;
    }

    public void setContractPurAmountYear(double contractPurAmountYear) {
        this.contractPurAmountYear = contractPurAmountYear;
    }

    public double getContractPurAmountYear() {
        return contractPurAmountYear;
    }

    public void setContractSaleAmountYear(double contractSaleAmountYear) {
        this.contractSaleAmountYear = contractSaleAmountYear;
    }

    public double getContractSaleAmountYear() {
        return contractSaleAmountYear;
    }

    public void setContractPurAmount(double contractPurAmount) {
        this.contractPurAmount = contractPurAmount;
    }

    public double getContractPurAmount() {
        return contractPurAmount;
    }

    public void setContractSaleAmount(double contractSaleAmount) {
        this.contractSaleAmount = contractSaleAmount;
    }

    public double getContractSaleAmount() {
        return contractSaleAmount;
    }

    public void setOrderPurAmount(double orderPurAmount) {
        this.orderPurAmount = orderPurAmount;
    }

    public double getOrderPurAmount() {
        return orderPurAmount;
    }

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
