package com.flong.springboot.base.model;



import java.util.List;

/**
 * @Author jinshi
 * @Date 2020/11/26 10:54
 * @Version 1.0
 */
public class ResultListModel<T> extends ResultModelImp {
    private int code;
    private String message;
    private List<T> list;

    public int getCode() {
        return code;
    }

    public ResultListModel<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ResultListModel<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResultListModel<T> setList(List<T> list) {
        if (null != list || list.size() >0) {
            this.list = list;
        }
        return this;
    }

    public List<T> getList() {
        return list;
    }
}
