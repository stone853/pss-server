package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.modules.entity.Customer;
import com.flong.springboot.modules.entity.Supplier;
import com.flong.springboot.modules.entity.dto.CustomerDto;
import com.flong.springboot.modules.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends ServiceImpl<CustomerMapper, Customer> {
        @Autowired
        CustomerMapper customerMapper;

        public IPage<Customer> page (CustomerDto customerDto) {
                QueryWrapper<Customer> build = buildWrapper(customerDto);
                return customerMapper.selectPage(customerDto.getPage()==null ? new Page<>() : customerDto.getPage(),build);
        }

        public Customer getOneByCode (String code) {
                QueryWrapper<Customer> build = new QueryWrapper<Customer>();
                build.eq("cust_code",code);
                return customerMapper.selectOne(build);
        }

        private QueryWrapper<Customer> buildWrapper(CustomerDto customerDto) {
                QueryWrapper<Customer> build = new QueryWrapper<>();
                if (customerDto.getCustCode() !=null && !"".equals(customerDto.getCustCode())) {
                        build.eq("cust_code",customerDto.getCustCode());
                }
                if (customerDto.getCustName() !=null && !"".equals(customerDto.getCustName())) {
                        build.eq("cust_name",customerDto.getCustName());
                }
                if (customerDto.getType() !=null && !"".equals(customerDto.getType())) {
                        build.eq("type",customerDto.getType());
                }
                return build;
        }

        /**
         *新增
         * @param c
         */
        public int insert (Customer c) {
                return customerMapper.insert(c.setCustCode(GeneratorKeyUtil.getCustNextId()));
        }


}
