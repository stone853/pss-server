package com.flong.springboot.modules.entity.vo;

import com.alibaba.fastjson.JSONArray;
import com.flong.springboot.modules.entity.ContractSale;

public class ContractSaleVo extends ContractSale {

    private String custName;

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
    }

    private JSONArray jsonArray;

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }


}
