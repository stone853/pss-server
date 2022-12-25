package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.modules.entity.Supplier;
import com.flong.springboot.modules.entity.dto.SupplierDto;
import com.flong.springboot.modules.mapper.SupplierMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService extends ServiceImpl<SupplierMapper, Supplier> {
        @Autowired
        SupplierMapper supplierMapper;

        public IPage<Supplier> page (SupplierDto supplierDto) {
                QueryWrapper<Supplier> build = buildWrapper(supplierDto);
                return supplierMapper.selectPage(supplierDto.getPage()==null ? new Page<>() : supplierDto.getPage(),build);
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
        public int insert (Supplier c) {
                return supplierMapper.insert(c.setSupplierCode(GeneratorKeyUtil.getSupplierNextId()));
        }


}
