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
import com.flong.springboot.modules.entity.Req;
import com.flong.springboot.modules.entity.dto.ReqDto;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.entity.vo.ReqVo;
import com.flong.springboot.modules.mapper.ReqMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class ReqService extends ServiceImpl<ReqMapper, Req> {
        @Autowired
        ReqMapper reqMapper;

        @Autowired
        MaterialDetailService materialDetailService;


        @Autowired
        ContractPurProcessHandle processHandle;

        @Autowired
        UserService userService;



        public ReqVo getOneByCode (String userId,String code) {
                ReqVo c = reqMapper.getOneByCode(code);
                convertJsonArray(c);
                setOptButton(userId,c);
                return c;
        }

        /**
         *新增
         * @param c
         */
        @Transactional
        public Req insert (Req c) {
                String subStatus = CommonConstant.REQ_SUB_STATUS;
                String reqStatus = c.getReqStatus();
                String processId = "";
                String reqCode = "";

                try {
                        c.setOptTime(UserHelper.getDateTime());

                        Integer keyId = c.getId();
                        //处理流程  begin
                        if (keyId !=null && keyId !=0) {
                                Req req = this.getOneById(keyId);
                                processId = req.getProcessId();
                        }
                        processId = processHandle.handleProcessByStatus(c.getOptUser(),keyId,processId,"需求单",reqStatus,subStatus,CommonConstant.REQ_PROCESS_TYPE);
                        if (StringUtils.isNotEmpty(reqStatus) && reqStatus.equals(subStatus) && StringUtils.isEmpty(c.getProcessId())) {
                                c.setProcessId(processId);
                        }
                        //处理流程  end

                        if (keyId !=null && keyId !=0) { //处理合同
                                Req cp = this.getOneById(keyId);
                                reqCode = cp.getReqCode();
                                reqMapper.updateById(c); //修改状态
                        } else {
                                reqCode = GeneratorKeyUtil.getReqNextCode();
                                c.setReqCode(reqCode);
                                reqMapper.insert(c);;
                        }
                } catch (ServiceException e) {
                        throw e;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加需求失败");
                }

                materialDetailService.updateOrInsertOrDelete(reqCode,c.getMaterialDetailList(),"4");
                return c;
        }


        public IPage<ReqVo> pageList (ReqDto reqDto) {
                IPage<ReqVo> pageList = reqMapper.pageList(reqDto.getPage()==null ? new Page<>():reqDto.getPage(),reqDto);
                List<ReqVo> customerList = pageList.getRecords();
                customerList.stream().forEach((p) -> {
                        convertJsonArray(p);
                        setOptButton(reqDto.getUserId(),p);
                        }
                );
                return  pageList;
        }


        /**
         * 根据ID查，详情
         * @param id
         * @return
         */
        public ReqVo getOneById (int id) {
                ReqVo cv = reqMapper.getOneById(id);
                return cv;
        }



        private ReqVo convertJsonArray (ReqVo c) {
                if (c !=null) {
                        c.setJsonArray(JSONArray.parseArray( c.getFileC()));
                }
                return c;
        }

        private ReqVo setOptButton (String userId, ReqVo c) {
                if (c == null) {
                        return null;
                }
                String[] userRoles = userService.getUserRoles(new UserDto().setUserId(userId));
                log.info("获取页面操作按钮:userId:{}", userId);

                PageUtils pu = new PageUtils();
                List<String> buttonList = pu.getOptButtons(userId,c.getOptUser(),userRoles,c.getCreateUserButton(),
                        c.getCheckRoleCode(),c.getCheckUserButton());
                c.setOptButton(buttonList);
                return c;

        }

}
