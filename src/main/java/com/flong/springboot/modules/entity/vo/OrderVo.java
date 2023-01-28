package com.flong.springboot.modules.entity.vo;


import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flong.springboot.modules.entity.Order;

import java.util.List;

@JsonIgnoreProperties(value = {"fileC","fileBeanList","materialDetailList"})
public class OrderVo extends Order {
        private String supplierName;
        private String orderStatusNote;
        private String sendTypeNote;
        private String contractName;


        private String payTypeNote;

        private String orderClassName;
        private JSONArray jsonArray;

        private String materialName;

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

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public String getMaterialName() {
            return materialName;
        }

    public void setOrderClassName(String orderClassName) {
            this.orderClassName = orderClassName;
        }

        public String getOrderClassName() {
            return orderClassName;
        }

        public void setPayTypeNote(String payTypeNote) {
            this.payTypeNote = payTypeNote;
        }

        public String getPayTypeNote() {
            return payTypeNote;
        }




        public void setJsonArray(JSONArray jsonArray) {
            this.jsonArray = jsonArray;
        }

        public JSONArray getJsonArray() {
            return jsonArray;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setOrderStatusNote(String orderStatusNote) {
            this.orderStatusNote = orderStatusNote;
        }

        public String getOrderStatusNote() {
            return orderStatusNote;
        }

        public void setSendTypeNote(String sendTypeNote) {
            this.sendTypeNote = sendTypeNote;
        }

        public String getSendTypeNote() {
            return sendTypeNote;
        }


        public void setContractName(String contractName) {
            this.contractName = contractName;
        }


        public String getContractName() {
            return contractName;
        }
}
