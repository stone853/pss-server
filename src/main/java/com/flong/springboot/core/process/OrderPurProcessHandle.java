package com.flong.springboot.core.process;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.*;
import com.flong.springboot.modules.service.MaterialDetailLogService;
import com.flong.springboot.modules.service.MaterialStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderPurProcessHandle extends ProcessHandle {



    @Autowired
    MaterialDetailLogService materialDetailLogService;

    @Autowired
    MaterialStockService materialStockService;

    private Order order;


    public void setOrder(Order order) {
        this.order = order;
    }


    @Override
    public String getNextStep (PssProcess pssProcess) {
        String sendType = order.getSendType();
        String currentStep = pssProcess.getStep();
        if (!currentStep.equals("4")) {
            return pssProcess.getNextStep();
        }
        if (sendType.equals("1")) {  //1--直达现场
            return "6";  //客户验收
        }
        return "5"; //仓储员验收
    }


    public void finish () {
        try {
            String orderCode = order.getOrderCode();
            QueryWrapper<MaterialDetailLog> q = new QueryWrapper<>();
            q.eq("foreign_code",orderCode);
            List<MaterialDetailLog> list = materialDetailLogService.list(q);
            if (list !=null || list.size() > 0 ) {
                for (int i =0; i < list.size(); i++) {
                    MaterialDetailLog m = list.get(i);
                    if (m.getQuantity() !=null) {
                        materialStockService.subInOrder(m.getMaterialCode(),m.getQuantity());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改库存失败");
        }

    }


}
