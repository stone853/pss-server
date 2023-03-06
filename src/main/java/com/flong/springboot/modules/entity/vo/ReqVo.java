package com.flong.springboot.modules.entity.vo;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flong.springboot.modules.entity.Req;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@JsonIgnoreProperties(value = {"fileC","fileBeanList","materialDetailList"})
@EqualsAndHashCode(callSuper=false)
public class ReqVo extends Req {


    private JSONArray jsonArray;

    private String reqStatusNote;


    private String checkUserButton;

    private String createUserButton;

    private String checkRoleCode;

    private List<String> optButton;






}
