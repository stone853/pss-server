package com.flong.springboot.modules.entity.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeUserDto {

    private String mobile;

    private String deptCode;

    private String userType;

}
