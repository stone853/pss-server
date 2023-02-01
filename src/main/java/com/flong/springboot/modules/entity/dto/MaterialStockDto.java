package com.flong.springboot.modules.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialStockDto {
    private Page page;

    private String materialName;

    private String materialModel;

    private String materialType;

    private String excludeNull;

    private String materialCode;

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setExcludeNull(String excludeNull) {
        this.excludeNull = excludeNull;
    }

    public String getExcludeNull() {
        return excludeNull;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialModel(String materialModel) {
        this.materialModel = materialModel;
    }

    public String getMaterialModel() {
        return materialModel;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public String getMaterialType() {
        return materialType;
    }

    private List<String> materialCodeList;

    public void setMaterialCodeList(List<String> materialCodeList) {
        this.materialCodeList = materialCodeList;
    }

    public List<String> getMaterialCodeList() {
        return materialCodeList;
    }

    public MaterialStockDto setPage(Page page) {
        this.page = page;
        return this;
    }

    public Page getPage() {
        return this.page;
    }

}