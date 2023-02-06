package com.flong.springboot.modules.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("首页数据查询")
@Data
public class IndexDataDto {
    @ApiModelProperty("1-企业内部 2-客户 3-供应商")
    private int indexType;

    private String supplierCode;

    private String custCode;

}
