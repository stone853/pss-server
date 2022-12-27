package com.flong.springboot.modules.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.flong.springboot.modules.entity.MaterialMgt;
import lombok.*;


public class MaterialMgtVo extends MaterialMgt {
    private String typename;
    private String material_model_name;

    public void setMaterial_model_name(String material_model_name) {
        this.material_model_name = material_model_name;
    }

    public String getMaterial_model_name() {
        return material_model_name;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getTypename() {
        return typename;
    }
}