package com.flong.springboot.modules.entity.vo;


import com.flong.springboot.modules.entity.EvaScheme;
import lombok.*;

import java.util.List;


public class EvaSchemeVo extends EvaScheme {
    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}