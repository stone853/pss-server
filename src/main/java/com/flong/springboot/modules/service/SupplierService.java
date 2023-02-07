package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.ContractSale;
import com.flong.springboot.modules.entity.Customer;
import com.flong.springboot.modules.entity.Supplier;
import com.flong.springboot.modules.entity.dto.CustomerDto;
import com.flong.springboot.modules.entity.dto.SupplierDto;
import com.flong.springboot.modules.entity.vo.CustomerVo;
import com.flong.springboot.modules.entity.vo.SupplierVo;
import com.flong.springboot.modules.mapper.SupplierMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplierService extends ServiceImpl<SupplierMapper, Supplier> {
        @Autowired
        SupplierMapper supplierMapper;

        @Autowired
        UserService userService;

        @Autowired
        MaterialDetailService materialDetailService;

        public IPage<SupplierVo> pageList (SupplierDto supplierDto) {
                IPage<SupplierVo> pageList = supplierMapper.pageList(supplierDto.getPage()==null ? new Page<>():supplierDto.getPage(),supplierDto);
                List<SupplierVo> supplierVoList = pageList.getRecords();
                supplierVoList.stream().forEach(p -> p.setJsonArray(JSONArray.parseArray( p.getFileC())));
                return  pageList;
        }

        public Supplier getOneByCode (String code) {
                QueryWrapper<Supplier> build = new QueryWrapper<Supplier>();
                build.eq("supplier_code",code);
                return supplierMapper.selectOne(build);
        }
        private QueryWrapper<Supplier> buildWrapper(SupplierDto supplierDto) {
                QueryWrapper<Supplier> build = new QueryWrapper<>();
                if (supplierDto.getSupplierCode() !=null && !"".equals(supplierDto.getSupplierCode())) {
                        build.like("supplier_code",supplierDto.getSupplierCode());
                }
                if (supplierDto.getSupplierName() !=null && !"".equals(supplierDto.getSupplierName())) {
                        build.like("supplier_name",supplierDto.getSupplierName());
                }
                if (supplierDto.getType() !=null && !"".equals(supplierDto.getType())) {
                        build.eq("type",supplierDto.getType());
                }
                return build;
        }

        /**
         *新增
         * @param c
         */
        public Supplier insert (Supplier c) {
                if (hasExist(c.getSupplierName())) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"供应商名称"+c.getSupplierName()+"已存在");
                }

                String foreignCode = GeneratorKeyUtil.getSupplierNextId();
                try {
                        c.setSupplierCode(foreignCode);
                        supplierMapper.insert(c);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加供应商失败");
                }
                materialDetailService.batchInsert(foreignCode,c.getMaterialDetailList(),"3");

                //新增或者修改用户信息
                userService.insertOrUpdateUser(foreignCode,c.getContractTel(),c.getContracts(),"3");
                return c;
        }




        /**
         *修改
         * @param c
         */
        @Transactional
        public void update (Supplier c) {
                int keyId = c.getId();
                if (keyId ==0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"id获取为空");
                }
                //先修改
                try {
                        supplierMapper.updateById(c);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改供应商信息失败");
                }
                if (StringUtils.isEmpty(c.getSupplierCode())) {
                        c = supplierMapper.selectById(keyId);
                }
                String foreignCode = c.getSupplierCode();
                materialDetailService.updateOrInsertOrDelete(foreignCode,c.getMaterialDetailList(),"3");

                //新增或者修改用户信息
                userService.insertOrUpdateUser(foreignCode,c.getContractTel(),c.getContracts(),"3");
        }


        public boolean hasExist (String supplierName) {
                QueryWrapper<Supplier> q = new QueryWrapper<>();
                q.eq("supplier_name",supplierName);
                List<Supplier> list = this.list(q);
                if (list !=null && list.size() > 0) {
                        return true;
                }
                return false;
        }

}
