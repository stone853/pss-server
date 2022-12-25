package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@TableName("t_pss_supplier")
public class Supplier {
    private String supplierCode;

    private String supplierName;

    private String uscc;

    private String addr;

    private String type;

    private String contracts;

    private String contractTel;

    private String evaLevel;

    private String remark;

    private Byte isDelete;

    private String filesS;

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

    public String getFilesS() {
        return filesS;
    }

    public void setFilesS(String filesS) {
        this.filesS = filesS == null ? null : filesS.trim();
    }
}