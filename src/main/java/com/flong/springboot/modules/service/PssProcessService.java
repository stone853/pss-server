package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.process.ContractPurProcessHandle;
import com.flong.springboot.core.process.ContractSaleProcessHandle;
import com.flong.springboot.core.process.OrderOutProcessHandle;
import com.flong.springboot.core.process.OrderPurProcessHandle;
import com.flong.springboot.modules.entity.*;
import com.flong.springboot.modules.entity.dto.PssProcessDto;
import com.flong.springboot.modules.entity.vo.ProcessInfo;
import com.flong.springboot.modules.mapper.PssProcessMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PssProcessService extends ServiceImpl<PssProcessMapper, PssProcess> {


        @Autowired
        ContractSaleProcessHandle contractSaleProcessHandle;

        @Autowired
        ContractPurProcessHandle contractPurProcessHandle;

        @Autowired
        OrderPurProcessHandle orderPurProcessHandle;

        @Autowired
        OrderOutProcessHandle orderOutProcessHandle;

        @Autowired
        OrderService orderService;

        @Autowired
        ContractSaleService contractSaleService;

        @Autowired
        ContractPurchaseService contractPurService;



        @Autowired
        PssProcessTaskService pssProcessTaskService;

        @Autowired
        ProcessLogService processLogService;

        @Transactional
        public void contractSaleProcess (String processType,PssProcessDto pssProcessDto) {
                //处理流程任务，并返回流程任务对应的合同状态
                String returnStatus = contractSaleProcessHandle.executeProcess(processType,pssProcessDto);

                //修改销售合同对应状态
                String processId = pssProcessDto.getProcessId();
                QueryWrapper<ContractSale> qc = new QueryWrapper();
                qc.eq("process_id",processId);
                ContractSale contractSale = contractSaleService.getOne(qc);
                if (contractSale == null) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"未找到流程:"+processId+"对应的合同");
                }
                contractSale.setContractStatus(returnStatus);
                contractSaleService.updateById(contractSale);
        }

        @Transactional
        public void contractPurProcess (String processType,PssProcessDto pssProcessDto) {
                //处理流程任务，并返回流程任务对应的合同状态
                String returnStatus = contractPurProcessHandle.executeProcess(processType,pssProcessDto);

                //修改销售合同对应状态
                String processId = pssProcessDto.getProcessId();
                QueryWrapper<ContractPurchase> qc = new QueryWrapper();
                qc.eq("process_id",processId);
                ContractPurchase c = contractPurService.getOne(qc);
                if (c == null) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"未找到流程:"+processId+"对应的合同");
                }
                c.setContractStatus(returnStatus);
                contractPurService.updateById(c);
        }


        @Transactional
        public void orderPurProcess (String processType,PssProcessDto pssProcessDto) {
                //修改销售合同对应状态
                String processId = pssProcessDto.getProcessId();
                QueryWrapper<Order> qc = new QueryWrapper();
                qc.eq("process_id",processId);
                Order order = orderService.getOne(qc);
                if (order == null) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"未找到流程:"+processId+"对应的订单");
                }
                //处理流程任务，并返回流程任务对应的合同状态
                //orderPurProcessHandle.setOrder(order);
                //orderPurProcessHandle.setSendType(order.getSendType());
                String returnStatus = orderPurProcessHandle.executeProcess(processType,pssProcessDto);
                order.setStatus(returnStatus);
                orderService.updateById(order);
        }

        @Transactional
        public void orderOutProcess (String processType,PssProcessDto pssProcessDto) {
                //修改销售合同对应状态
                String processId = pssProcessDto.getProcessId();
                QueryWrapper<Order> qc = new QueryWrapper();
                qc.eq("process_id",processId);
                Order order = orderService.getOne(qc);
                if (order == null) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"未找到流程:"+processId+"对应的订单");
                }

                //处理流程任务，并返回流程任务对应的合同状态
                String returnStatus = orderOutProcessHandle.executeProcess(processType,pssProcessDto);
                order.setStatus(returnStatus);
                orderService.updateById(order);
        }

        public ProcessInfo getProcessInfo (String processId) {
                if (StringUtils.isEmpty(processId)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"查询流程信息，流程ID不能为空");
                }

                ProcessInfo info = new ProcessInfo();
                try {
                        QueryWrapper<PssProcessTask> queryTask = new QueryWrapper<>();
                        queryTask.eq("process_id",processId);
                        PssProcessTask prcessTask = pssProcessTaskService.getOne(queryTask);

//                        QueryWrapper<ProcessLog> queryLog = new QueryWrapper<>();
//                        queryLog.eq("process_id",processId);
                        List<ProcessLog> processLog = processLogService.findAll(processId);

                        info.setProcessLogList(processLog);
                        info.setProcessTask(prcessTask);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"查询:"+processId+"相关信息失败");
                }
                return info;
        }


}
