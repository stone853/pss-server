package com.flong.springboot.modules.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdUserPwdDto {
    @ApiModelProperty("旧密码")
    private String oldPwd;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("新密码")
    private String newPwd;


}
