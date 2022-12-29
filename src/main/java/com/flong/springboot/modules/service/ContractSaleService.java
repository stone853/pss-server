package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.MsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.ContractSale;
import com.flong.springboot.modules.entity.MaterialDetail;
import com.flong.springboot.modules.entity.dto.ContractSaleDto;
import com.flong.springboot.modules.mapper.ContractSaleMapper;
import com.flong.springboot.modules.mapper.MaterialDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContractSaleService extends ServiceImpl<ContractSaleMapper, ContractSale> {
        @Autowired
        ContractSaleMapper contractSaleMapper;

        @Autowired
        MaterialDetailService materialDetailService;



        public IPage<ContractSale> page (ContractSaleDto contractSale) {
                QueryWrapper<ContractSale> build = buildWrapper(contractSale);
                return contractSaleMapper.selectPage(contractSale.getPage()==null ? new Page<>() : contractSale.getPage(),build);
        }

        public ContractSale getOneByCode (String code) {
                QueryWrapper<ContractSale> build = new QueryWrapper<ContractSale>();
                build.eq("contract_code",code);
                return contractSaleMapper.selectOne(build);
        }

        private QueryWrapper<ContractSale> buildWrapper(ContractSaleDto contractSaleDto) {
                QueryWrapper<ContractSale> build = new QueryWrapper<>();
                if (contractSaleDto.getContractCode() !=null && !"".equals(contractSaleDto.getContractCode())) {
                        build.eq("contract_code",contractSaleDto.getContractCode());
                }

                return build;
        }

        /**
         *新增
         * @param c
         */
        public int insert (ContractSale c) {
                String contractCode = GeneratorKeyUtil.getConractSaleNextCode();
                c.setContractCode(contractCode);
                c.setCreateTime(UserHelper.getDateTime());
                c.setUpdateTime(UserHelper.getDateTime());
                c.setSigningDate(UserHelper.getDate());
                try {
                        contractSaleMapper.insert(c);contractSaleMapper.insert(c);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加销售合同失败");
                }

                List<MaterialDetail> list = c.getMaterialDetailList();

                list.stream().forEach(p ->
                        p.setDetailId(GeneratorKeyUtil.getMaterialDetailNextCode())
                                .setForeignCode(contractCode)
                );
                materialDetailService.saveBatch(c.getMaterialDetailList());

                return 0;
        }


}
