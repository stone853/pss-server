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
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.process.ContractSaleProcessHandle;
import com.flong.springboot.modules.entity.*;
import com.flong.springboot.modules.entity.dto.ContractSaleDto;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.entity.vo.ContractSaleVo;
import com.flong.springboot.modules.mapper.ContractSaleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
         *新增
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
                        if (keyId !=null && keyId !=0) { //处理流程
                                ContractSale contractSaleProcess = this.getOneById(keyId);
                                processId = contractSaleProcess.getProcessId();
                        }
                        processId = processHandle.handleProcessByStatus(keyId,processId,c.getContractName(),contractStatus,subStatus,CommonConstant.CONTRACT_SALE_PROCESS_TYPE);
                        if (StringUtils.isNotEmpty(contractStatus) && contractStatus.equals(subStatus) && StringUtils.isEmpty(c.getProcessId())) {
                                c.setProcessId(processId);
                        }


                        if (keyId !=null && keyId !=0) {//处理合同信息
                                ContractSale contractSale = this.getOneById(keyId);
                                contractCode = contractSale.getContractCode();
                                contractSaleMapper.updateById(c); //修改合同
                        } else {
                                contractCode = GeneratorKeyUtil.getConractSaleNextCode();
                                c.setContractCode(contractCode);
                                contractSaleMapper.insert(c);;  //新增合同
                        }

                }catch (ServiceException e) {
                        throw e;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加销售合同失败");
                }

                materialDetailService.updateOrInsertOrDelete(contractCode,c.getMaterialDetailList(),"1");
                return c;
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
                }catch (ServiceException e) {
                        throw e;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改销售合同失败");
                }

                materialDetailService.updateOrInsertOrDelete(foreignCode,c.getMaterialDetailList(),"1");
        }

        public IPage<ContractSaleVo> pageList (ContractSaleDto contractSaleDto) {
                IPage<ContractSaleVo> pageList = contractSaleMapper.pageList(contractSaleDto.getPage()==null ? new Page<>():contractSaleDto.getPage(),contractSaleDto);
                List<ContractSaleVo> saleList = pageList.getRecords();
                saleList.stream().forEach((p) -> {
                        convertJsonArray(p);
                        setOptButton(contractSaleDto,p); //设置操作按钮
                });
                return  pageList;
        }


        /**
         * 根据ID查，详情
         * @param id
         * @return
         */
        public ContractSaleVo getOneById (int id) {
                return convertJsonArray(contractSaleMapper.getOneById(id));
        }

        public ContractSaleVo getOneByCode (String code) {
               return convertJsonArray(contractSaleMapper.getOneByCode(code));
        }


        private ContractSaleVo convertJsonArray (ContractSaleVo c) {
                if (c !=null) {
                        c.setJsonArray(JSONArray.parseArray( c.getFileC()));
                }
                return c;
        }

        private ContractSaleVo setOptButton (ContractSaleDto dto, ContractSaleVo c) {
                if (c == null) {
                        return null;
                }
                String userId = dto.getUserId();
                String createUser = c.getCreateUser();
                List<String> optButton = new ArrayList<>();
                //返回基本的详情按钮
                if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(createUser)) {
                        optButton.add("详情");
                        c.setOptButton(optButton);
                        return c;
                }

                String[] userRoles = userService.getUserRoles(new UserDto().setUserId(userId));
                StringBuilder sb = new StringBuilder();
                if (c.getCreateUser() != null && c.getCreateUser().equals(userId)) { //用户操作按钮
                        sb.append(c.getCreateUserButton()+";");
                }
                if (userRoles !=null && userRoles.length >0) {  //返回角色按钮
                        List<String> roleList = Arrays.asList(userRoles);
                        if (roleList.contains(c.getCheckRoleCode())) {
                                sb.append(c.getCheckUserButton()+";");
                        }
                }
                if (StringUtils.isEmpty(sb.toString())){
                        sb.append("详情");
                }

                String [] s = sb.toString().split(";");
                optButton = Arrays.asList(s);
                c.setOptButton(Arrays.asList(s));

                //非创建用户过滤 重新发起、删除、编辑
                if (!userId.equals(createUser)) {
                        optButton.remove("编辑");
                        optButton.remove("删除");
                        optButton.remove("重新发起");
                }

                c.setOptButton(optButton);
                return c;
        }

}
