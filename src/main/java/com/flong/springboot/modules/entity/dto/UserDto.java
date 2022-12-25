package com.flong.springboot.modules.entity.dto;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;




public class UserDto {

    private String name;

    private String mobile;

    private Byte isDelete;

    private String password;

    private String deptCode;

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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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
