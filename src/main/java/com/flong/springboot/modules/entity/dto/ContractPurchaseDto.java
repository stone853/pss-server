package com.flong.springboot.modules.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


public class ContractPurchaseDto {
    private Integer id;

    private String contractCode;

    private String contractName;

    private String partB;



    private String constructionSite;

    private Long contractAmount;

    private Long taxRate;

    private Long amountExcludingTax;

    private String createUser;

    private String createTime;

    private String updateTime;

    private String paymentTerm;

    private String contractStatus;

    private String fileC;



    private Page page;



    private String signingBeginDate;

    private String signingEndDate;

    public void setSigningBeginDate(String signingBeginDate) {
        this.signingBeginDate = signingBeginDate;
    }

    public String getSigningBeginDate() {
        return signingBeginDate;
    }

    public void setSigningEndDate(String signingEndDate) {
        this.signingEndDate = signingEndDate;
    }

    public String getSigningEndDate() {
        return signingEndDate;
    }

    public ContractPurchaseDto setPage(Page page) {
        this.page = page;
        return this;
    }

    public Page getPage() {
        return this.page;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    public void setPartB(String partB) {
        this.partB = partB;
    }

    public String getPartB() {
        return partB;
    }



    public String getConstructionSite() {
        return constructionSite;
    }

    public void setConstructionSite(String constructionSite) {
        this.constructionSite = constructionSite == null ? null : constructionSite.trim();
    }

    public Long getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Long contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Long getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(Long taxRate) {
        this.taxRate = taxRate;
    }

    public Long getAmountExcludingTax() {
        return amountExcludingTax;
    }

    public void setAmountExcludingTax(Long amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm == null ? null : paymentTerm.trim();
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus == null ? null : contractStatus.trim();
    }

    public String getFileC() {
        return fileC;
    }

    public void setFileC(String fileC) {
        this.fileC = fileC == null ? null : fileC.trim();
    }
}