package com.flong.springboot.modules.entity.vo;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flong.springboot.modules.entity.ContractPurchase;

import java.util.List;

@JsonIgnoreProperties(value = {"fileC","fileBeanList","materialDetailList"})
public class ContractPurchaseVo extends ContractPurchase {

    private String supplierName;
    private JSONArray jsonArray;

    private String contractStatusNote;

    private String custName;

    private String contractNameS;

    private String checkUserButton;

    private String createUserButton;

    private String checkRoleCode;

    private List<String> optButton;


    public void setOptButton(List<String> optButton) {
        this.optButton = optButton;
    }

    public void setCheckRoleCode(String checkRoleCode) {
        this.checkRoleCode = checkRoleCode;
    }

    public void setCreateUserButton(String createUserButton) {
        this.createUserButton = createUserButton;
    }

    public void setCheckUserButton(String checkUserButton) {
        this.checkUserButton = checkUserButton;
    }

    public List<String> getOptButton() {
        return optButton;
    }

    public String getCheckRoleCode() {
        return checkRoleCode;
    }

    public String getCreateUserButton() {
        return createUserButton;
    }

    public String getCheckUserButton() {
        return checkUserButton;
    }

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
