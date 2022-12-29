package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@TableName("t_pss_material_detail")
public class MaterialDetail {
    private Integer id;

    private String detailId;

    private String foreignCode;

    private String materialCode;

    private String type;

    private String quantity;

    private Long priceTax;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDetailId() {
        return detailId;
    }

    public MaterialDetail setDetailId(String detailId) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity == null ? null : quantity.trim();
    }

    public Long getPriceTax() {
        return priceTax;
    }

    public void setPriceTax(Long priceTax) {
        this.priceTax = priceTax;
    }
}