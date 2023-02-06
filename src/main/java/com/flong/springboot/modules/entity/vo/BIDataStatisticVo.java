package com.flong.springboot.modules.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApiModel("BI数据")
@Data
public class BIDataStatisticVo {

    @ApiModelProperty("累计销售合同数量")
    private List<Map> salesRanking = new ArrayList<>();
    @ApiModelProperty("累计销售合同数量")
    private List<Map> materialTop5 = new ArrayList<>();
    @ApiModelProperty("累计销售合同数量")
    private List<Map> salesAnalysis = new ArrayList<>();
    @ApiModelProperty("累计销售合同数量")
    private List<Map> sendRecords = new ArrayList<>();
    @ApiModelProperty("累计销售合同数量")
    private List<Map> sendMaterialTop5 = new ArrayList<>();
    @ApiModelProperty("首页数据")
    private IndexDataVo indexDataVo;
}
