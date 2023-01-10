package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("t_pss_order_purchase")
public class OrderPurchase {
    private Integer id;

    private String orderCode;

    private String contractCodeP;

    private String supplierCode;

    private String sendType;

    private String shipAddr;

    private String applicant;

    private String applicantTel;

    private String applicationDate;

    private String remark;

    private String status;

    private String payType;


    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayType() {
        return payType;
    }


    private String fileC;

    @TableField(exist = false)
    private List<FileBean> fileBeanList;

    public OrderPurchase setFileBeanList(List<FileBean> fileBeanList) {
        this.fileBeanList = fileBeanList;
        return this;
    }

    public List<FileBean> getFileBeanList() {
        return fileBeanList;
    }

    public OrderPurchase setFileC(String fileC) {
        this.fileC = fileC;
        return this;
    }

    public String getFileC() {
        return fileC;
    }

    @TableField(exist = false)
    private List<MaterialDetailLog> materialDetailLogList;

    public OrderPurchase setMaterialDetailLogList(List<MaterialDetailLog> materialDetailLogList) {
        this.materialDetailLogList = materialDetailLogList;
        return this;
    }

    public List<MaterialDetailLog> getMaterialDetailLogList() {
        return materialDetailLogList;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public String getContractCodeP() {
        return contractCodeP;
    }

    public void setContractCodeP(String contractCodeP) {
        this.contractCodeP = contractCodeP == null ? null : contractCodeP.trim();
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType == null ? null : sendType.trim();
    }

    public String getShipAddr() {
        return shipAddr;
    }

    public void setShipAddr(String shipAddr) {
        this.shipAddr = shipAddr == null ? null : shipAddr.trim();
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant == null ? null : applicant.trim();
    }

    public String getApplicantTel() {
        return applicantTel;
    }

    public void setApplicantTel(String applicantTel) {
        this.applicantTel = applicantTel == null ? null : applicantTel.trim();
    }

    public String getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.applicationDate = applicationDate == null ? null : applicationDate.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}