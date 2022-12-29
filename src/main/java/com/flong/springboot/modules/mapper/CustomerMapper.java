package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.Customer;
import com.flong.springboot.modules.entity.dto.CustomerDto;
import com.flong.springboot.modules.entity.vo.CustomerVo;
import org.apache.ibatis.annotations.Param;



public interface CustomerMapper extends BaseMapper<Customer> {
    public IPage<CustomerVo> pageList(Page<Customer> page, @Param("customer") CustomerDto customer);
}