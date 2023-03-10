package com.flong.springboot.modules.entity.vo;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flong.springboot.modules.entity.MaterialDetailLog;

@JsonIgnoreProperties(value = {"fileC","fileBeanList"})
public class MaterialDetailLogVo extends MaterialDetailLog {
    private String materialName;
    private String materialModel;
    private String measureUnit;

    private JSONArray jsonArray;

    private Integer acptQuantity;


    public void setAcptQuantity(Integer acptQuantity) {
        this.acptQuantity = acptQuantity;
    }

    public Integer getAcptQuantity() {
        return acptQuantity;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
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

    public String getMeasureUnit() {
        return measureUnit;
    }


    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }


}