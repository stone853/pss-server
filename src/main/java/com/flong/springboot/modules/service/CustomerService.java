package com.flong.springboot.modules.service;



import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.Customer;
import com.flong.springboot.modules.entity.dto.CustomerDto;
import com.flong.springboot.modules.entity.vo.CustomerVo;
import com.flong.springboot.modules.mapper.CustomerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
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
        public Customer insert (Customer c) {
                if (hasExist(c.getCustName())) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"客户名称"+c.getCustName()+"已存在");
                }
                String custCode = GeneratorKeyUtil.getCustNextId();
                customerMapper.insert(c.setCustCode(custCode));
                return c;
        }


        public IPage<CustomerVo> pageList (CustomerDto customerDto) {
                IPage<CustomerVo> pageList = customerMapper.pageList(customerDto.getPage()==null ? new Page<>():customerDto.getPage(),customerDto);
                List<CustomerVo> customerList = pageList.getRecords();
                customerList.stream().forEach(p -> p.setJsonArray(JSONArray.parseArray( p.getFilesC())));
                return  pageList;
        }


        public boolean hasExist (String custName) {
                QueryWrapper<Customer> q = new QueryWrapper<>();
                q.eq("cust_name",custName);
                List<Customer> list = this.list(q);
                if (list !=null && list.size() > 0) {
                        return true;
                }
                return false;
        }


}
