package com.flong.springboot.core.process;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.ProcessLog;
import com.flong.springboot.modules.entity.PssProcess;
import com.flong.springboot.modules.entity.PssProcessTask;
import com.flong.springboot.modules.entity.dto.PssProcessDto;
import com.flong.springboot.modules.entity.vo.ProcessInfo;
import com.flong.springboot.modules.service.ProcessLogService;
import com.flong.springboot.modules.service.PssProcessService;
import com.flong.springboot.modules.service.PssProcessTaskService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.List;

@Service
public abstract class ProcessHandle {

    @Autowired
    PssProcessTaskService pssProcessTaskService;

    @Autowired
    PssProcessService pssProcessService;

    @Autowired
    ProcessLogService processLogService;

    private String processId;

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessId() {
        return processId;
    }

    public String executeProcess (String processType, PssProcessDto pssProcessDto) {
            String returnStatus = "";

            Integer result = pssProcessDto.getResult();
            String opinion = pssProcessDto.getOpinion();
            String processId = pssProcessDto.getProcessId();
            String processName =pssProcessDto.getProcessName();

            if (StringUtils.isEmpty(processId)) {
                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"processId不能为空");
            }

            //初始化
            setProcessId(processId);

            //获取当前流程步骤
            QueryWrapper<PssProcessTask> queryPt = new QueryWrapper<>();
            queryPt.eq("process_id",pssProcessDto.getProcessId());
            PssProcessTask processTask = pssProcessTaskService.getOne(queryPt);
            if (processTask == null) {
                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"没有找到当前流程任务");
            }
            String currentStep = processTask.getStep();

            PssProcess pssProcess = getProcessByTypeAndStep(processType,currentStep);
            if (pssProcess == null) {
                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"没有配置当前流程节点");
            }
            String currentStepName = pssProcess.getStepName();

            //流程审批通过
            String optResult = "";
            String taskStep = "";
            if (result == 1) { //审批通过
                optResult = "通过";
                String nextStep = getNextStep(pssProcess);
                if (nextStep == null) {
                    throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"未找到流程:"+processId+"下一步未设置");
                }

                returnStatus = pssProcess.getProcessStatusApproved();
                taskStep = nextStep;
            } else {  //驳回
                optResult = "驳回";
                String preStep = pssProcess.getPreStep();
                if (preStep == null) {
                    throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"未找到流程:"+processId+"上一步未设置");
                }

                returnStatus = pssProcess.getProcessStatusRefused();
                taskStep = preStep;
            }
            //修改任务状态
            String nextCheckRole = "";
            String nextStepName = "";
            if (!taskStep.equals("-1")) {
                PssProcess nextProcess = getProcessByTypeAndStep(processType,taskStep);
                if (nextProcess != null) {
                    nextCheckRole = nextProcess.getRoleCode();
                    nextStepName = nextProcess.getStepName();

                }
            } else {
                nextStepName = "结束";
                //调用结束方法
                finish();
            }
            pssProcessTaskService.updateTaskStepByProcessId(processId,taskStep,nextStepName,nextCheckRole);
            //记录日志
            insertProcesslog(processId,currentStepName,pssProcessDto.getUserId(),optResult,opinion);

            return returnStatus;
    }



    abstract void finish ();

    public String getNextStep (PssProcess pssProcess) {
        return pssProcess.getNextStep();
    }

    @Transactional
    public void startProcess (String userId,String processId,String processName,String step,String processType) {
        PssProcess pssProcess = getProcessByTypeAndStep(processType,step);;
        String stepName = pssProcess.getStepName();//提交后步骤名称
        String roleCode = pssProcess.getRoleCode();

        QueryWrapper<PssProcessTask> hasProcess = new QueryWrapper<>();
        hasProcess.eq("process_id",processId);
        PssProcessTask pssProcessTask = pssProcessTaskService.getOne(hasProcess);
        if (pssProcessTask == null ) {
            pssProcessTask = new PssProcessTask();
        }
        pssProcessTask.setProcessId(processId);
        pssProcessTask.setProcessName(processName);
        pssProcessTask.setStep(step);
        pssProcessTask.setStepName(stepName);
        pssProcessTask.setCheckRole(roleCode);
        pssProcessTask.setProcessType(processType);
        pssProcessTask.setOptTime(UserHelper.getDateTime());

        pssProcessTaskService.saveOrUpdate(pssProcessTask);
        //记录日志
        String preStep= pssProcess.getPreStep();
        PssProcess preProcess = getProcessByTypeAndStep(processType,preStep);
        insertProcesslog(processId,preProcess.getStepName(),userId,"提交","");
    }

    public PssProcess getProcessByTypeAndStep (String processType,String step) {
        QueryWrapper<PssProcess> q = new QueryWrapper();
        q.eq("type", processType);
        q.eq("step",step);
        return pssProcessService.getOne(q);
    }


    public void insertProcesslog (String processId,String stepName,String optUser,String optResult,String optOpinion) {
        ProcessLog p = new ProcessLog();
        p.setProcessId(processId)
                .setStepName(stepName)
                .setOptUser(optUser)
                .setOptResult(optResult)
                .setOptTime(UserHelper.getDateTime())
                .setOptOpinion(optOpinion);

        processLogService.save(p);
    }

    public String handleProcessByStatus (String userId,Integer keyId,String processId,String processName,String orderStatus,String subStatus,String processType) {
        if (StringUtils.isEmpty(processType)) {
            return processId;
        }
        if (keyId !=null && keyId !=0) {//处理流程
            if (StringUtils.isNotEmpty(orderStatus) && orderStatus.equals(subStatus)) {
                if (StringUtils.isEmpty(processId)) {
                    processId = GeneratorKeyUtil.getProcessNextCode();
                    startProcess(userId,processId,processName,subStatus,processType);
                } else {//驳回后重新提交
                    PssProcessDto p = new PssProcessDto();
                    p.setProcessId(processId);
                    p.setResult(1);
                    p.setOpinion("提交");
                    p.setProcessName(processName);
                    p.setUserId(userId);
                    executeProcess(processType,p);
                    //pssProcessTaskService.updateTaskStepByProcessId(processId,subStatus);
                }
            }

        } else {
            if (StringUtils.isNotEmpty(orderStatus) && orderStatus.equals(subStatus)) {
                processId = GeneratorKeyUtil.getProcessNextCode();
                startProcess(userId,processId,processName,subStatus,processType);

            }
        }
        return  processId;
    }








    public static void main (String args[]) {
        try {
            Class p = Class.forName("com.flong.springboot.core.process.ProcessHandle");

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
