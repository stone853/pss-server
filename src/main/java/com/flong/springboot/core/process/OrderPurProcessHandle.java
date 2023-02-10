package com.flong.springboot.core.process;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.*;
import com.flong.springboot.modules.entity.vo.MaterialDetailLogVo;
import com.flong.springboot.modules.service.MaterialDetailLogService;
import com.flong.springboot.modules.service.MaterialStockService;
import com.flong.springboot.modules.service.OrderService;
import org.mockito.internal.matchers.Or;
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


    @Autowired
    OrderService orderService;


    @Override
    public String getNextStep (PssProcess pssProcess) {
        Order order = orderService.getOrder(this.getProcessId());
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
            Order order = orderService.getOrder(this.getProcessId());

            //修改订单完成时间
            order.setFinishTime(UserHelper.getDateTime());
            orderService.updateById(order);

            String orderCode = order.getOrderCode();
            List<MaterialDetailLogVo> list = materialDetailLogService.findRaw(orderCode);
            if (list !=null || list.size() > 0 ) {
                for (int i =0; i < list.size(); i++) {
                    MaterialDetailLogVo m = list.get(i);
                    if (m.getQuantity() !=null) {
                        materialStockService.subInOrder(m.getMaterialCode(),m.getMaterialName(),m.getRemark(),m.getAcptQuantity());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改库存失败");
        }

    }




}
