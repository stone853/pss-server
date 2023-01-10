package com.flong.springboot.base.model;

/**
 * @Author jinshi
 * @Date 2020/11/27 15:26
 * @Version 1.0
 * 采购物料明细来源
 */
public enum MaterialLogTypeEnum {
    IN("1","入库"),
    OUT("2","出库"),
    INVENTORY("3","盘点")
    ;

    MaterialLogTypeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private String code;
    private  String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}