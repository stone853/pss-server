package com.flong.springboot.modules.service;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.modules.entity.Dict;
import com.flong.springboot.modules.entity.MaterialDetail;
import com.flong.springboot.modules.mapper.DictMapper;
import com.flong.springboot.modules.mapper.MaterialDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialDetailService extends ServiceImpl<MaterialDetailMapper, MaterialDetail> {

        @Autowired
        MaterialDetailMapper materialDetailMapper;



}