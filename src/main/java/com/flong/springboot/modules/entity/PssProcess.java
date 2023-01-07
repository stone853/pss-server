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

    private Integer step;

    private String stepName;

    private Integer nextStep;

    private Integer preStep;

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

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName == null ? null : stepName.trim();
    }

    public Integer getNextStep() {
        return nextStep;
    }

    public void setNextStep(Integer nextStep) {
        this.nextStep = nextStep;
    }

    public Integer getPreStep() {
        return preStep;
    }

    public void setPreStep(Integer preStep) {
        this.preStep = preStep;
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