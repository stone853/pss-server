package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("t_pss_supplier")
public class Supplier {
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


    private String supplierCode;

    @Length(max = 30,message = "名称长度不能大于30")
    private String supplierName;

    @Length(max = 20,message = "统一社会信用代码长度不能大于20")
    private String uscc;

    @Length(max = 300,message = "地址长度不能大于300")
    private String addr;

    private String type;

    @Length(max = 30,message = "联系人长度不能大于30")
    private String contracts;

    @Length(max = 20,message = "联系电话长度不能大于20")
    private String contractTel;

    private String evaLevel;

    @Length(max = 300,message = "备注长度不能大于300")
    private String remark;

    private Byte isDelete;
    @JsonIgnore
    private String fileC;

    private String optTime;

    public void setOptTime(String optTime) {
        this.optTime = optTime;
    }

    public String getOptTime() {
        return optTime;
    }

    @TableField(exist = false)
    private List<FileBean> fileBeanList;

    public Supplier setFileBeanList(List<FileBean> fileBeanList) {
        this.fileBeanList = fileBeanList;
        return this;
    }

    @TableField(exist = false)
    private List<MaterialDetail> materialDetailList;

    public Supplier setMaterialDetailList(List<MaterialDetail> materialDetailList) {
        this.materialDetailList = materialDetailList;
        return this;
    }

    public List<MaterialDetail> getMaterialDetailList() {
        return materialDetailList;
    }

    public List<FileBean> getFileBeanList() {
        return fileBeanList;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public Supplier setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode == null ? null : supplierCode.trim();
        return this;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getUscc() {
        return uscc;
    }

    public void setUscc(String uscc) {
        this.uscc = uscc == null ? null : uscc.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getContracts() {
        return contracts;
    }

    public void setContracts(String contracts) {
        this.contracts = contracts == null ? null : contracts.trim();
    }

    public String getContractTel() {
        return contractTel;
    }

    public void setContractTel(String contractTel) {
        this.contractTel = contractTel == null ? null : contractTel.trim();
    }

    public String getEvaLevel() {
        return evaLevel;
    }

    public void setEvaLevel(String evaLevel) {
        this.evaLevel = evaLevel == null ? null : evaLevel.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public String getFileC() {
        return fileC;
    }

    public void setFileC(String fileC) {
        this.fileC = fileC == null ? null : fileC.trim();
    }
}