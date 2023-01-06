package com.flong.springboot.modules.entity.vo;

public class LoginVo {
    private String token;

    private String userName;

    public LoginVo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public LoginVo setToken(String token) {
        this.token = token;
        return this;
    }

    public String getToken() {
        return token;
    }
}
