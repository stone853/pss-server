package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flong.springboot.modules.entity.dto.ContractSaleDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper=false)
@TableName("t_pss_contract_sale")
public class ContractSale {
    private Integer id;

    private String contractCode;

    private String contractName;

    private String partA;

    private String signingDate;

    private String constructionSite;

    private BigDecimal contractAmount;

    private BigDecimal taxRate;

    private BigDecimal amountExcludingTax;

    private String createUser;

    private String createTime;

    private String updateTime;

    private String paymentTerm;

    private String contractStatus;

    private String fileC;

    private String processId;

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessId() {
        return processId;
    }

    @TableField(exist = false)
    private List<FileBean> fileBeanList;

    public ContractSale setFileBeanList(List<FileBean> fileBeanList) {
        this.fileBeanList = fileBeanList;
        return this;
    }

    public List<FileBean> getFileBeanList() {
        return fileBeanList;
    }

    @TableField(exist = false)
    private List<MaterialDetail> materialDetailList;

    public ContractSale setMaterialDetailList(List<MaterialDetail> materialDetailList) {
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

    public ContractSale setContractCode(String contractCode) {
        this.contractCode = contractCode == null ? null : contractCode.trim();
        return this;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    public String getPartA() {
        return partA;
    }

    public void setPartA(String partA) {
        this.partA = partA == null ? null : partA.trim();
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

    public BigDecimal getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(BigDecimal contractAmount) {
        this.contractAmount = contractAmount;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getAmountExcludingTax() {
        return amountExcludingTax;
    }

    public void setAmountExcludingTax(BigDecimal amountExcludingTax) {
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