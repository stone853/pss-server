package com.flong.springboot.modules.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PssProcessDto {

    @NotNull(message =  "processId不能为空" )
    private String processId;

    @NotNull(message =  "result不能为空" )
    private Integer result;

    private String opinion;

    private String processName;

    private String userId;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessName() {
        return processName;
    }


    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public void setResult(Integer result) {
        this.result = result;
    }



    public Integer getResult() {
        return result;
    }

    public String getOpinion() {
        return opinion;
    }
}
