package com.flong.springboot.core.process;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.ContractSale;
import com.flong.springboot.modules.entity.PssProcess;
import com.flong.springboot.modules.service.ContractSaleService;
import com.flong.springboot.modules.service.PssProcessTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Objects;

@Service
public class ContractSaleProcess {

    @Autowired
    PssProcessTaskService pssProcessTaskService;

    @Autowired
    ContractSaleService contractSaleService;
    public void executeProess (Integer currentStep,Integer result, String opinion,String processId,String processName,PssProcess pssProcess) {

//            QueryWrapper<ContractSale> qw = new QueryWrapper();
//            qw.eq("process_id",processId);
//            ContractSale c = contractSaleService.getOne(qw);
//






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
