package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.CommonConstant;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.process.OrderPurProcessHandle;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.Order;
import com.flong.springboot.modules.entity.OrderSend;
import com.flong.springboot.modules.entity.OrderSendLog;
import com.flong.springboot.modules.entity.dto.OrderSendDto;
import com.flong.springboot.modules.entity.dto.OrderSendMaterialDto;
import com.flong.springboot.modules.entity.dto.PssProcessDto;
import com.flong.springboot.modules.entity.dto.UpdSendStatus;
import com.flong.springboot.modules.entity.vo.MaterialDetailSendOrderVo;
import com.flong.springboot.modules.entity.vo.OrderSendLogVo;
import com.flong.springboot.modules.entity.vo.OrderSendVo;
import com.flong.springboot.modules.mapper.MaterialDetailSendMapper;
import com.flong.springboot.modules.mapper.OrderSendLogMapper;
import com.flong.springboot.modules.mapper.OrderSendMapper;
import lombok.extern.slf4j.Slf4j;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.flong.springboot.core.exception.CommMsgCode.NOT_FOUND;

@Slf4j
@Service
public class OrderSendService extends ServiceImpl<OrderSendMapper, OrderSend> {
        @Autowired
        OrderSendMapper orderSendMapper;

        @Autowired
        MaterialDetailSendMapper materialDetailSendMapper;

        @Autowired
        MaterialDetailSendService materialDetailSendService;

        @Autowired
        PssProcessService pssProcessService;

        @Autowired
        OrderService orderService;

        @Autowired
        OrderSendLogService orderSendLogService;

        @Autowired
        OrderSendLogMapper orderSendLogMapper;


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
         *??????
         * @param c
         */
        @Transactional
        public OrderSend insert (OrderSend c) {
                String orderCode = c.getOrderCode();
                if (StringUtils.isEmpty(orderCode)) {
                        throw new ServiceException(CommMsgCode.NO_DATA,"????????????orderCode????????????");
                }
                String orderSendCode = "";
                try {
                        c.setSendTime(UserHelper.getDateTime());

                        Integer keyId = c.getId();
                        if (keyId !=null && keyId !=0) {
                                OrderSendVo orderSend = this.getOneById(keyId);
                                orderSendCode = orderSend.getOrderSendCode();
                                orderSendMapper.updateById(c); //????????????
                        } else {
                                orderSendCode = GeneratorKeyUtil.getOrderSendNextCode();
                                c.setOrderSendCode(orderSendCode);
                                orderSendMapper.insert(c);;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"?????????????????????");
                }

                materialDetailSendService.updateOrInsertOrDelete(orderCode,orderSendCode,c.getMaterialDetailSendList(), "");

                //????????????
                insertOrderSendLog(c,orderSendCode);
                return c;
        }


        /**
         * ?????????????????????????????????
         * @param orderCode
         * @return
         */
        public boolean isSendAll (String orderCode) {
                //??????????????????????????????
                QueryWrapper<OrderSend> q1 = new QueryWrapper<>();
                q1.eq("order_code",orderCode);
                List<OrderSend> list1 = this.list(q1);
                if (list1 ==null || list1.size() ==0) {
                        return false;
                }

                //?????????????????????????????????
                QueryWrapper<OrderSend> q = new QueryWrapper<>();
                q.eq("order_code",orderCode);
                q.in("send_status","0","2");  // 0 ????????? 2 ????????????
                List<OrderSend> list = this.list(q);
                if (list !=null && list.size() >0) {
                        return false;
                }
                return true;
        }

        /**
         * ?????????????????????????????????
         * @param orderCode
         * @return
         */
        public boolean isCheckSendAll (String orderCode) {
                QueryWrapper<OrderSend> q = new QueryWrapper<>();
                q.eq("order_code",orderCode);
                q.ne("send_status","3");
                List<OrderSend> list = this.list(q);
                if (list !=null && list.size() >0) {
                        return false;
                }
                return true;
        }

