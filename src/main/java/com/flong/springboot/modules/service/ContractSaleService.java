package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.CommonConstant;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.MsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.*;
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

        @Autowired
        PssProcessService pssProcessService;

        @Autowired
        PssProcessTaskService pssProcessTaskService;

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

                String subStatus = CommonConstant.CONTRACT_SALE_SUB_STATUS;
                String contractStatus = c.getContractStatus();
                String processId = "";
                String contractCode = "";
                //返回
                int r = 0;
                try {

                        c.setCreateTime(UserHelper.getDateTime());
                        c.setUpdateTime(UserHelper.getDateTime());

                        Integer keyId = c.getId();
                        if (keyId !=null && keyId !=0) {
                                ContractSale contractSaleProcess = this.getOneById(keyId);
                                processId = contractSaleProcess.getProcessId();
                        }
                        processId = pssProcessService.handleProcessByStatus(keyId,processId,c.getContractName(),contractStatus,subStatus);
                        if (StringUtils.isNotEmpty(contractStatus) && contractStatus.equals(subStatus) && StringUtils.isEmpty(c.getProcessId())) {
                                c.setProcessId(processId);
                        }


                        if (keyId !=null && keyId !=0) {//处理合同信息
                                contractCode = c.getContractCode();
                                r = contractSaleMapper.updateById(c); //修改合同
                        } else {
                                contractCode = GeneratorKeyUtil.getConractSaleNextCode();
                                c.setContractCode(contractCode);
                                r = contractSaleMapper.insert(c);;  //新增合同
                        }

                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加销售合同失败");
                }


                materialDetailService.updateOrInsertOrDelete(contractCode,c.getMaterialDetailList(),"1");
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
                if (keyId ==0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"id获取为空");
                }

                if (StringUtils.isEmpty(c.getContractCode())) {
                        ContractSale s = contractSaleMapper.selectById(keyId);
                        c.setContractCode(s.getContractCode());//加上，否则修改报错
                }
                String foreignCode = c.getContractCode();
                //先修改合同
                try {
                        contractSaleMapper.updateById(c);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改销售合同失败");
                }

                materialDetailService.updateOrInsertOrDelete(foreignCode,c.getMaterialDetailList(),"1");
        }

        public IPage<ContractSaleVo> pageList (ContractSaleDto contractSaleDto) {
                IPage<ContractSaleVo> pageList = contractSaleMapper.pageList(contractSaleDto.getPage()==null ? new Page<>():contractSaleDto.getPage(),contractSaleDto);
                List<ContractSaleVo> customerList = pageList.getRecords();
                customerList.stream().forEach(p -> p.setJsonArray(JSONArray.parseArray( p.getFileC())));
                return  pageList;
        }


        /**
         * 根据ID查，详情
         * @param id
         * @return
         */
        public ContractSaleVo getOneById (int id) {
                return contractSaleMapper.getOneById(id);
        }
}
