package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.process.ProcessTaskHandle;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.ContractSale;
import com.flong.springboot.modules.entity.PssProcess;
import com.flong.springboot.modules.entity.PssProcessTask;
import com.flong.springboot.modules.entity.dto.PssProcessDto;
import com.flong.springboot.modules.mapper.PssProcessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PssProcessService extends ServiceImpl<PssProcessMapper, PssProcess> {
        @Autowired
        PssProcessMapper pssProcessMapper;
        @Autowired
        PssProcessTaskService pssProcessTaskService;

        @Autowired
        ProcessTaskHandle processTaskHandle;

        @Autowired
        ContractSaleService contractSaleService;

        @Transactional
        public void contractSaleProcess (String processType,PssProcessDto pssProcessDto) {
                //处理流程任务，并返回流程任务对应的合同状态
                String returnStatus = processTaskHandle.executeProcess(processType,pssProcessDto);

                //修改销售合同对应状态
                String processId = pssProcessDto.getProcessId();
                QueryWrapper<ContractSale> qc = new QueryWrapper();
                qc.eq("process_id",processId);
                ContractSale contractSale = contractSaleService.getOne(qc);
                if (contractSale == null) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"未找到流程:"+processId+"对应的合同");
                }
                contractSale.setContractStatus(returnStatus);
                contractSaleService.update(contractSale);
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
