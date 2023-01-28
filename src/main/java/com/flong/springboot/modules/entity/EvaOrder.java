package com.flong.springboot.modules.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@JsonIgnoreProperties(ignoreUnknown = true)
@TableName("t_pss_eva_order")
public class EvaOrder {
    private Integer id;

    private String orderCode;

    private Double evaResult;

    private String evaStatus;

    private String evaTime;

    private String evaDes;


    private String indexString;
    @JsonIgnore
    private String fileC;

    @TableField(exist = false)
    private List<FileBean> fileBeanList;
    @TableField(exist = false)
    private List<EvaIndex> evaIndexList;

    public void setIndexString(String indexString) {
        this.indexString = indexString;
    }

    public String getIndexString() {
        return indexString;
    }

    public void setEvaIndexList(List<EvaIndex> evaIndexList) {
        this.evaIndexList = evaIndexList;
    }

    public List<EvaIndex> getEvaIndexList() {
        return evaIndexList;
    }

    public EvaOrder setFileBeanList(List<FileBean> fileBeanList) {
        this.fileBeanList = fileBeanList;
        return this;
    }

    public List<FileBean> getFileBeanList() {
        return fileBeanList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    public Double getEvaResult() {
        return evaResult;
    }

    public void setEvaResult(Double evaResult) {
        this.evaResult = evaResult;
    }

    public String getEvaStatus() {
        return evaStatus;
    }

    public void setEvaStatus(String evaStatus) {
        this.evaStatus = evaStatus == null ? null : evaStatus.trim();
    }

    public String getEvaTime() {
        return evaTime;
    }

    public void setEvaTime(String evaTime) {
        this.evaTime = evaTime == null ? null : evaTime.trim();
    }

    public String getEvaDes() {
        return evaDes;
    }

    public void setEvaDes(String evaDes) {
        this.evaDes = evaDes == null ? null : evaDes.trim();
    }



    public String getFileC() {
        return fileC;
    }

    public void setFileC(String fileC) {
        this.fileC = fileC == null ? null : fileC.trim();
    }
}