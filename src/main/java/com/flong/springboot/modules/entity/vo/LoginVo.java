package com.flong.springboot.modules.entity.vo;

public class LoginVo {
    String token;

    public LoginVo setToken(String token) {
        this.token = token;
        return this;
    }

    public String getToken() {
        return token;
    }
}
