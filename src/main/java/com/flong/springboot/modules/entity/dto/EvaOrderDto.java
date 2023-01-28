package com.flong.springboot.modules.entity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flong.springboot.modules.entity.EvaIndex;
import com.flong.springboot.modules.entity.FileBean;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EvaOrderDto {


    private String orderCode;

    private String supplierCode;

    private String supplierName;

    private String evaStatus;

    private String beginTime;

    private String endTime;
    private String evaDes;

    private String evaIndex;

    private String fileC;

    private Page page;

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }



    public String getEvaStatus() {
        return evaStatus;
    }

    public void setEvaStatus(String evaStatus) {
        this.evaStatus = evaStatus == null ? null : evaStatus.trim();
    }



    public String getEvaDes() {
        return evaDes;
    }

    public void setEvaDes(String evaDes) {
        this.evaDes = evaDes == null ? null : evaDes.trim();
    }

    public String getEvaIndex() {
        return evaIndex;
    }

    public void setEvaIndex(String evaIndex) {
        this.evaIndex = evaIndex == null ? null : evaIndex.trim();
    }

    public String getFileC() {
        return fileC;
    }

    public void setFileC(String fileC) {
        this.fileC = fileC == null ? null : fileC.trim();
    }


    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

}