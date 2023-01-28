package com.flong.springboot.modules.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialStockDto {
    private Page page;

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