package com.flong.springboot.modules.entity.vo;

import java.util.List;

public class LoginVo {
    private String token;

    private String userName;

    private String userId;

    private String roleCode;

    private String roleName;

    private String deptName;

    private String userType;

    private String deptCode;

    private List<CustomerVo> customerVoList;


    public void setCustomerVoList(List<CustomerVo> customerVoList) {
        this.customerVoList = customerVoList;
    }

    public List<CustomerVo> getCustomerVoList() {
        return customerVoList;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

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
