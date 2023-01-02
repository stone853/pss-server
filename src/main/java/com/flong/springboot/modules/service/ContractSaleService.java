package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.MsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.ContractSale;
import com.flong.springboot.modules.entity.MaterialDetail;
import com.flong.springboot.modules.entity.dto.ContractSaleDto;
import com.flong.springboot.modules.entity.dto.CustomerDto;
import com.flong.springboot.modules.entity.vo.ContractSaleVo;
import com.flong.springboot.modules.entity.vo.CustomerVo;
import com.flong.springboot.modules.mapper.ContractSaleMapper;
import com.flong.springboot.modules.mapper.MaterialDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
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
        @Transactional
        public int insert (ContractSale c) {
                String contractCode = GeneratorKeyUtil.getConractSaleNextCode();
                //返回
                int r = 0;
                try {
                        c.setContractCode(contractCode);
                        c.setCreateTime(UserHelper.getDateTime());
                        c.setUpdateTime(UserHelper.getDateTime());
                        c.setSigningDate(UserHelper.getDate());

                        r = contractSaleMapper.insert(c);;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加销售合同失败");
                }


                materialDetailService.batchInsert(contractCode,c.getMaterialDetailList());
//                try {
//                        List<MaterialDetail> list = c.getMaterialDetailList();
//                        list.stream().forEach(p ->
//                                p.setDetailId(GeneratorKeyUtil.getMaterialDetailNextCode())
//                                        .setForeignCode(contractCode)
//                        );
//                        materialDetailService.saveBatch(c.getMaterialDetailList());
//                } catch (Exception e) {
//                        e.printStackTrace();
//                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加物料明细失败");
//                }


                return r;
        }


        /**
         *修改
         * @param c
         */
        public void update (ContractSale c) {
                int keyId = c.getId();
                String contractCode = c.getContractCode();
                if (keyId ==0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"id获取为空");
                }
                //先修改合同
                try {
                        contractSaleMapper.updateById(c);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改销售合同失败");
                }

                materialDetailService.updateOrInsertOrDelete(contractCode,c.getMaterialDetailList());
        }

        public IPage<ContractSaleVo> pageList (ContractSaleDto contractSaleDto) {
                IPage<ContractSaleVo> pageList = contractSaleMapper.pageList(contractSaleDto.getPage()==null ? new Page<>():contractSaleDto.getPage(),contractSaleDto);
                List<ContractSaleVo> customerList = pageList.getRecords();
                customerList.stream().forEach(p -> p.setJsonArray(JSONArray.parseArray( p.getFileC())));
                return  pageList;
        }


}
