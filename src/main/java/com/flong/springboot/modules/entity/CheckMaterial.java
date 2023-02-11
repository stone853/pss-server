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
@TableName("t_pss_check_material")
public class CheckMaterial {
    private Integer id;

    private String billCode;

    @Length(max = 30,message = "名称长度不能大于30")
    private String materialName;

    private String optUser;

    private String optTime;

    private String checkResult;

    private String billStatus;

    private String checkMaterial;

    @TableField(exist = false)
    private List<MaterialStock> checkMaterialList;

    @Length(max = 300,message = "备注长度不能大于300")
    private String remark;
    @JsonIgnore
    private String fileC;

    @TableField(exist = false)
    private List<FileBean> fileBeanList;

    public CheckMaterial setFileBeanList(List<FileBean> fileBeanList) {
        this.fileBeanList = fileBeanList;
        return this;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialName() {
        return materialName;
    }

    public List<FileBean> getFileBeanList() {
        return fileBeanList;
    }

    public void setFileC(String fileC) {
        this.fileC = fileC;
    }

    public String getFileC() {
        return fileC;
    }

    public void setCheckMaterialList(List<MaterialStock> checkMaterialList) {
        this.checkMaterialList = checkMaterialList;
    }

    public List<MaterialStock> getCheckMaterialList() {
        return checkMaterialList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode == null ? null : billCode.trim();
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser == null ? null : optUser.trim();
    }

    public String getOptTime() {
        return optTime;
    }

    public void setOptTime(String optTime) {
        this.optTime = optTime == null ? null : optTime.trim();
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult == null ? null : checkResult.trim();
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus == null ? null : billStatus.trim();
    }

    public String getCheckMaterial() {
        return checkMaterial;
    }

    public void setCheckMaterial(String checkMaterial) {
        this.checkMaterial = checkMaterial == null ? null : checkMaterial.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}