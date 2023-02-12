package com.flong.springboot.modules.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.flong.springboot.modules.entity.DataStatistic;

import java.util.List;
import java.util.Map;

public interface DataStatisticMapper extends BaseMapper<DataStatistic> {
    List<Map> salesRanking();

    List<Map> materialTop5();

    List<Map> salesAnalysis();

    List<Map> sendRecords();

    List<Map> sendMaterialTop5();

    List<Map> customerInfo();
}
