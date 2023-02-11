package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("t_pss_material_detail_send")
public class MaterialDetailSend {
    private String materialCode;

    private Integer sendQuantity;



    private Integer id;

    private String detailId;

    private String orderCode;

    private String foreignCode;


    private String brand;
    private String sourceType;


    private Integer acptQuantity;

    @Length(max = 300,message = "备注长度不能大于300")
    private String acptRemark;

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public Integer getAcptQuantity() {
        return acptQuantity;
    }

    public void setAcptQuantity(Integer acptQuantity) {
        this.acptQuantity = acptQuantity;
    }

    public void setAcptRemark(String acptRemark) {
        this.acptRemark = acptRemark;
    }

    public String getAcptRemark() {
        return acptRemark;
    }

    public MaterialDetailSend setSourceType(String sourceType) {
        this.sourceType = sourceType;
        return this;
    }

    public String getSourceType() {
        return sourceType;
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

    public MaterialDetailSend setDetailId(String detailId) {
        this.detailId = detailId == null ? null : detailId.trim();
        return this;
    }

    public String getForeignCode() {
        return foreignCode;
    }

    public MaterialDetailSend setForeignCode(String foreignCode) {
        this.foreignCode = foreignCode == null ? null : foreignCode.trim();
        return this;
    }

    

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode == null ? null : materialCode.trim();
    }

    public void setSendQuantity(Integer sendQuantity) {
        this.sendQuantity = sendQuantity;
    }

    public Integer getSendQuantity() {
        return sendQuantity;
    }
}