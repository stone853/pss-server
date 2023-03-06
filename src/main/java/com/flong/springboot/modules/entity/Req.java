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
@TableName("t_pss_req")
public class Req {
    private Integer id;

    private String reqCode;

    private String proName;

    private String proAddr;

    private String optUser;

    private String optTime;

    private String deliverDate;

    private String remark;

    private String reqStatus;

    private String fileC;

    private String processId;

    @TableField(exist = false)
    private List<MaterialDetail> materialDetailList;

    @TableField(exist = false)
    private List<FileBean> fileBeanList;

}