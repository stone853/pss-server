package com.flong.springboot.modules.entity.dto;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialMgtDto {
    private String materialCode;

    private String materialName;

    private String type;

    private Page page;

    private String materialModel;

    private String supplierCode;

    private List<String> codes;


    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setMaterialModel(String materialModel) {
        this.materialModel = materialModel;
    }

    public String getMaterialModel() {
        return materialModel;
    }

    public MaterialMgtDto setPage(Page page) {
        this.page = page;
        return this;
    }

    public Page getPage() {
        return page;
    }

    public void setType(String type) {
        this.type = type;
    }


    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getType() {
        return type;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public String getMaterialName() {
        return materialName;
    }
}
