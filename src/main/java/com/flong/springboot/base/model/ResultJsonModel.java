package com.flong.springboot.base.model;


/**
 * @Author jinshi
 * @Date 2020/11/26 10:54
 * @Version 1.0
 */
public class ResultJsonModel<T> extends ResultModelImp {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public ResultJsonModel<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultJsonModel<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResultJsonModel<T> setData(T data) {
        this.data = data;
        return this;
    }

    public T getData() {
        return data;
    }
}
