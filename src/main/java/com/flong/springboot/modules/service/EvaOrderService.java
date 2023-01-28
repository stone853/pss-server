package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.EvaIndex;
import com.flong.springboot.modules.entity.EvaOrder;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.dto.ContractPurchaseDto;
import com.flong.springboot.modules.entity.dto.EvaOrderDto;
import com.flong.springboot.modules.entity.vo.ContractPurchaseVo;
import com.flong.springboot.modules.entity.vo.EvaOrderVo;
import com.flong.springboot.modules.mapper.EvaIndexMapper;
import com.flong.springboot.modules.mapper.EvaOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaOrderService extends ServiceImpl<EvaOrderMapper, EvaOrder> {
        @Autowired
        EvaOrderMapper evaOrderMapper;

        @Autowired
        EvaIndexMapper evaIndexMapper;


        public EvaOrder insert (EvaOrder evaOrder) {
                String orderCode = evaOrder.getOrderCode();
                if (StringUtils.isEmpty(orderCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"orderCode编码不能为空");
                }

                List<FileBean> fileBeanList = evaOrder.getFileBeanList();
                if (fileBeanList !=null && fileBeanList.size() > 0) {
                        FileBean f = new FileBean();
                        evaOrder.setFileC(f.fileBeanListToString(evaOrder.getFileBeanList()));
                }

                List<EvaIndex> evaIndexList = evaOrder.getEvaIndexList();
                if (evaIndexList ==null || evaIndexList.size() == 0) {
                        List<EvaIndex> el = evaIndexMapper.findIndexList();
                        evaOrder.setEvaIndexList(el);

                }
                evaOrder.setIndexString(JSONArray.toJSONString(evaOrder.getEvaIndexList()));
                evaOrder.setEvaTime(UserHelper.getDateTime());
                evaOrder.setEvaStatus("0");

                evaOrderMapper.insert(evaOrder);
                return evaOrder;
        }


        public EvaOrder update (EvaOrder evaOrder) {
                Integer keyId = evaOrder.getId();
                if (keyId == null || keyId == 0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"keyId编码不能为空");
                }

                List<FileBean> fileBeanList = evaOrder.getFileBeanList();
                if (fileBeanList !=null && fileBeanList.size() > 0) {
                        FileBean f = new FileBean();
                        evaOrder.setFileC(f.fileBeanListToString(evaOrder.getFileBeanList()));
                }

                evaOrder.setEvaResult(calScore(evaOrder));
                evaOrder.setIndexString(JSONArray.toJSONString(evaOrder.getEvaIndexList()));
                evaOrder.setEvaTime(UserHelper.getDateTime());
                evaOrder.setEvaStatus("1");

                evaOrderMapper.updateById(evaOrder);
                return evaOrder;
        }

        public IPage<EvaOrderVo> pageList (EvaOrderDto evaOrderDto) {
                IPage<EvaOrderVo> pageList = evaOrderMapper.pageList(evaOrderDto.getPage()==null ? new Page<>():evaOrderDto.getPage(),evaOrderDto);
                List<EvaOrderVo> evaOrderList = pageList.getRecords();
                if (evaOrderList !=null && evaOrderList.size() > 0) {
                        evaOrderList.stream().forEach((p) -> {
                                p.setJsonArray(JSONArray.parseArray( p.getFileC()));
                                p.setIndexList(JSONArray.parseArray(p.getIndexString()));
                        });
                }
                return  pageList;
        }

        /**
         * 计算得分
         * @param evaOrder
         * @return
         */
        private double calScore (EvaOrder evaOrder) {
                double sumScore = 0;
                try {
                        List<EvaIndex> list = evaOrder.getEvaIndexList();
                        if (list != null && list.size() > 0) {
                                for (int i = 0;i < list.size();i ++) {
                                        EvaIndex evaIndex = list.get(i);
                                        sumScore += evaIndex.getIndexScore() * evaIndex.getIndexWeight()/100;
                                }
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                        new ServiceException(CommMsgCode.BIZ_INTERRUPT,"计算得分失败");
                }
                return sumScore;

        }
}
