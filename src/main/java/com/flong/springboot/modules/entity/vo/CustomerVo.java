package com.flong.springboot.modules.entity.vo;

import com.flong.springboot.modules.entity.Customer;
import com.flong.springboot.modules.entity.MaterialMgt;


public class CustomerVo extends Customer {
    private String typename;

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getTypename() {
        return typename;
    }
}