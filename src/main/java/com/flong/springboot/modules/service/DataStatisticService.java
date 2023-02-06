package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.modules.entity.DataStatistic;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.mapper.DataStatisticMapper;
import com.flong.springboot.modules.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataStatisticService extends ServiceImpl<DataStatisticMapper, DataStatistic> {
    @Autowired
    DataStatisticMapper dataStatisticMapper;

    public List<Map> salesRanking () {
        return dataStatisticMapper.salesRanking();
    }

    public List<Map> materialTop5 () {
        return dataStatisticMapper.materialTop5();
    }

    public List<Map> salesAnalysis () {
        return dataStatisticMapper.salesAnalysis();
    }

    public List<Map> sendRecords () {
        return dataStatisticMapper.sendRecords();
    }

    public List<Map> sendMaterialTop5 () {
        return dataStatisticMapper.sendMaterialTop5();
    }

}
