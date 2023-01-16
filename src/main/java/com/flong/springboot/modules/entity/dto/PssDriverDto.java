package com.flong.springboot.modules.entity.dto;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flong.springboot.modules.entity.PssDriver;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PssDriverDto {


    private String driverCode;

    private String driverName;


    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverName() {
        return driverName;
    }

    private Page page;

    public PssDriverDto setPage(Page page) {
        this.page = page;
        return this;
    }

    public Page getPage() {
        return page;
    }


}
