package com.flong.springboot.modules.entity.vo;

public class LoginVo {
    private String token;

    private String userName;

    private String userId;

    private String roleCode;

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public LoginVo setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

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
