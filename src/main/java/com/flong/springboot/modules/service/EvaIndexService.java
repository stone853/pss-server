package com.flong.springboot.modules.service;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.EvaIndex;
import com.flong.springboot.modules.mapper.EvaIndexMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaIndexService extends ServiceImpl<EvaIndexMapper, EvaIndex> {

        @Autowired
        EvaIndexMapper evaIndexMapper;


        /**
         * 根据foreignCode 批量新增
         * @param list
         * @param foreignCode
         * @return
         */
        public boolean batchInsert (String foreignCode,List<EvaIndex> list) {
                if (list == null || list.size() == 0) {
                        return true;
                }
                if (StringUtils.isEmpty(foreignCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"foreignCode编码不能为空");
                }

                try {
                        list.stream().forEach((p) -> {
                                p.setSchemeCode(foreignCode);
                                }
                        );
                        this.saveBatch(list);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加指标失败");
                }
                return true;
        }


        /**
         * insert or update ,没有则delete
         * @param foreignCode
         * @param list
         * @return
         */
        public boolean updateOrInsertOrDelete (String foreignCode,List<EvaIndex> list) {
                if (list == null || list.size() == 0) {
                        return true;
                }

                if (StringUtils.isEmpty(foreignCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"foreignCode编码不能为空");
                }

                //查询库中目前有的物料明细
                List<Integer> detailIdsB = list.stream().map(EvaIndex::getId).collect(Collectors.toList());
                detailIdsB.removeIf(p -> p== null);

                //不在里面的先删除
                try {
                        QueryWrapper<EvaIndex> dw = new QueryWrapper();
                        if (detailIdsB !=null && detailIdsB.size() >0) {
                                dw.notIn("id",detailIdsB);
                        }
                        dw.eq("scheme_code",foreignCode);
                        evaIndexMapper.delete(dw);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"删除指标明细失败");
                }

                //修改物料明细，insert or update
                try {
                        list.stream().forEach((p) ->{
                                        p.setSchemeCode(foreignCode);
                                }

                        );
                        this.saveOrUpdateBatch(list);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加指标明细失败");
                }

                return true;
        }


        public List<EvaIndex> list (String foreignCode) {
                if (StringUtils.isEmpty(foreignCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"请传入方案编码");
                }

                QueryWrapper<EvaIndex> qw = new QueryWrapper();
                qw.eq("scheme_code",foreignCode);
                return evaIndexMapper.selectList(qw);
        }



}