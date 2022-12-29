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
@TableName("t_pss_customer")
public class Customer {

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    private String custCode;

    private String custName;

    private String addr;

    private String type;

    private String contracts;

    private String contractTel;

    private String remark;

    private Byte isDelete;

    private String filesC;

    @TableField(exist = false)
    private List<FileBean> fileBeanList;

    public Customer setFileBeanList(List<FileBean> fileBeanList) {
        this.fileBeanList = fileBeanList;
        return this;
    }

    public List<FileBean> getFileBeanList() {
        return fileBeanList;
    }

    public String getCustCode() {
        return custCode;
    }

    public Customer setCustCode(String custCode) {
        this.custCode = custCode == null ? null : custCode.trim();
        return this;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
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

    public String getFilesC() {
        return filesC;
    }

    public void setFilesC(String filesC) {
        this.filesC = filesC == null ? null : filesC.trim();
    }
}