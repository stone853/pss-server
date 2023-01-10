package com.flong.springboot.modules.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.flong.springboot.modules.entity.User;

public class UserVo extends User {
        private String roleNames;

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getRoleNames() {
        return roleNames;
    }
}
