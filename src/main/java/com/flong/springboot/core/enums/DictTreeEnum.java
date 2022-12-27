package com.flong.springboot.core.enums;

/**
 * @Author jinshi
 * @Date 2020/11/27 15:26
 * @Version 1.0
 */
public enum DictTreeEnum {
    MATERIAL((byte) 3,"WLLX"),
    CUSTOMER((byte)0,"KHLX"),
    SUPPLIER((byte)2,"GYSLX"),
    ;

    DictTreeEnum(byte code, String type) {
        this.code = code;
        this.type = type;
    }

    private byte code;
    private  String type;

    public byte getCode() {
        return code;
    }

    public String gettype() {
        return type;
    }
}