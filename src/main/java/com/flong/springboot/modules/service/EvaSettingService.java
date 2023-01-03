package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.modules.entity.EvaSetting;
import com.flong.springboot.modules.mapper.EvaSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EvaSettingService extends ServiceImpl<EvaSettingMapper, EvaSetting> {
    @Autowired
    EvaSettingMapper evaSettingMapper;


}