        /**
         * ?????????????????????  3 ??????  2 ??????
         * @param c
         */
        @Transactional
        public void acptOrderSend (OrderSend c) {
                Integer keyId = c.getId();
                if (keyId == null) {
                        throw new ServiceException(CommMsgCode.NO_DATA, "?????????keyId????????????");
                }

                String orderSendCode = "";
                try {
                        OrderSendVo orderSend = this.getOneById(keyId);
                        orderSendCode = orderSend.getOrderSendCode();
                        if (c.getSendStatus().equals("3")) {
                                c.setAcptResult("????????????");
                        } else {
                                c.setAcptResult("????????????");
                        }
                        c.setAcptTime(UserHelper.getDateTime());
                        c.setAcptUserId(c.getUserId());
                        orderSendMapper.updateById(c); //????????????

                        materialDetailSendService.acptQuantity(orderSendCode, c.getMaterialDetailSendList(), "");


                        //????????????
                        insertOrderSendLog(c,orderSendCode);
                } catch (ServiceException e) {
                        throw e;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT, "?????????????????????");
                }
        }


        /**
         *??????
         * @param c
         */
        public void update (OrderSend c) {
                int keyId = c.getId();
                String orderSendCode = c.getOrderSendCode();
                String orderCode = c.getOrderCode();
                if (keyId ==0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"id????????????");
                }
                //???????????????
                c.setSendTime(UserHelper.getDateTime());
                try {
                        orderSendMapper.updateById(c);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"?????????????????????");
                }

                if(StringUtils.isEmpty(orderSendCode) || StringUtils.isEmpty(orderCode)) {
                        OrderSend o = orderSendMapper.selectById(c.getId());
                        orderSendCode = o.getOrderSendCode();
                        orderCode = o.getOrderCode();
                }

                materialDetailSendService.updateOrInsertOrDelete(orderCode,orderSendCode,c.getMaterialDetailSendList(),"");

                //????????????
                insertOrderSendLog(c,orderSendCode);
        }

        public void updateOrderSend (String userId,OrderSend orderSend) {

                this.updateById(orderSend);
                //????????????
                OrderSend o = this.getOneById(orderSend.getId());
                String orderSendCode = o.getOrderSendCode();
                o.setUserId(userId);
                insertOrderSendLog(o,orderSendCode);
        }


        private void insertOrderSendLog (OrderSend c,String orderSendCode) {
                if (StringUtils.isNotEmpty(c.getSendStatus())) {
                        //????????????
                        OrderSendLog orderSendLog = new OrderSendLog();
                        orderSendLog.setSendStatus(c.getSendStatus());
                        orderSendLog.setOrderSendCode(orderSendCode);
                        orderSendLog.setOptUser(c.getUserId());
                        orderSendLog.setOptTime(UserHelper.getDateTime());
                        orderSendLogService.save(orderSendLog);
                }
        }

        public IPage<OrderSendVo> pageList (OrderSendDto orderSendDto) {
                IPage<OrderSendVo> pageList = orderSendMapper.pageList(orderSendDto.getPage()==null ? new Page<>():orderSendDto.getPage(),orderSendDto);
                List<OrderSendVo> orderList = pageList.getRecords();
                if (orderList !=null && orderList.size() >0) {
                        orderList.stream().forEach((p) ->{
                                p.setJsonArray(JSONArray.parseArray( p.getFileC()));
                                p.setAcptJsonArray(JSONArray.parseArray( p.getAcptFileC()));
                                //????????????
                                p.setMaterialSendVo(
                                        materialDetailSendMapper.findAll(
                                                p.getOrderSendCode()
                                        )
                                );

                        });
                }

                return pageList;
        }

        private void handleProcess (OrderSend orderSend) {
                String orderCode = orderSend.getOrderCode();
                Order order = orderService.getOneByCode(orderCode);
                if (order == null) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"??????????????????:"+orderCode);
                }
                String processId = order.getProcessId();
                if (StringUtils.isEmpty(processId)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,orderCode+"??????processId??????");
                }
                PssProcessDto processDto = new PssProcessDto();
                processDto.setOpinion("??????");
                processDto.setResult(1);
                processDto.setProcessId(processId);
                pssProcessService.orderPurProcess(CommonConstant.ORDER_PUR_TYPE,processDto);
                log.info("???????????????{}????????????", processId);
        }


        /**
         * ??????ID????????????
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

        public List<OrderSendLogVo> getSendLogList (String orderSendCode) {
                return orderSendLogMapper.getByOrderSendCode(orderSendCode);
        }

}
