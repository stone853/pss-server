package com.flong.springboot.modules.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerDto {
    private String custCode;

    private String custName;

    private String type;

    private Page page;

    private List<String> codes;

    public void setCodes(List<String> codes) {
        this.codes = codes;
    }


    public List<String> getCodes() {
        return codes;
    }

    public CustomerDto setPage(Page page) {
        this.page = page;
        return this;
    }

    public Page getPage() {
        return this.page;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
