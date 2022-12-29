package com.flong.springboot.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author jinshi
 * @Date 2021/2/19 9:15
 * @Version 1.0
 */
@Component
public class PssConfig {

    @Value("${pss.upload.customer.url}")
    private String customerFileUrl;


    public void setCustomerFileUrl(String customerFileUrl) {
        this.customerFileUrl = customerFileUrl;
    }

    public String getCustomerFileUrl() {
        return customerFileUrl;
    }
}