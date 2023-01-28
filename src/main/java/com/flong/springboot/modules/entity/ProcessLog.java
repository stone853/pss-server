package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("t_pss_process_log")
public class ProcessLog {
    private Integer id;

    private String processId;

    private String stepName;

    private String optTime;

    private String optUser;

    private String optResult;

    private String optOpinion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProcessId() {
        return processId;
    }

    public ProcessLog setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
        return this;
    }

    public String getStepName() {
        return stepName;
    }

    public ProcessLog setStepName(String stepName) {
        this.stepName = stepName == null ? null : stepName.trim();
        return this;
    }

    public String getOptTime() {
        return optTime;
    }

    public ProcessLog setOptTime(String optTime) {
        this.optTime = optTime == null ? null : optTime.trim();
        return this;
    }

    public String getOptUser() {
        return optUser;
    }

    public ProcessLog setOptUser(String optUser) {
        this.optUser = optUser == null ? null : optUser.trim();
        return this;
    }

    public String getOptResult() {
        return optResult;
    }

    public ProcessLog setOptResult(String optResult) {
        this.optResult = optResult == null ? null : optResult.trim();
        return this;
    }

    public String getOptOpinion() {
        return optOpinion;
    }

    public ProcessLog setOptOpinion(String optOpinion) {
        this.optOpinion = optOpinion == null ? null : optOpinion.trim();
        return this;
    }
}