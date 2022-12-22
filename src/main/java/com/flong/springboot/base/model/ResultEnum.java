package com.flong.springboot.base.model;

/**
 * @Author jinshi
 * @Date 2020/11/27 15:26
 * @Version 1.0
 */
public enum ResultEnum {
    UNKONW_ERROR(-1,"未知错误"),
    ERROR(0,"失败"),
    SUCCESS(1,"成功"),
    FLUNK(100,"不及格"),
    WELL(101,"良好")
    ;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private  String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}