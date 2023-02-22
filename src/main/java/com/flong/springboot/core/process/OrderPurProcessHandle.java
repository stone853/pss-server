package com.flong.springboot.core.process;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.*;
import com.flong.springboot.modules.entity.vo.MaterialDetailLogVo;
import com.flong.springboot.modules.service.MaterialDetailLogService;
import com.flong.springboot.modules.service.MaterialStockService;
import com.flong.springboot.modules.service.OrderService;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

            String sendType = order.getSendType();
            if (StringUtils.isNotEmpty(sendType) && !sendType.equals("1")){
                if (list !=null || list.size() > 0 ) {
                    for (int i =0; i < list.size(); i++) {
                        MaterialDetailLogVo m = list.get(i);
                        if (m.getAcptQuantity() !=null) {
                            materialStockService.subInOrder(m.getMaterialCode(),m.getMaterialName(),m.getRemark(),m.getAcptQuantity());
                        }
                    }
                }
            } else {
                try {
                    inertOutOrder(order,list);
                } catch (ServiceException s) {
                    throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"自动创建出库单失败");
                }
            }
        } catch (ServiceException s) {
            throw s;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改库存失败");
        }


        //推送订单评价
        orderService.pushEvaOrder();
    }

    @Transactional
    private void inertOutOrder (Order inOrder,List<MaterialDetailLogVo> inMaterial) {
        Order outOrder = new Order();
        outOrder.setApplicant(inOrder.getApplicant());
        outOrder.setOrderClass("201");//领券出库
        outOrder.setRemark("直达现场订单-自动出库");
        outOrder.setStatus("7"); //完成
        outOrder.setOrderType("2");//出库
        outOrder.setApplicationDate(UserHelper.getDate());
        outOrder.setFinishTime(inOrder.getFinishTime());
        outOrder.setFileC(inOrder.getFileC());
        String outOrderCode = GeneratorKeyUtil.getZDOutOrderNextCode();
        outOrder.setOrderCode(outOrderCode);
        orderService.save(outOrder);

        List<MaterialDetailLog> outMaterialList = new ArrayList<>();
        if (inMaterial !=null && inMaterial.size() >0) {
            inMaterial.stream().forEach( (p) -> {
                MaterialDetailLog outMaterial = new MaterialDetailLog();
                outMaterial.setSourceType("2");
                outMaterial.setFileC(p.getFileC());
                outMaterial.setDetailId(GeneratorKeyUtil.getMaterialDetailNextCode());
                outMaterial.setRemark("直达现场订单-自动出库");
               // outMaterial.setAmountPrice(p.getAmountPrice());
                outMaterial.setBrand(p.getBrand());
                outMaterial.setForeignCode(outOrderCode);
                outMaterial.setMaterialCode(p.getMaterialCode());
                outMaterial.setMaterialName(p.getMaterialName());
                outMaterial.setQuantity(p.getAcptQuantity());
                outMaterial.setRecordTime(UserHelper.getDateTime());
                outMaterialList.add(outMaterial);
            });
        }

        materialDetailLogService.saveBatch(outMaterialList);

    }



}
