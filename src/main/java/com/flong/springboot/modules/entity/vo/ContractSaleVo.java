package com.flong.springboot.modules.entity.vo;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flong.springboot.modules.entity.ContractSale;

import java.util.List;

@JsonIgnoreProperties(value = {"fileC","fileBeanList","materialDetailList"})
public class ContractSaleVo extends ContractSale {

    private String custName;



    private JSONArray jsonArray;

    private String contractStatusNote;

    private String checkUserButton;

    private String createUserButton;

    private String checkRoleCode;

    private List<String> optButton;


    public void setOptButton(List<String> optButton) {
        this.optButton = optButton;
    }

    public List<String> getOptButton() {
        return optButton;
    }

    public void setCheckRoleCode(String checkRoleCode) {
        this.checkRoleCode = checkRoleCode;
    }

    public String getCheckRoleCode() {
        return checkRoleCode;
    }

    public void setCheckUserButton(String checkUserButton) {
        this.checkUserButton = checkUserButton;
    }

    public String getCheckUserButton() {
        return checkUserButton;
    }

    public void setCreateUserButton(String createUserButton) {
        this.createUserButton = createUserButton;
    }

    public String getCreateUserButton() {
        return createUserButton;
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

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }


}
