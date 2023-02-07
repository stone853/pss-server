package com.flong.springboot.modules.entity.vo;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flong.springboot.modules.entity.Feedback;
import lombok.Data;


@JsonIgnoreProperties(value = {"fileC","fileBeanList"})
public class FeedbackVo extends Feedback {


    private JSONArray jsonArray;

    private String userName;

    private String companyName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public FeedbackVo setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
        return this;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }
}
