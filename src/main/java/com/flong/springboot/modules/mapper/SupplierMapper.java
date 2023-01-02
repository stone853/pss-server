package com.flong.springboot.modules.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.flong.springboot.modules.entity.Customer;
import com.flong.springboot.modules.entity.Supplier;
import com.flong.springboot.modules.entity.dto.CustomerDto;
import com.flong.springboot.modules.entity.dto.SupplierDto;
import com.flong.springboot.modules.entity.vo.CustomerVo;
import com.flong.springboot.modules.entity.vo.SupplierVo;
import org.apache.ibatis.annotations.Param;


public interface SupplierMapper extends BaseMapper<Supplier> {
    public IPage<SupplierVo> pageList(Page<Customer> page, @Param("supplier") SupplierDto supplier);
}