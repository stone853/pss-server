package com.flong.springboot.modules.entity.vo;


import com.flong.springboot.modules.entity.OptLog;
import lombok.*;


public class OptLogVo extends OptLog {
        private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}