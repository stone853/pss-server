package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.core.constant.CommonConstant;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.process.ContractSaleProcess;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.ContractSale;
import com.flong.springboot.modules.entity.PssProcess;
import com.flong.springboot.modules.entity.PssProcessTask;
import com.flong.springboot.modules.entity.dto.PssProcessDto;
import com.flong.springboot.modules.mapper.PssProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
public class PssProcessService extends ServiceImpl<PssProcessMapper, PssProcess> {
        @Autowired
        PssProcessMapper pssProcessMapper;
        @Autowired
        PssProcessTaskService pssProcessTaskService;

        @Autowired
        ContractSaleProcess contractSaleProcess;

        public void contractSaleProcess (String processType,PssProcessDto pssProcessDto) {
                String currentStep = pssProcessDto.getCurrentStep();
                Integer result = pssProcessDto.getResult();
                String opinion = pssProcessDto.getOpinion();
                String processId = pssProcessDto.getProcessId();
                String processName =pssProcessDto.getProcessName();

                QueryWrapper<PssProcess> qw = new QueryWrapper();
                qw.eq("type", processType);
                qw.eq("step",currentStep);
                PssProcess pssProcess = this.getOne(qw);

                if (pssProcess == null) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"没有找到当前流程节点");
                }

                contractSaleProcess.executeProcess(currentStep,result,opinion,processId,processName,pssProcess);

//                String classForName = pssProcess.getClassForName();
//
//                if (StringUtils.isEmpty(classForName)) {
//                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"该流程节点没有配置处理步骤");
//                }

                try {
//                        Class clazz = Class.forName(classForName);
//                        Object o = clazz.newInstance();
//
//                        Class [] argTypes = new Class[6];
//                        argTypes[0]= String.class;
//                        argTypes[1] = Integer.class;
//                        argTypes[2] = String.class;
//                        argTypes[3] = String.class;
//                        argTypes[4] = String.class;
//                        argTypes[5] = PssProcess.class;
//
//                        Method method = clazz.getMethod("executeProcess",argTypes);
//                        method.invoke( o,currentStep,result,opinion,processId,processName,pssProcess);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"调用销售合同流程失败");
                }


        }

        public void startProcess (String processId,String processName,String step,String stepName) {
                PssProcessTask pssProcessTask = new PssProcessTask();
                pssProcessTask.setProcessId(processId);
                pssProcessTask.setProcessName(processName);
                pssProcessTask.setStep(step);

                pssProcessTaskService.saveOrUpdate(pssProcessTask);
                //还有记录日志没做
        }


        public String handleProcessByStatus (Integer keyId,String processId,String processName,String orderStatus,String subStatus) {
                if (keyId !=null && keyId !=0) {//处理流程

                        if (StringUtils.isNotEmpty(orderStatus) && orderStatus.equals(subStatus)) {
                                if (StringUtils.isEmpty(processId)) {
                                        processId = GeneratorKeyUtil.getProcessNextCode();
                                        this.startProcess(processId,processName,subStatus,"");
                                } else {
                                        pssProcessTaskService.updateTaskStepByProcessId(processId,subStatus);

                                }

                        }

                } else {
                        if (StringUtils.isNotEmpty(orderStatus) && orderStatus.equals(subStatus)) {
                                processId = GeneratorKeyUtil.getProcessNextCode();
                                this.startProcess(processId,processName,subStatus,"");

                        }
                }
                return  processId;
        }
}
