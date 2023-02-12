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
import com.flong.springboot.core.process.ContractPurProcessHandle;
import com.flong.springboot.modules.entity.ContractPurchase;
import com.flong.springboot.modules.entity.dto.ContractPurchaseDto;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.entity.vo.ContractPurchaseVo;
import com.flong.springboot.modules.mapper.ContractPurchaseMapper;
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
public class ContractPurchaseService extends ServiceImpl<ContractPurchaseMapper, ContractPurchase> {
        @Autowired
        ContractPurchaseMapper contractPurchaseMapper;

        @Autowired
        MaterialDetailService materialDetailService;


        @Autowired
        ContractPurProcessHandle processHandle;

        @Autowired
        UserService userService;

//        public IPage<ContractPurchase> page (ContractPurchaseDto contractPurchase) {
//                QueryWrapper<ContractPurchase> build = buildWrapper(contractPurchase);
//                return contractPurchaseMapper.selectPage(contractPurchase.getPage()==null ? new Page<>() : contractPurchase.getPage(),build);
//        }

        public ContractPurchaseVo getOneByCode (String userId,String code) {
                ContractPurchaseVo c = contractPurchaseMapper.getOneByCode(code);
                convertJsonArray(c);
                setOptButton(userId,c);
                return c;
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
        public ContractPurchase insert (ContractPurchase c) {
                String subStatus = CommonConstant.CONTRACT_PUR_SUB_STATUS;
                String contractStatus = c.getContractStatus();
                String processId = "";
                String contractCode = "";

                try {
                        c.setCreateTime(UserHelper.getDateTime());
                        c.setUpdateTime(UserHelper.getDateTime());

                        Integer keyId = c.getId();
                        //处理流程  begin
                        if (keyId !=null && keyId !=0) {
                                ContractPurchase contractPurProcess = this.getOneById(keyId);
                                processId = contractPurProcess.getProcessId();
                        }
                        processId = processHandle.handleProcessByStatus(c.getCreateUser(),keyId,processId,c.getContractName(),contractStatus,subStatus,CommonConstant.CONTRACT_PUR_PROCESS_TYPE);
                        if (StringUtils.isNotEmpty(contractStatus) && contractStatus.equals(subStatus) && StringUtils.isEmpty(c.getProcessId())) {
                                c.setProcessId(processId);
                        }
                        //处理流程  end

                        if (keyId !=null && keyId !=0) { //处理合同
                                ContractPurchase cp = this.getOneById(keyId);
                                contractCode = cp.getContractCode();
                                contractPurchaseMapper.updateById(c); //修改状态
                        } else {
                                contractCode = GeneratorKeyUtil.getConractPurchaseNextCode();
                                c.setContractCode(contractCode);
                                contractPurchaseMapper.insert(c);;
                        }

                } catch (ServiceException e) {
                        throw e;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加采购合同失败");
                }

                materialDetailService.updateOrInsertOrDelete(contractCode,c.getMaterialDetailList(),"2");
                return c;
        }


        /**
         *修改
         * @param c
         */
        public void update (ContractPurchase c) {
                int keyId = c.getId();
                if (keyId ==0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"id获取为空");
                }

                if (StringUtils.isEmpty(c.getContractCode())) {
                        ContractPurchase s = contractPurchaseMapper.selectById(keyId);
                        c.setContractCode(s.getContractCode());//加上，否则修改报错
                }
                String foreignCode = c.getContractCode();
                //先修改合同
                try {
                        contractPurchaseMapper.updateById(c);
                } catch (ServiceException e) {
                        throw e;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改销售合同失败");
                }

                materialDetailService.updateOrInsertOrDelete(foreignCode,c.getMaterialDetailList(),"2");
        }

        public IPage<ContractPurchaseVo> pageList (ContractPurchaseDto contractPurchaseDto) {
                IPage<ContractPurchaseVo> pageList = contractPurchaseMapper.pageList(contractPurchaseDto.getPage()==null ? new Page<>():contractPurchaseDto.getPage(),contractPurchaseDto);
                List<ContractPurchaseVo> customerList = pageList.getRecords();
                customerList.stream().forEach((p) -> {
                        convertJsonArray(p);
                        setOptButton(contractPurchaseDto.getUserId(),p);
                        }
                );
                return  pageList;
        }


        /**
         * 根据ID查，详情
         * @param id
         * @return
         */
        public ContractPurchaseVo getOneById (int id) {
                ContractPurchaseVo cv = contractPurchaseMapper.getOneById(id);
                return cv;
        }



        private ContractPurchaseVo convertJsonArray (ContractPurchaseVo c) {
                if (c !=null) {
                        c.setJsonArray(JSONArray.parseArray( c.getFileC()));
                }
                return c;
        }

        private ContractPurchaseVo setOptButton (String userId, ContractPurchaseVo c) {
                if (c == null) {
                        return null;
                }
                String[] userRoles = userService.getUserRoles(new UserDto().setUserId(userId));
                log.info("获取页面操作按钮:userId:{}", userId);

                PageUtils pu = new PageUtils();
                List<String> buttonList = pu.getOptButtons(userId,c.getCreateUser(),userRoles,c.getCreateUserButton(),
                        c.getCheckRoleCode(),c.getCheckUserButton());
                c.setOptButton(buttonList);
                return c;

//                String userId = dto.getUserId();
//                String createUser = c.getCreateUser();
//                List<String> optButton = new ArrayList<>();
//                //返回基本的详情按钮
//                if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(createUser)) {
//                        optButton.add("详情");
//                        c.setOptButton(optButton);
//                        return c;
//                }
//
//                String[] userRoles = userService.getUserRoles(new UserDto().setUserId(userId));
//                StringBuilder sb = new StringBuilder();
//                if (c.getCreateUser() != null && c.getCreateUser().equals(userId)) { //用户操作按钮
//                        sb.append(c.getCreateUserButton()+";");
//                }
//                if (userRoles !=null && userRoles.length >0) {  //返回角色按钮
//                        List<String> roleList = Arrays.asList(userRoles);
//                        if (roleList.contains(c.getCheckRoleCode())) {
//                                sb.append(c.getCheckUserButton()+";");
//                        }
//                }
//                if (StringUtils.isEmpty(sb.toString())){
//                        sb.append("详情");
//                }
//
//                String [] s = sb.toString().split(";");
//                optButton = Arrays.asList(s);
//                c.setOptButton(Arrays.asList(s));
//
//                //非创建用户过滤 重新发起、删除、编辑
//                if (!userId.equals(createUser)) {
//                        optButton.remove("编辑");
//                        optButton.remove("删除");
//                        optButton.remove("重新发起");
//                }
//
//                c.setOptButton(optButton);
//                return c;
        }

}
