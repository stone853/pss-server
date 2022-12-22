package com.flong.springboot.base.result;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author jinshi
 * @Date 2020/11/26 10:51
 * @Version 1.0
 */
public class JsonResult implements Result{
    JSONObject result;

    public JsonResult (JSONObject result) {
        this.result = result;
    }
    @Override
    public JSONObject getResult() {
        return result;
    }
}
