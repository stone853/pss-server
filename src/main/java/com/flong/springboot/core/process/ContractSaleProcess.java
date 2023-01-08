package com.flong.springboot.core.process;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.ContractSale;
import com.flong.springboot.modules.entity.PssProcess;
import com.flong.springboot.modules.entity.PssProcessTask;
import com.flong.springboot.modules.service.ContractSaleService;
import com.flong.springboot.modules.service.PssProcessTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.Objects;

@Service
public class ContractSaleProcess {

    @Autowired
    PssProcessTaskService pssProcessTaskService;

    @Autowired
    ContractSaleService contractSaleService;

    @Transactional
    public void executeProcess (String currentStep,Integer result, String opinion,String processId,String processName,PssProcess pssProcess) {
            QueryWrapper<ContractSale> qc = new QueryWrapper();
            qc.eq("process_id",processId);
            ContractSale contractSale = contractSaleService.getOne(qc);

            if (contractSale == null) {
                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"未找到流程:"+processId+"对应的合同");
            }

            String taskStep = "";
            //流程审批通过
            if (result == 1) { //审批通过
                String nextStep = pssProcess.getNextStep();
                if (nextStep == null) {
                    throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"未找到流程:"+processId+"下一步未设置");
                }

                contractSale.setContractStatus(pssProcess.getProcessStatusApproved());

                taskStep = nextStep;
            } else {  //驳回
                String preStep = pssProcess.getPreStep();
                if (preStep == null) {
                    throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"未找到流程:"+processId+"上一步未设置");
                }

                contractSale.setContractStatus(pssProcess.getProcessStatusRefused());

                taskStep = preStep;
            }
            //修改任务状态
            pssProcessTaskService.updateTaskStepByProcessId(processId,taskStep);
            //修改销售合同状态
            contractSaleService.update(contractSale);
    }




    public static void main (String args[]) {
        try {
            Class p = Class.forName("com.flong.springboot.core.process.ContractSaleProcess");

            Class [] argTypes = new Class[3];
            argTypes[0]= Integer.class;
            argTypes[1] = Integer.class;
            argTypes[2] = String.class;

            Method method = p.getMethod("executeProess",argTypes);
            Object b = p.newInstance();
            method.invoke( b,1,2,"3");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
