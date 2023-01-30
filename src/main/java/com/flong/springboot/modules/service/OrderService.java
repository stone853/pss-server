package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.model.OrderTypeEnum;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.PageUtils;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.CommonConstant;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.process.OrderPurProcessHandle;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.Order;
import com.flong.springboot.modules.entity.dto.ContractPurchaseDto;
import com.flong.springboot.modules.entity.dto.OrderDto;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.entity.vo.ContractPurchaseVo;
import com.flong.springboot.modules.entity.vo.OrderVo;
import com.flong.springboot.modules.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class OrderService extends ServiceImpl<OrderMapper, Order> {
        @Autowired
        OrderMapper orderMapper;

        @Autowired
        MaterialDetailLogService materialDetailLogService;

        @Autowired
        OrderPurProcessHandle processHandle;

        @Autowired
        UserService userService;


        public IPage<Order> page (OrderDto OrderDto) {
                QueryWrapper<Order> build = buildWrapper(OrderDto);
                return orderMapper.selectPage(OrderDto.getPage()==null ? new Page<>() : OrderDto.getPage(),build);
        }

        public Order getOneByCode (String code) {
                QueryWrapper<Order> build = new QueryWrapper<Order>();
                build.eq("Order_code",code);
                return orderMapper.selectOne(build);
        }

        private QueryWrapper<Order> buildWrapper(OrderDto orderDto) {
                QueryWrapper<Order> build = new QueryWrapper<>();
                if (orderDto.getOrderCode() !=null && !"".equals(orderDto.getOrderCode())) {
                        build.eq("order_code", orderDto.getOrderCode());
                }

                return build;
        }

        /**
         *新增
         * @param c
         */
        @Transactional
        public Order insert (Order c) {
                String subStatus = CommonConstant.ORDER_SUB_STATUS;
                String orderStatus = c.getStatus();
                String processId = "";
                String processType = "";
                String orderCode = "";
                String orderType = c.getOrderType();
                try {
                        if (StringUtils.isEmpty(orderType)) {
                                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"订单类型不能为空");
                        }

                        //采购订单
                        if (orderType.equals(OrderTypeEnum.IN.getCode())) {
                                FileBean f = new FileBean();
                                c.setFileC(f.fileBeanListToString(c.getFileBeanList()));
                                //设置供应商
                                c.setSupplierCode(userService.getUserDeptCode(new UserDto().setUserId(c.getApplicant())));

                                processType = CommonConstant.ORDER_PUR_TYPE;
                        } else if (orderType.equals(OrderTypeEnum.OUT.getCode())){
                                if (StringUtils.isEmpty(c.getOrderClass())) {
                                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"出库单需要输入orderClass出库类型");
                                }

                                processType = CommonConstant.ORDER_OUT_TYPE;
                        }
                        c.setApplicationDate(UserHelper.getDate());

                        Integer keyId = c.getId();
                        //处理流程  begin
                        if (keyId !=null && keyId !=0) {
                                Order order = this.getOneById(keyId);
                                processId = order.getProcessId();
                        }

                        processId = processHandle.handleProcessByStatus(c.getApplicant(),keyId,processId,c.getOrderCode(),orderStatus,subStatus,processType);

                        if (StringUtils.isNotEmpty(orderStatus) && orderStatus.equals(subStatus) && StringUtils.isEmpty(c.getProcessId())) {
                                c.setProcessId(processId);
                        }
                        //处理流程  end

                        if (keyId !=null && keyId !=0) {
                                Order order = this.getOneById(keyId);
                                orderCode = order.getOrderCode();
                                orderMapper.updateById(c); //修改状态
                        } else {
                                orderCode = GeneratorKeyUtil.getOrderNextCode();
                                c.setOrderCode(orderCode);
                                orderMapper.insert(c);
                        }
                }catch (ServiceException e) {
                        throw e;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加订单失败");
                }

                materialDetailLogService.updateOrInsertOrDelete(orderCode,c.getMaterialDetailLogList(), orderType);
                return c;
        }


        /**
         *修改
         * @param c
         */
        public void update (Order c) {
                int keyId = c.getId();
                String orderCode = c.getOrderCode();
                if (keyId ==0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"id获取为空");
                }
                //先修改合同
                try {
                        orderMapper.updateById(c);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改销售合同失败");
                }

                if(StringUtils.isEmpty(orderCode)) {
                        Order o = orderMapper.selectById(c.getId());
                        orderCode = o.getOrderCode();
                }
                materialDetailLogService.updateOrInsertOrDelete(orderCode,c.getMaterialDetailLogList(), OrderTypeEnum.IN.getCode());
        }

        /**
         * 采购订单
         * @param orderDto
         * @return
         */
        public IPage<OrderVo> pagePurList (OrderDto orderDto) {
                IPage<OrderVo> pageList = orderMapper.pagePurList(orderDto.getPage()==null ? new Page<>(): orderDto.getPage(), orderDto);
                return invokePage(orderDto,pageList);
        }

        /**
         * 出库单
         * @param orderDto
         * @return
         */
        public IPage<OrderVo> pageOutList (OrderDto orderDto) {
                IPage<OrderVo> pageList = orderMapper.pageOutList(orderDto.getPage()==null ? new Page<>(): orderDto.getPage(), orderDto);
                return invokePage(orderDto,pageList);
        }

        /**
         * 处理分页信息
         * @param pageList
         * @return
         */
        private IPage<OrderVo> invokePage(OrderDto orderDto,IPage<OrderVo> pageList) {
                List<OrderVo> orderList = pageList.getRecords();
                if (orderList !=null && orderList.size() >0) {
                        orderList.stream().forEach((p) ->{
                                p.setJsonArray(JSONArray.parseArray( p.getFileC()));
                                setOptButton(orderDto,p);
                        });
                }

                return pageList;
        }

        /**
         * 根据ID查，详情
         * @param id
         * @return
         */
        public OrderVo getOneById (int id) {
                return orderMapper.getOneById(id);
        }


        private OrderVo setOptButton (OrderDto dto, OrderVo c) {
                if (c == null) {
                        return null;
                }
                String userId = dto.getUserId();
                String[] userRoles = userService.getUserRoles(new UserDto().setUserId(userId));
                log.info("获取页面操作按钮:userId:{}", userId);

                PageUtils pu = new PageUtils();
                List<String> buttonList = pu.getOptButtons(dto.getUserId(),c.getApplicant(),userRoles,c.getCreateUserButton(),
                        c.getCheckRoleCode(),c.getCheckUserButton());
                c.setOptButton(buttonList);
                return c;
        }


        public Order getOrder (String processId) {
                QueryWrapper<Order> q = new QueryWrapper<>();
                q.eq("process_id",processId);
                q.last("limit 1");
                Order order = this.getOne(q);
                if (order == null) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"找不到流程对应订单，processId:"+processId);
                }
                return order;
        }
}
