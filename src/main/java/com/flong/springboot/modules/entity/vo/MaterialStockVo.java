package com.flong.springboot.modules.entity.vo;

import com.alibaba.fastjson.JSONArray;
import com.flong.springboot.modules.entity.MaterialDetailLog;
import com.flong.springboot.modules.entity.MaterialStock;


public class MaterialStockVo extends MaterialStock {
    private String materialName;
    private String materialModel;
    private String measureUnit;
    private String brand;



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

    public String getBrand() {
        return brand;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }


}