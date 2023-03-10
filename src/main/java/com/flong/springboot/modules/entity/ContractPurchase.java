package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("t_pss_contract_purchase")
public class ContractPurchase {
    private Integer id;

    private String contractCode;

    @Length(max = 30,message = "名称长度不能大于30")
    private String contractName;

    private String partB;

    private String signingDate;

    private String constructionSite;



    private String createUser;

    private String createTime;

    private String updateTime;

    private String paymentTerm;

    private String contractStatus;

    @JsonIgnore
    private String fileC;

    private String custCode;

    private String supplierCode;

    private String contractCodeS;

    private BigDecimal contractAmount;

    private BigDecimal taxRate;

    private BigDecimal amountExcludingTax;

    private String processId;


    @TableField(exist = false)
    private List<FileBean> fileBeanList;

    @TableField(exist = false)
    private List<MaterialDetail> materialDetailList;

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setAmountExcludingTax(BigDecimal amountExcludingTax) {
        this.amountExcludingTax = amountExcludingTax;
    }

    public BigDecimal getAmountExcludingTax() {
        return amountExcludingTax;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }



    public ContractPurchase setFileBeanList(List<FileBean> fileBeanList) {
        this.fileBeanList = fileBeanList;
        return this;
    }

    public List<FileBean> getFileBeanList() {
        return fileBeanList;
    }



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