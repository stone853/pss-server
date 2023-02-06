package com.flong.springboot.modules.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("代办任务")
@Data
public class TodoTaskVo {
    @ApiModelProperty("待办状态")
    private String taskStatus;

    @ApiModelProperty("编码")
    private String taskCode;

    @ApiModelProperty("待办类型")
    private String taskType;

    @ApiModelProperty("标题")
    private String taskTitle;

    @ApiModelProperty("流程步骤")
    private String step;

    @ApiModelProperty("流程步骤名称")
    private String stepName;

    @ApiModelProperty("处理人")
    private String userId;

    @ApiModelProperty("流程ID")
    private String processId;

    @ApiModelProperty("接收时间")
    private String optTime;
}
