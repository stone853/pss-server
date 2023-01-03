package com.flong.springboot.modules.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class EvaSchemeDto {
    private String schemeName;

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getSchemeName() {
        return schemeName;
    }
}
