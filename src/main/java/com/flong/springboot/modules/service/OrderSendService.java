package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.model.MaterialLogTypeEnum;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.MaterialDetailLog;
import com.flong.springboot.modules.entity.OrderSend;
import com.flong.springboot.modules.entity.dto.OrderSendDto;
import com.flong.springboot.modules.entity.dto.OrderSendMaterialDto;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.entity.vo.MaterialDetailSendOrderVo;
import com.flong.springboot.modules.entity.vo.OrderSendVo;
import com.flong.springboot.modules.mapper.MaterialDetailSendMapper;
import com.flong.springboot.modules.mapper.OrderSendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderSendService extends ServiceImpl<OrderSendMapper, OrderSend> {
        @Autowired
        OrderSendMapper orderSendMapper;

        @Autowired
        MaterialDetailSendMapper materialDetailSendMapper;

        @Autowired
        MaterialDetailSendService materialDetailSendService;


        public IPage<OrderSend> page (OrderSendDto orderSendDto) {
                QueryWrapper<OrderSend> build = buildWrapper(orderSendDto);
                return orderSendMapper.selectPage(orderSendDto.getPage()==null ? new Page<>() : orderSendDto.getPage(),build);
        }

        public OrderSend getOneByCode (String code) {
                QueryWrapper<OrderSend> build = new QueryWrapper<OrderSend>();
                build.eq("order_code",code);
                return orderSendMapper.selectOne(build);
        }

        private QueryWrapper<OrderSend> buildWrapper(OrderSendDto OrderSendDto) {
                QueryWrapper<OrderSend> build = new QueryWrapper<>();
                if (OrderSendDto.getOrderCode() !=null && !"".equals(OrderSendDto.getOrderCode())) {
                        build.eq("order_code",OrderSendDto.getOrderCode());
                }

                return build;
        }

        /**
         *新增
         * @param c
         */
        @Transactional
        public int insert (OrderSend c) {
                String orderCode = c.getOrderCode();
                if (StringUtils.isEmpty(orderCode)) {
                        throw new ServiceException(CommMsgCode.NO_DATA,"订单编号orderCode不能为空");
                }

                String orderSendCode = "";
                //返回
                int r = 0;
                try {

                        Integer keyId = c.getId();
                        if (keyId !=null && keyId !=0) {
                                OrderSendVo orderSend = this.getOneById(keyId);
                                orderSendCode = orderSend.getOrderSendCode();
                                r = orderSendMapper.updateById(c); //修改状态
                        } else {
                                orderSendCode = GeneratorKeyUtil.getOrderSendNextCode();
                                c.setOrderSendCode(orderSendCode);
                                r = orderSendMapper.insert(c);;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加发货单失败");
                }

                materialDetailSendService.updateOrInsertOrDelete(orderCode,orderSendCode,c.getMaterialDetailSendList(), "");
                return r;
        }


        /**
         *修改
         * @param c
         */
        public void update (OrderSend c) {
                int keyId = c.getId();
                String orderSendCode = c.getOrderSendCode();
                String orderCode = c.getOrderCode();
                if (keyId ==0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"id获取为空");
                }
                //先修改合同
                try {
                        orderSendMapper.updateById(c);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改发货单失败");
                }

                if(StringUtils.isEmpty(orderSendCode) || StringUtils.isEmpty(orderCode)) {
                        OrderSend o = orderSendMapper.selectById(c.getId());
                        orderSendCode = o.getOrderSendCode();
                        orderCode = o.getOrderCode();
                }
                materialDetailSendService.updateOrInsertOrDelete(orderCode,orderSendCode,c.getMaterialDetailSendList(),"");
        }

        public IPage<OrderSendVo> pageList (OrderSendDto orderSendDto) {
                IPage<OrderSendVo> pageList = orderSendMapper.pageList(orderSendDto.getPage()==null ? new Page<>():orderSendDto.getPage(),orderSendDto);
                List<OrderSendVo> orderList = pageList.getRecords();
                if (orderList !=null && orderList.size() >0) {
                        orderList.stream().forEach((p) ->{
                                p.setJsonArray(JSONArray.parseArray( p.getFileC()));

                                //处理物料
                                p.setMaterialSendVo(
                                        materialDetailSendMapper.findAll(
                                                p.getOrderSendCode()
                                        )
                                );

                        });
                }

                return pageList;
        }


        /**
         * 根据ID查，详情
         * @param id
         * @return
         */
        public OrderSendVo getOneById (int id) {
                return orderSendMapper.getOneById(id);
        }


        public List<MaterialDetailSendOrderVo> getOrderMaterial(OrderSendMaterialDto orderSendMaterialDto) {
                return orderSendMapper.getOrderMaterial(orderSendMaterialDto);
        }

        public List<MaterialDetailSendOrderVo> getSendMaterial(OrderSendMaterialDto orderSendMaterialDto) {
                return orderSendMapper.getSendMaterial(orderSendMaterialDto);
        }

}
