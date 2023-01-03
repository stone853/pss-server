package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@TableName("t_pss_contract_purchase")
public class ContractPurchase {
    private Integer id;

    private String contractCode;

    private String contractName;

    private String partB;

    private String signingDate;

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

    private String custCode;

    private String supplierCode;

    private String contractCodeS;


    @TableField(exist = false)
    private List<FileBean> fileBeanList;

    public ContractPurchase setFileBeanList(List<FileBean> fileBeanList) {
        this.fileBeanList = fileBeanList;
        return this;
    }

    public List<FileBean> getFileBeanList() {
        return fileBeanList;
    }

    @TableField(exist = false)
    private List<MaterialDetail> materialDetailList;

    public ContractPurchase setMaterialDetailList(List<MaterialDetail> materialDetailList) {
        this.materialDetailList = materialDetailList;
        return this;
    }

    public List<MaterialDetail> getMaterialDetailList() {
        return materialDetailList;
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

    public String getPartB() {
        return partB;
    }

    public void setPartB(String partB) {
        this.partB = partB == null ? null : partB.trim();
    }

    public String getSigningDate() {
        return signingDate;
    }

    public void setSigningDate(String signingDate) {
        this.signingDate = signingDate == null ? null : signingDate.trim();
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

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode == null ? null : custCode.trim();
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getContractCodeS() {
        return contractCodeS;
    }

    public void setContractCodeS(String contractCodeS) {
        this.contractCodeS = contractCodeS == null ? null : contractCodeS.trim();
    }
}