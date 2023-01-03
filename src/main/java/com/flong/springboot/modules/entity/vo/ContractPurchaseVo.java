package com.flong.springboot.modules.entity.vo;

import com.alibaba.fastjson.JSONArray;
import com.flong.springboot.modules.entity.ContractPurchase;

public class ContractPurchaseVo extends ContractPurchase {

    private String supplierName;

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    private JSONArray jsonArray;

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }


}
