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
import com.flong.springboot.modules.entity.ContractSale;
import com.flong.springboot.modules.entity.MaterialDetailLog;
import com.flong.springboot.modules.entity.OrderPurchase;
import com.flong.springboot.modules.entity.dto.OrderPurchaseDto;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.entity.vo.OrderPurchaseVo;
import com.flong.springboot.modules.entity.vo.UserVo;
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

        @Autowired
        UserService userService;


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
                        c.setApplicationDate(UserHelper.getDate());

                        Integer keyId = c.getId();

                        //设置供应商
                        c.setSupplierCode(userService.getUserDeptCode(new UserDto().setUserId(c.getApplicant())));

                        if (keyId !=null && keyId !=0) {
                                OrderPurchase orderPurchase = this.getOneById(keyId);
                                orderCode = orderPurchase.getOrderCode();
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

                materialDetailLogService.updateOrInsertOrDelete(orderCode,c.getMaterialDetailLogList(), MaterialLogTypeEnum.IN.getCode());
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
                materialDetailLogService.updateOrInsertOrDelete(orderCode,c.getMaterialDetailLogList(),MaterialLogTypeEnum.IN.getCode());
        }

        public IPage<OrderPurchaseVo> pageList (OrderPurchaseDto orderPurchaseDto) {
                IPage<OrderPurchaseVo> pageList = orderPurchaseMapper.pageList(orderPurchaseDto.getPage()==null ? new Page<>():orderPurchaseDto.getPage(),orderPurchaseDto);
                List<OrderPurchaseVo> orderList = pageList.getRecords();
                if (orderList !=null && orderList.size() >0) {
                        orderList.stream().forEach((p) ->{
                                p.setJsonArray(JSONArray.parseArray( p.getFileC()));
                                List<MaterialDetailLog> mdlList = p.getMaterialDetailLogList();
                                if (mdlList !=null && mdlList.size() >0) {
                                        mdlList.stream().forEach(x -> x.setJsonArray(JSONArray.parseArray( x.getFileC())));
                                }

                        });
                }

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
