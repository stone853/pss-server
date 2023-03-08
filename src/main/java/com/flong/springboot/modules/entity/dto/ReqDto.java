package com.flong.springboot.modules.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ReqDto {
    private Page page;

    @ApiModelProperty("项目")
    private String custCode;

    @ApiModelProperty("需求单编号")
    private String reqCode;

    @ApiModelProperty("状态")
    private String reqStatus;

    @ApiModelProperty("申请开始时间")
    private String appBeginDate;

    @ApiModelProperty("申请结束时间")
    private String appEndDate;

    @ApiModelProperty("交付开始日期")
    private String deliverBeginDate;

    @ApiModelProperty("交付结束日期")
    private String deliverEndDate;
}