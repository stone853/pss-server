package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.ContractPurchase;
import com.flong.springboot.modules.entity.ContractSale;
import com.flong.springboot.modules.entity.OrderPurchase;
import com.flong.springboot.modules.entity.dto.ContractPurchaseDto;
import com.flong.springboot.modules.entity.vo.ContractPurchaseVo;
import com.flong.springboot.modules.mapper.ContractPurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContractPurchaseService extends ServiceImpl<ContractPurchaseMapper, ContractPurchase> {
        @Autowired
        ContractPurchaseMapper contractPurchaseMapper;

        @Autowired
        MaterialDetailService materialDetailService;



        public IPage<ContractPurchase> page (ContractPurchaseDto contractPurchase) {
                QueryWrapper<ContractPurchase> build = buildWrapper(contractPurchase);
                return contractPurchaseMapper.selectPage(contractPurchase.getPage()==null ? new Page<>() : contractPurchase.getPage(),build);
        }

        public ContractPurchase getOneByCode (String code) {
                QueryWrapper<ContractPurchase> build = new QueryWrapper<ContractPurchase>();
                build.eq("contract_code",code);
                return contractPurchaseMapper.selectOne(build);
        }

        private QueryWrapper<ContractPurchase> buildWrapper(ContractPurchaseDto contractPurchaseDto) {
                QueryWrapper<ContractPurchase> build = new QueryWrapper<>();
                if (contractPurchaseDto.getContractCode() !=null && !"".equals(contractPurchaseDto.getContractCode())) {
                        build.eq("contract_code",contractPurchaseDto.getContractCode());
                }

                return build;
        }

        /**
         *新增
         * @param c
         */
        @Transactional
        public int insert (ContractPurchase c) {
                String contractCode = GeneratorKeyUtil.getConractPurchaseNextCode();
                //返回
                int r = 0;
                try {
                        c.setContractCode(contractCode);
                        c.setCreateTime(UserHelper.getDateTime());
                        c.setUpdateTime(UserHelper.getDateTime());

                        r = contractPurchaseMapper.insert(c);;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加采购合同失败");
                }


                materialDetailService.batchInsert(contractCode,c.getMaterialDetailList(),"2");
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
        public void update (ContractPurchase c) {
                int keyId = c.getId();
                String contractCode = c.getContractCode();
                if (keyId ==0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"id获取为空");
                }
                //先修改合同
                try {
                        contractPurchaseMapper.updateById(c);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改销售合同失败");
                }

                if(StringUtils.isEmpty(contractCode)) {
                        ContractPurchase cp = contractPurchaseMapper.selectById(c.getId());
                        contractCode = cp.getContractCode();
                }
                materialDetailService.updateOrInsertOrDelete(contractCode,c.getMaterialDetailList());
        }

        public IPage<ContractPurchaseVo> pageList (ContractPurchaseDto contractPurchaseDto) {
                IPage<ContractPurchaseVo> pageList = contractPurchaseMapper.pageList(contractPurchaseDto.getPage()==null ? new Page<>():contractPurchaseDto.getPage(),contractPurchaseDto);
                List<ContractPurchaseVo> customerList = pageList.getRecords();
                customerList.stream().forEach(p -> p.setJsonArray(JSONArray.parseArray( p.getFileC())));
                return  pageList;
        }


        /**
         * 根据ID查，详情
         * @param id
         * @return
         */
        public ContractPurchaseVo getOneById (int id) {
                return contractPurchaseMapper.getOneById(id);
        }

}
