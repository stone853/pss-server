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
@TableName("t_pss_order_send")
public class OrderSend {
    private Integer id;

    private String orderSendCode;
    private String orderCode;

    private String driverCode;

    private String estimatedDeliveryTime;

    private String remark;
    @JsonIgnore
    private String fileC;

    @JsonIgnore
    private String acptFileC;

    private String sendStatus;

    private String acptTime;

    private String sendTime;

    @TableField(exist = false)
    private String userId;

    @TableField(exist = false)
    private List<FileBean> fileBeanList;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setAcptTime(String acptTime) {
        this.acptTime = acptTime;
    }

    public String getAcptTime() {
        return acptTime;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public OrderSend setFileBeanList(List<FileBean> fileBeanList) {
        this.fileBeanList = fileBeanList;
        return this;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public List<FileBean> getFileBeanList() {
        return fileBeanList;
    }



    @TableField(exist = false)
    private List<MaterialDetailSend> materialDetailSendList;

    public OrderSend setMaterialDetailSendList(List<MaterialDetailSend> materialDetailSendList) {
        this.materialDetailSendList = materialDetailSendList;
        return this;
    }

    public List<MaterialDetailSend> getMaterialDetailSendList() {
        return materialDetailSendList;
    }


    public void setOrderSendCode(String orderSendCode) {
        this.orderSendCode = orderSendCode;
    }

    public String getOrderSendCode() {
        return orderSendCode;
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

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode == null ? null : driverCode.trim();
    }

    public String getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(String estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime == null ? null : estimatedDeliveryTime.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getFileC() {
        return fileC;
    }

    public void setFileC(String fileC) {
        this.fileC = fileC == null ? null : fileC.trim();
    }
}