package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.PageUtils;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.CommonConstant;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.process.ContractSaleProcessHandle;
import com.flong.springboot.modules.entity.*;
import com.flong.springboot.modules.entity.dto.ContractSaleDto;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.entity.vo.ContractSaleVo;
import com.flong.springboot.modules.mapper.ContractSaleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class ContractSaleService extends ServiceImpl<ContractSaleMapper, ContractSale> {
        @Autowired
        ContractSaleMapper contractSaleMapper;

        @Autowired
        MaterialDetailService materialDetailService;

        @Autowired
        PssProcessService pssProcessService;

        @Autowired
        ContractSaleProcessHandle processHandle;

        @Autowired
        UserService userService;

//        public IPage<ContractSale> page (ContractSaleDto contractSale) {
//                QueryWrapper<ContractSale> build = buildWrapper(contractSale);
//                return contractSaleMapper.selectPage(contractSale.getPage()==null ? new Page<>() : contractSale.getPage(),build);
//        }



        private QueryWrapper<ContractSale> buildWrapper(ContractSaleDto contractSaleDto) {
                QueryWrapper<ContractSale> build = new QueryWrapper<>();
                if (contractSaleDto.getContractCode() !=null && !"".equals(contractSaleDto.getContractCode())) {
                        build.eq("contract_code",contractSaleDto.getContractCode());
                }

                return build;
        }

        /**
         *??????
         * @param c
         */
        @Transactional
        public ContractSale insert (ContractSale c) {

                String subStatus = CommonConstant.CONTRACT_SALE_SUB_STATUS;
                String contractStatus = c.getContractStatus();
                String processId = "";
                String contractCode = "";

                try {

                        c.setCreateTime(UserHelper.getDateTime());
                        c.setUpdateTime(UserHelper.getDateTime());

                        Integer keyId = c.getId();
                        if (keyId !=null && keyId !=0) { //????????????
                                ContractSale contractSaleProcess = this.getOneById(keyId);
                                processId = contractSaleProcess.getProcessId();
                        }
                        processId = processHandle.handleProcessByStatus(c.getCreateUser(),keyId,processId,c.getContractName(),contractStatus,subStatus,CommonConstant.CONTRACT_SALE_PROCESS_TYPE);
                        if (StringUtils.isNotEmpty(contractStatus) && contractStatus.equals(subStatus) && StringUtils.isEmpty(c.getProcessId())) {
                                c.setProcessId(processId);
                        }


                        if (keyId !=null && keyId !=0) {//??????????????????
                                ContractSale contractSale = this.getOneById(keyId);
                                contractCode = contractSale.getContractCode();
                                contractSaleMapper.updateById(c); //????????????
                        } else {
                                contractCode = GeneratorKeyUtil.getConractSaleNextCode();
                                c.setContractCode(contractCode);
                                contractSaleMapper.insert(c);;  //????????????
                        }

                }catch (ServiceException e) {
                        throw e;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"????????????????????????");
                }

                materialDetailService.updateOrInsertOrDelete(contractCode,c.getMaterialDetailList(),"1");
                return c;
        }


        /**
         *??????
         * @param c
         */
        public void update (ContractSale c) {
                int keyId = c.getId();
                if (keyId ==0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"id????????????");
                }

                if (StringUtils.isEmpty(c.getContractCode())) {
                        ContractSale s = contractSaleMapper.selectById(keyId);
                        c.setContractCode(s.getContractCode());//???????????????????????????
                }
                String foreignCode = c.getContractCode();
                //???????????????
                try {
                        contractSaleMapper.updateById(c);
                }catch (ServiceException e) {
                        throw e;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"????????????????????????");
                }

                materialDetailService.updateOrInsertOrDelete(foreignCode,c.getMaterialDetailList(),"1");
        }

        public IPage<ContractSaleVo> pageList (ContractSaleDto contractSaleDto) {
                IPage<ContractSaleVo> pageList = contractSaleMapper.pageList(contractSaleDto.getPage()==null ? new Page<>():contractSaleDto.getPage(),contractSaleDto);
                List<ContractSaleVo> saleList = pageList.getRecords();
                saleList.stream().forEach((p) -> {
                        convertJsonArray(p);
                        setOptButton(contractSaleDto.getUserId(),p); //??????????????????
                });
                return  pageList;
        }


        /**
         * ??????ID????????????
         * @param id
         * @return
         */
        public ContractSaleVo getOneById (int id) {
                return convertJsonArray(contractSaleMapper.getOneById(id));
        }

        public ContractSaleVo getOneByCode (String userId,String code) {
                ContractSaleVo cv = convertJsonArray(contractSaleMapper.getOneByCode(code));
                setOptButton(userId,cv);
                return cv;
        }


        private ContractSaleVo convertJsonArray (ContractSaleVo c) {
                if (c !=null) {
                        c.setJsonArray(JSONArray.parseArray( c.getFileC()));
                }
                return c;
        }

        private ContractSaleVo setOptButton (String userId, ContractSaleVo c) {
                if (c == null) {
                        return null;
                }
                String[] userRoles = userService.getUserRoles(new UserDto().setUserId(userId));
                log.info("????????????????????????:userId:{}", userId);

                PageUtils pu = new PageUtils();
                List<String> buttonList = pu.getOptButtons(userId,c.getCreateUser(),userRoles,c.getCreateUserButton(),
                        c.getCheckRoleCode(),c.getCheckUserButton());
                c.setOptButton(buttonList);
                return c;
        }

}
