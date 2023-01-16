package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.modules.entity.PssDriver;
import com.flong.springboot.modules.entity.dto.PssDriverDto;
import com.flong.springboot.modules.mapper.DriverMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PssDriverService extends ServiceImpl<DriverMapper, PssDriver> {
    @Autowired
    DriverMapper pssDriverMapper;

    public IPage<PssDriver> pageList (PssDriverDto pssDriver) {
        QueryWrapper<PssDriver> build = buildWrapper(pssDriver);
        return pssDriverMapper.selectPage(pssDriver.getPage()==null ? new Page<>():pssDriver.getPage(),build);
    }

    private QueryWrapper<PssDriver> buildWrapper(PssDriverDto pssDriver) {
        QueryWrapper<PssDriver> build = new QueryWrapper<>();
        if (pssDriver.getDriverName() !=null && !"".equals(pssDriver.getDriverName())) {
            build.like("driver_name",pssDriver.getDriverName());
        }
        if (pssDriver.getDriverCode() !=null && !"".equals(pssDriver.getDriverCode())) {
            build.eq("driver_code",pssDriver.getDriverCode());
        }
        return build;
    }








}
