package com.flong.springboot.core.process;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.ContractSale;
import com.flong.springboot.modules.entity.PssProcess;
import com.flong.springboot.modules.entity.dto.PssProcessDto;
import com.flong.springboot.modules.service.ContractSaleService;
import com.flong.springboot.modules.service.PssProcessService;
import com.flong.springboot.modules.service.PssProcessTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;

@Service
public class ProcessTaskHandle {

    @Autowired
    PssProcessTaskService pssProcessTaskService;

    @Autowired
    ContractSaleService contractSaleService;

    @Autowired
    PssProcessService pssProcessService;


    public String executeProcess (String processType, PssProcessDto pssProcessDto) {
            String returnStatus = "";
            String taskStep = "";

            String currentStep = pssProcessDto.getCurrentStep();
            Integer result = pssProcessDto.getResult();
            String opinion = pssProcessDto.getOpinion();
            String processId = pssProcessDto.getProcessId();
            String processName =pssProcessDto.getProcessName();

            QueryWrapper<PssProcess> qw = new QueryWrapper();
            qw.eq("type", processType);
            qw.eq("step",currentStep);
            PssProcess pssProcess = pssProcessService.getOne(qw);

            if (pssProcess == null) {
                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"没有找到当前流程节点");
            }

            //流程审批通过
            if (result == 1) { //审批通过
                String nextStep = pssProcess.getNextStep();
                if (nextStep == null) {
                    throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"未找到流程:"+processId+"下一步未设置");
                }

                returnStatus = pssProcess.getProcessStatusApproved();

                taskStep = nextStep;
            } else {  //驳回
                String preStep = pssProcess.getPreStep();
                if (preStep == null) {
                    throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"未找到流程:"+processId+"上一步未设置");
                }

                returnStatus = pssProcess.getProcessStatusRefused();

                taskStep = preStep;
            }
            //修改任务状态
            pssProcessTaskService.updateTaskStepByProcessId(processId,taskStep);

            return returnStatus;
    }




    public static void main (String args[]) {
        try {
            Class p = Class.forName("com.flong.springboot.core.process.ProcessTaskHandle");

            Class [] argTypes = new Class[3];
            argTypes[0]= Integer.class;
            argTypes[1] = Integer.class;
            argTypes[2] = String.class;

            Method method = p.getMethod("executeProess",argTypes);
            Object b = p.newInstance();
            method.invoke( b,1,2,"3");


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
        }

    }
}
