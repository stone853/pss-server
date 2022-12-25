package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.Customer;
import com.flong.springboot.modules.entity.dto.CustomerDto;
import com.flong.springboot.modules.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:客户控制层
 */
@Api(tags = "客户信息")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /**
     * 添加
     */
    @ApiOperation("增加客户信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "Customer",dataTypeClass = Customer.class , value ="")})
    @PostMapping("/v1/add")
    public int add(@RequestHeader("token") String token,@RequestBody Customer t) {
        return customerService.insert(t);
    }

    /**
     * 修改
     * @param customer
     */
    @PutMapping("/updateById")
    public void updateById(@RequestBody Customer customer) {
        customerService.updateById(customer);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        customerService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     * @param custCode
     */
    @GetMapping("/getOne/{custCode}")
    public Customer getOne(@RequestHeader("token") String token,@PathVariable("custCode") String custCode) {
        return customerService.getOneByCode(custCode);
    }

    /**
     * 客户分页，参数有多个使用下标索引进行处理.如果有两个参数(如客户名和地址)：conditionList[0].fieldName=CustomerName、 conditionList[0].fieldName=address
     * 未转码请求分页地址: http://localhost:7011/Customer/page?conditionList[0].fieldName=CustomerName&conditionList[0].operation=LIKE&conditionList[0].value=周
     * 已转码请求分页地址: http://localhost:7011/Customer/page?conditionList[0].fieldName=CustomerName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
     * @return
     */
    @PostMapping("/page")
    public IPage<Customer> page(@RequestHeader("token") String token,@RequestBody CustomerDto CustomerDto) {
        return customerService.page(CustomerDto);
    }


}
