package com.flong.springboot.modules.entity.dto;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    private String userId;


    private String name;

    private String mobile;

    private Byte isDelete;



    private String deptCode;

    private String roleCodes;

    private List<String> codes; //部门编号集


    public void setCodes(List<String> codes) {
        this.codes = codes;
    }

    public List<String> getCodes() {
        return codes;
    }

    public void setRoleCodes(String roleCodes) {
        this.roleCodes = roleCodes;
    }

    public String getRoleCodes() {
        return roleCodes;
    }


    public UserDto setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    private Page page;

    public UserDto setPage(Page page) {
        this.page = page;
        return this;
    }

    public Page getPage() {
        return this.page;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptCode() {
        return deptCode;
    }



    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getMobile() {
        return mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
