package com.flong.springboot.core.process;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.MaterialDetailLog;
import com.flong.springboot.modules.entity.MaterialStock;
import com.flong.springboot.modules.entity.Order;
import com.flong.springboot.modules.entity.PssProcess;
import com.flong.springboot.modules.service.MaterialDetailLogService;
import com.flong.springboot.modules.service.MaterialStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderOutProcessHandle extends ProcessHandle {

    private Order order;

    @Autowired
    MaterialDetailLogService materialDetailLogService;

    @Autowired
    MaterialStockService materialStockService;

    public void setOrder(Order order) {
        this.order = order;
    }

    public void finish () {
        try {
            String orderCode = this.order.getOrderCode();
            QueryWrapper<MaterialDetailLog> q = new QueryWrapper<>();
            q.eq("foreign_code",orderCode);
            List<MaterialDetailLog> list = materialDetailLogService.list(q);
            if (list !=null || list.size() > 0 ) {
                for (int i =0; i < list.size(); i++) {
                    MaterialDetailLog m = list.get(i);
                    if (m.getQuantity() !=null) {
                        materialStockService.subOutOrder(m.getMaterialCode(),m.getQuantity());
                    }
                }
//                Map<String, MaterialDetailLog> map= list.stream().collect(Collectors.toMap(MaterialDetailLog :: getMaterialCode, p -> p));
//                QueryWrapper<MaterialStock> qStock = new QueryWrapper<>();
//                q.in("material_code",map.keySet());
//                List<MaterialStock> stockList = materialStockService.list(qStock);
//                if (stockList !=null && stockList.size() > 0) {
//                    for (int i = 0; i < stockList.size(); i++) {
//                        MaterialStock ms = stockList.get(i);
//                        MaterialDetailLog ml = map.get(ms.getMaterialCode());
//                        if (ml != null && ms.getQuantity() !=null && ml.getQuantity() !=null) {
//                            ms.setQuantity(ms.getQuantity() - ml.getQuantity());
//                        }
//                    }
//                    materialStockService.updateBatchById(stockList);
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改库存失败");
        }

    }


}
