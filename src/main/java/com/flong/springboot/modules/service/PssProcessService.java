package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.core.constant.CommonConstant;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.PssProcess;
import com.flong.springboot.modules.entity.dto.PssProcessDto;
import com.flong.springboot.modules.mapper.PssProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

@Service
public class PssProcessService extends ServiceImpl<PssProcessMapper, PssProcess> {
        @Autowired
        PssProcessMapper pssProcessMapper;

        public void subForApprove (String processType,PssProcessDto pssProcessDto) {
                Integer currentStep = pssProcessDto.getCurrentStep();
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

                String classForName = pssProcess.getClassForName();

                if (StringUtils.isEmpty(classForName)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"该流程节点没有配置处理步骤");
                }

                try {
                        Class clazz = Class.forName(classForName);

                        Class [] argTypes = new Class[4];
                        argTypes[0]= Integer.class;
                        argTypes[1] = Integer.class;
                        argTypes[2] = String.class;
                        argTypes[3] = String.class;
                        argTypes[4] = String.class;
                        argTypes[5] = PssProcess.class;

                        Method method = clazz.getMethod("executeProess",argTypes);
                        Object o = clazz.newInstance();
                        method.invoke( o,currentStep,result,opinion,processId,processName,pssProcess);

                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"调用"+classForName+"执行一次");
                }


        }
}
