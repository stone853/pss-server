package com.flong.springboot.modules.entity.vo;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flong.springboot.modules.entity.CheckMaterial;



@JsonIgnoreProperties(value = {"checkMaterialList", "fileBeanList","fileC","checkMaterial"})
public class CheckMaterialVo extends CheckMaterial {
    private String checkResultName;

    private String billStatusName;

    private JSONArray jsonArray;

    private JSONArray checkMaterialJsonArray;

    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setCheckMaterialJsonArray(JSONArray checkMaterialJsonArray) {
        this.checkMaterialJsonArray = checkMaterialJsonArray;
    }

    public JSONArray getCheckMaterialJsonArray() {
        return checkMaterialJsonArray;
    }

    public CheckMaterialVo setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        return this;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setBillStatusName(String billStatusName) {
        this.billStatusName = billStatusName;
    }

    public String getBillStatusName() {
        return billStatusName;
    }

    public void setCheckResultName(String checkResultName) {
        this.checkResultName = checkResultName;
    }

    public String getCheckResultName() {
        return checkResultName;
    }
}