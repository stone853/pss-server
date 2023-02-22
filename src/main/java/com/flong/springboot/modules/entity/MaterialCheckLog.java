package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("t_pss_material_check_log")
public class MaterialCheckLog {
    private Integer id;

    private String foreignCode;

    private String materialCode;

    private String materialName;


    private Integer quantity;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName == null ? null : materialName.trim();
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}