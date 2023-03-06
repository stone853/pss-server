package com.flong.springboot.modules.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResetPwdDto {

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("新密码")
    private String newPwd;

    @ApiModelProperty("验证码")
    private String verifCode;
}
