package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("t_pss_material_detail_log")
public class MaterialDetailLog {
    private Integer id;

    private String detailId;

    private String foreignCode;

    private String materialCode;

    private String sourceType;

    private String quantity;

    private BigDecimal unitPrice;

    private BigDecimal amountPrice;

    private String estimatedDeliveryTime;

    private Integer acptQuantity;

    private String recordTime;

    private String fileC;


    @TableField(exist = false)
    private List<FileBean> fileBeanList;

    public MaterialDetailLog setFileBeanList(List<FileBean> fileBeanList) {
        this.fileBeanList = fileBeanList;
        return this;
    }

    public List<FileBean> getFileBeanList() {
        return fileBeanList;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetailId() {
        return detailId;
    }

    public MaterialDetailLog setDetailId(String detailId) {
        this.detailId = detailId == null ? null : detailId.trim();
        return this;
    }

    public String getForeignCode() {
        return foreignCode;
    }

    public void setForeignCode(String foreignCode) {
        this.foreignCode = foreignCode == null ? null : foreignCode.trim();
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode == null ? null : materialCode.trim();
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity == null ? null : quantity.trim();
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getAmountPrice() {
        return amountPrice;
    }

    public void setAmountPrice(BigDecimal amountPrice) {
        this.amountPrice = amountPrice;
    }

    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime == null ? null : estimatedDeliveryTime.trim();
    }

    public Integer getAcptQuantity() {
        return acptQuantity;
    }

    public void setAcptQuantity(Integer acptQuantity) {
        this.acptQuantity = acptQuantity;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime == null ? null : recordTime.trim();
    }

    public String getFileC() {
        return fileC;
    }

    public void setFileC(String fileC) {
        this.fileC = fileC == null ? null : fileC.trim();
    }
}