package com.flong.springboot.modules.entity.vo;

import com.alibaba.fastjson.JSONArray;
import com.flong.springboot.modules.entity.Supplier;

public class SupplierVo extends Supplier {
    private String typename;

    private JSONArray jsonArray;

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getTypename() {
        return typename;
    }
}
