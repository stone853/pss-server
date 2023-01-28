package com.flong.springboot.modules.entity.vo;

import com.flong.springboot.modules.entity.MaterialStock;


public class MaterialStockDetailVo extends MaterialStock {
    private String materialName;
    private String materialModel;
    private String measureUnit;
    private String brand;

    private Integer quantityToIn;

    private Integer quantityIn;

    private Integer quantityToOut;

    private Integer quantityOut;

    private Integer avlQuantity;

    public void setAvlQuantity(Integer avlQuantity) {
        this.avlQuantity = avlQuantity;
    }

    public Integer getAvlQuantity() {
        return avlQuantity;
    }

    public void setQuantityIn(Integer quantityIn) {
        this.quantityIn = quantityIn;
    }

    public Integer getQuantityIn() {
        return quantityIn;
    }

    public void setQuantityOut(Integer quantityOut) {
        this.quantityOut = quantityOut;
    }

    public Integer getQuantityOut() {
        return quantityOut;
    }

    public void setQuantityToIn(Integer quantityToIn) {
        this.quantityToIn = quantityToIn;
    }

    public Integer getQuantityToIn() {
        return quantityToIn;
    }

    public void setQuantityToOut(Integer quantityToOut) {
        this.quantityToOut = quantityToOut;
    }

    public Integer getQuantityToOut() {
        return quantityToOut;
    }




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