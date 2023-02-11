package com.flong.springboot.modules.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.flong.springboot.modules.entity.User;

public class UserVo extends User {
    private String roleNames;

    private String deptName;



    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getRoleNames() {
        return roleNames;
    }
}
