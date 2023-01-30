package com.flong.springboot.modules.entity.vo;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flong.springboot.modules.entity.EvaIndex;
import com.flong.springboot.modules.entity.FileBean;
import lombok.*;

import java.util.List;


public class EvaOrderVo {
    private Integer id;

    private String orderCode;

    private Double evaResult;

    private String evaStatus;

    private String evaTime;

    private String evaDes;


    private String fileC;

    private String indexString;

    private String supplierName;

    private JSONArray jsonArray;

    private JSONArray indexList;

    private String evaStatusName;

    private String finishTime;

    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setEvaStatusName(String evaStatusName) {
        this.evaStatusName = evaStatusName;
    }

    public String getEvaStatusName() {
        return evaStatusName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setIndexList(JSONArray indexList) {
        this.indexList = indexList;
    }

    public String getIndexString() {
        return indexString;
    }

    public void setIndexString(String indexString) {
        this.indexString = indexString;
    }

    public JSONArray getIndexList() {
        return indexList;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
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

    public Double getEvaResult() {
        return evaResult;
    }

    public void setEvaResult(Double evaResult) {
        this.evaResult = evaResult;
    }

    public String getEvaStatus() {
        return evaStatus;
    }

    public void setEvaStatus(String evaStatus) {
        this.evaStatus = evaStatus == null ? null : evaStatus.trim();
    }

    public String getEvaTime() {
        return evaTime;
    }

    public void setEvaTime(String evaTime) {
        this.evaTime = evaTime == null ? null : evaTime.trim();
    }

    public String getEvaDes() {
        return evaDes;
    }

    public void setEvaDes(String evaDes) {
        this.evaDes = evaDes == null ? null : evaDes.trim();
    }


    public String getFileC() {
        return fileC;
    }

    public void setFileC(String fileC) {
        this.fileC = fileC == null ? null : fileC.trim();
    }
}