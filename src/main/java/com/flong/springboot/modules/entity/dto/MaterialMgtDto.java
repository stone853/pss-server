package com.flong.springboot.modules.entity.dto;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialMgtDto {
    private String materialCode;

    private String materialName;

    private String type;

    private Page page;

    private String materialModel;

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
