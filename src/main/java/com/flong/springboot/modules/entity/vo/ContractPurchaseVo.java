package com.flong.springboot.modules.entity.vo;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flong.springboot.modules.entity.ContractPurchase;

public class ContractPurchaseVo extends ContractPurchase {

    private String supplierName;
    private JSONArray jsonArray;

    private String contractStatusNote;

    private String custName;

    private String contractNameS;

    public void setContractNameS(String contractNameS) {
        this.contractNameS = contractNameS;
    }

    public String getContractNameS() {
        return contractNameS;
    }

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

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }



    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }




}
