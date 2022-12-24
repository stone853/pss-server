package com.flong.springboot.modules.service;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.modules.entity.Dict;
import com.flong.springboot.modules.mapper.DictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DictService extends ServiceImpl<DictMapper, Dict> {

        @Autowired
        DictMapper dictMapper;

        public List<Dict> getDictsByType (String type) {
            QueryWrapper<Dict> q = new QueryWrapper<Dict>();
            q.eq("type",type);
            return dictMapper.selectList(q);
        }

}
