package com.flong.springboot.modules.entity.vo;

import com.flong.springboot.modules.entity.MaterialDetail;
import com.flong.springboot.modules.entity.MaterialMgt;


public class MaterialDetailVo extends MaterialDetail {
    private String materialName;
    private String materialModel;
    private String measureUnit;

    public void setMaterialModel(String materialModel) {
        this.materialModel = materialModel;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getMaterialModel() {
        return materialModel;
    }

    public String getMaterialName() {
        return materialName;
    }


    public String getMeasureUnit() {
        return measureUnit;
    }


    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }


}