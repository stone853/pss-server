package com.flong.springboot.modules.entity.vo;

import com.alibaba.fastjson.JSONArray;
import com.flong.springboot.modules.entity.ContractSale;

public class ContractSaleVo extends ContractSale {

    private String custName;



    private JSONArray jsonArray;

    private String contractStatusNote;

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustName() {
        return custName;
    }


    public void setContractStatusNote(String contractStatusNote) {
        this.contractStatusNote = contractStatusNote;
    }

    public String getContractStatusNote() {
        return contractStatusNote;
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }


}
