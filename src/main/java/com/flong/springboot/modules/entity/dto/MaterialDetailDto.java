package com.flong.springboot.modules.entity.dto;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MaterialDetailDto {
    private String foreignCode;

    public void setForeignCode(String foreignCode) {
        this.foreignCode = foreignCode;
    }

    public String getForeignCode() {
        return foreignCode;
    }
}
