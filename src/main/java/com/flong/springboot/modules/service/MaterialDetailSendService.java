package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.MaterialDetailLog;
import com.flong.springboot.modules.entity.MaterialDetailSend;
import com.flong.springboot.modules.entity.MaterialStock;
import com.flong.springboot.modules.entity.dto.OrderSendMaterialDto;
import com.flong.springboot.modules.entity.vo.MaterialDetailSendOrderVo;
import com.flong.springboot.modules.entity.vo.MaterialDetailSendVo;
import com.flong.springboot.modules.mapper.MaterialDetailSendMapper;
import com.flong.springboot.modules.mapper.OrderSendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MaterialDetailSendService extends ServiceImpl<MaterialDetailSendMapper, MaterialDetailSend> {

        @Autowired
        MaterialDetailSendMapper materialDetailSendMapper;

        @Autowired
        OrderSendMapper orderSendMapper;


        /**
         * insert or update ,没有则delete
         * @param foreignCode
         * @param list
         * @return
         */
        public synchronized boolean updateOrInsertOrDelete (String orderCode,String foreignCode,List<MaterialDetailSend> list,String type) {
                //查询库中目前有的物料明细
                if (StringUtils.isEmpty(foreignCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"请输入foreignCode编码");
                }

                if (list == null || list.size() == 0) {
                        QueryWrapper<MaterialDetailSend> dw = new QueryWrapper<MaterialDetailSend>();
                        dw.eq("foreign_code",foreignCode);
                        materialDetailSendMapper.delete(dw);
                        return true;
                }
                //判断物料code重复
                List<String> tempList = list.stream().map(MaterialDetailSend::getMaterialCode).distinct().collect(Collectors.toList());
                if (tempList.size() != list.size()) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"物料编码重复");
                }

                //判断发送数量是否超过剩余数量
                isSendable(orderCode,foreignCode,list);
                //不在里面的先删除
                List<Integer> detailIdsB = list.stream().map(MaterialDetailSend::getId).collect(Collectors.toList());
                detailIdsB.removeIf(p -> p== null);
                try {
                        QueryWrapper<MaterialDetailSend> dw = new QueryWrapper<MaterialDetailSend>();
                        if (detailIdsB !=null && detailIdsB.size() >0) {
                                dw.notIn("id",detailIdsB);
                        }
                        dw.eq("foreign_code",foreignCode);
                        materialDetailSendMapper.delete(dw);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"删除物料明细失败");
                }

                //修改物料明细，insert or update
                try {
                        list.stream().forEach((p) ->{
                                        if (null == p.getMaterialCode()) {
                                                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"物料编码不能为空");
                                        }

                                        if (StringUtils.isEmpty(p.getDetailId())) {
                                                p.setDetailId(GeneratorKeyUtil.getMaterialDetailNextCode());
                                        }

                                        p.setForeignCode(foreignCode);
                                        p.setOrderCode(orderCode);
                                        p.setSourceType(type);
                                }

                        );
                        this.saveOrUpdateBatch(list);
                }catch (ServiceException e) {
                        throw e;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加或修改物料明细失败");
                }

                return true;
        }

        /**
         * insert or update ,没有则delete
         * @param foreignCode
         * @param list
         * @return
         */
        public boolean acptQuantity (String foreignCode,List<MaterialDetailSend> list,String type) {
                //查询库中目前有的物料明细
                if (StringUtils.isEmpty(foreignCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"请输入foreignCode编码");
                }
                if (list == null || list.size() == 0) {
                        return true;
                }
                //修改物料明细，insert or update
                try {
                        list.stream().forEach((p) ->{

                                        String materialCode = p.getMaterialCode();
                                        if (StringUtils.isEmpty(materialCode)) {
                                                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"验收materialCode不能为空");
                                        }
                                        String acptRemark = p.getAcptRemark();
                                        int acptQuantity = p.getAcptQuantity();
                                        updAcptQuantity(foreignCode,materialCode,acptQuantity,acptRemark);
                                }

                        );
                }catch (ServiceException e) {
                        throw e;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"验收物料数量有误");
                }

                return true;
        }

        public void updAcptQuantity (String foreignCode,String materialCode,int acptQuantity,String acptRemark) {
                if (StringUtils.isEmpty(materialCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改物料code为空");
                }

                QueryWrapper<MaterialDetailSend> q = new QueryWrapper<>();
                q.eq("material_code",materialCode);
                q.eq("foreign_code",foreignCode);
                q.last("limit 1");
                MaterialDetailSend m = this.getOne(q);

                if (acptQuantity !=0) {
                        UpdateWrapper<MaterialDetailSend> uSql = new UpdateWrapper<>();
                        uSql.set("acpt_quantity",acptQuantity);
                        uSql.set("acpt_remark",acptRemark);
                        uSql.eq("material_code", materialCode);
                        uSql.eq("foreign_code", foreignCode);
                        materialDetailSendMapper.update(null,uSql);
                }

        }


        private boolean isSendable (String orderCode,String orderSendCode,List<MaterialDetailSend> list) {
                OrderSendMaterialDto o = new OrderSendMaterialDto();
                o.setOrderCode(orderCode);
                o.setOrderSendCode(orderSendCode);
                List<MaterialDetailSendOrderVo> orderList =orderSendMapper.getOrderMaterial(o);
                Map<String,MaterialDetailSendOrderVo> map= orderList.stream().collect(Collectors.toMap(MaterialDetailSendOrderVo :: getMaterialCode, p -> p));

                if (orderList == null || orderList.size() ==0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"发货单:"+orderCode+"为空，不能发货");
                }

                list.stream().forEach((p) -> {
                        String materialCode = p.getMaterialCode();
                        Integer sendQuantity= p.getSendQuantity();

                        if (sendQuantity > 0) {
                                MaterialDetailSendOrderVo m = map.get(materialCode);
                                if (m == null || sendQuantity > m.getRemainQuantity()) {
                                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"发送数量超过剩余数量");
                                }
                        }
                });

                return true;

        }

//        public List<MaterialDetailSendVo> list (String foreignCode) {
//
//                return materialDetailSendMapper.findAll(foreignCode);
//        }



        public List<MaterialDetailSendVo> getByOrderSendCode (String foreignCode) {
                return materialDetailSendMapper.getByOrderSendCode(foreignCode);
        }


}