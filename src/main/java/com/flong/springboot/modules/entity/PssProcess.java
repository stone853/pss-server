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
@TableName("t_pss_process")
public class PssProcess {
    private Integer id;

    private String type;

    private String typeAme;

    private String step;

    private String stepName;

    private String nextStep;

    private String preStep;

    private String processStatusApproved;

    private String processStatusRefused;

    private String classForName;

    private String decision;

    private String roleCode;

    private String optButton;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTypeAme() {
        return typeAme;
    }

    public void setTypeAme(String typeAme) {
        this.typeAme = typeAme == null ? null : typeAme.trim();
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step == null ? null : step.trim();
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName == null ? null : stepName.trim();
    }

    public String getNextStep() {
        return nextStep;
    }

    public void setNextStep(String nextStep) {
        this.nextStep = nextStep == null ? null : nextStep.trim();
    }

    public String getPreStep() {
        return preStep;
    }

    public void setPreStep(String preStep) {
        this.preStep = preStep == null ? null : preStep.trim();
    }

    public String getProcessStatusApproved() {
        return processStatusApproved;
    }

    public void setProcessStatusApproved(String processStatusApproved) {
        this.processStatusApproved = processStatusApproved == null ? null : processStatusApproved.trim();
    }

    public String getProcessStatusRefused() {
        return processStatusRefused;
    }

    public void setProcessStatusRefused(String processStatusRefused) {
        this.processStatusRefused = processStatusRefused == null ? null : processStatusRefused.trim();
    }

    public String getClassForName() {
        return classForName;
    }

    public void setClassForName(String classForName) {
        this.classForName = classForName == null ? null : classForName.trim();
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision == null ? null : decision.trim();
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    public String getOptButton() {
        return optButton;
    }

    public void setOptButton(String optButton) {
        this.optButton = optButton == null ? null : optButton.trim();
    }
}