package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.OrderPurchase;
import com.flong.springboot.modules.entity.dto.OrderPurchaseDto;
import com.flong.springboot.modules.entity.vo.OrderPurchaseVo;
import com.flong.springboot.modules.mapper.OrderPurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderPurchaseService extends ServiceImpl<OrderPurchaseMapper, OrderPurchase> {
        @Autowired
        OrderPurchaseMapper orderPurchaseMapper;

        @Autowired
        MaterialDetailLogService materialDetailLogService;



        public IPage<OrderPurchase> page (OrderPurchaseDto OrderPurchaseDto) {
                QueryWrapper<OrderPurchase> build = buildWrapper(OrderPurchaseDto);
                return orderPurchaseMapper.selectPage(OrderPurchaseDto.getPage()==null ? new Page<>() : OrderPurchaseDto.getPage(),build);
        }

        public OrderPurchase getOneByCode (String code) {
                QueryWrapper<OrderPurchase> build = new QueryWrapper<OrderPurchase>();
                build.eq("Order_code",code);
                return orderPurchaseMapper.selectOne(build);
        }

        private QueryWrapper<OrderPurchase> buildWrapper(OrderPurchaseDto orderPurchaseDto) {
                QueryWrapper<OrderPurchase> build = new QueryWrapper<>();
                if (orderPurchaseDto.getOrderCode() !=null && !"".equals(orderPurchaseDto.getOrderCode())) {
                        build.eq("order_code",orderPurchaseDto.getOrderCode());
                }

                return build;
        }

        /**
         *新增
         * @param c
         */
        @Transactional
        public int insert (OrderPurchase c) {
                String orderCode = "";
                //返回
                int r = 0;
                try {
                        c.setOrderCode(orderCode);
                        c.setApplicationDate(UserHelper.getDate());

                        Integer keyId = c.getId();

                        if (keyId !=null && keyId !=0) {
                                orderCode = c.getOrderCode();
                                r = orderPurchaseMapper.updateById(c); //修改状态
                        } else {
                                orderCode = GeneratorKeyUtil.getOrderPurchaseNextCode();
                                c.setOrderCode(orderCode);
                                r = orderPurchaseMapper.insert(c);;
                        }


                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加订单失败");
                }

                materialDetailLogService.updateOrInsertOrDelete(orderCode,c.getMaterialDetailLogList());
                return r;
        }


        /**
         *修改
         * @param c
         */
        public void update (OrderPurchase c) {
                int keyId = c.getId();
                String orderCode = c.getOrderCode();
                if (keyId ==0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"id获取为空");
                }
                //先修改合同
                try {
                        orderPurchaseMapper.updateById(c);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改销售合同失败");
                }

                if(StringUtils.isEmpty(orderCode)) {
                        OrderPurchase o = orderPurchaseMapper.selectById(c.getId());
                        orderCode = o.getOrderCode();
                }
                materialDetailLogService.updateOrInsertOrDelete(orderCode,c.getMaterialDetailLogList());
        }

        public IPage<OrderPurchaseVo> pageList (OrderPurchaseDto orderPurchaseDto) {
                IPage<OrderPurchaseVo> pageList = orderPurchaseMapper.pageList(orderPurchaseDto.getPage()==null ? new Page<>():orderPurchaseDto.getPage(),orderPurchaseDto);
                List<OrderPurchaseVo> orderList = pageList.getRecords();
                orderList.stream().forEach(p -> p.setJsonArray(JSONArray.parseArray( p.getFileC())));
                return pageList;
        }


        /**
         * 根据ID查，详情
         * @param id
         * @return
         */
        public OrderPurchaseVo getOneById (int id) {
                return orderPurchaseMapper.getOneById(id);
        }

}
