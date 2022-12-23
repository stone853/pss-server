package com.flong.springboot.modules.entity.dto;

public class LoginDto {
    private String userId;
    private String pwd;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }
}
