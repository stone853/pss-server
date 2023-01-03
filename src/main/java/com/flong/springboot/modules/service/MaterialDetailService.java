package com.flong.springboot.modules.service;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.Dict;
import com.flong.springboot.modules.entity.EvaIndex;
import com.flong.springboot.modules.entity.MaterialDetail;
import com.flong.springboot.modules.mapper.DictMapper;
import com.flong.springboot.modules.mapper.MaterialDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialDetailService extends ServiceImpl<MaterialDetailMapper, MaterialDetail> {

        @Autowired
        MaterialDetailMapper materialDetailMapper;


        /**
         * 根据foreignCode 批量新增
         * @param list
         * @param foreignCode
         * @param 1 销售合同  2 采购合同 3 供应商
         * @return
         */
        public boolean batchInsert (String foreignCode,List<MaterialDetail> list,String type) {
                if (list == null || list.size() == 0) {
                        return true;
                }
                if (StringUtils.isEmpty(foreignCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"foreignCode编码不能为空");
                }

                try {
                        list.stream().forEach((p) -> {
                                if (null == p.getMaterialCode()) {
                                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"物料编码不能为空");
                                }
                                p.setDetailId(GeneratorKeyUtil.getMaterialDetailNextCode())
                                        .setForeignCode(foreignCode);
                                p.setType(type);
                                }

                        );
                        this.saveBatch(list);
                } catch (ServiceException s) {
                        s.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"物料编码不能为空");
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加物料明细失败");
                }
                return true;
        }


        /**
         * insert or update ,没有则delete
         * @param foreignCode
         * @param list
         * @return
         */
        public boolean updateOrInsertOrDelete (String foreignCode,List<MaterialDetail> list) {
                if (list == null || list.size() == 0) {
                        return true;
                }

                //查询库中目前有的物料明细
                if (StringUtils.isEmpty(foreignCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"请输入foreignCode编码");
                }

                List<Integer> detailIdsB = list.stream().map(MaterialDetail::getId).collect(Collectors.toList());
                detailIdsB.removeIf(p -> p== null);

                //不在里面的先删除
                try {
                        QueryWrapper<MaterialDetail> dw = new QueryWrapper<MaterialDetail>();
                        if (detailIdsB !=null && detailIdsB.size() >0) {
                                dw.notIn("id",detailIdsB);
                        }
                        dw.eq("foreign_code",foreignCode);
                        materialDetailMapper.delete(dw);
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
                                        //如果detail为空，表示新增的，需要获取detailId
                                        if (StringUtils.isEmpty(p.getId())) {
                                                p.setDetailId(GeneratorKeyUtil.getMaterialDetailNextCode());
                                                p.setForeignCode(foreignCode);
                                        }
                                        p.setForeignCode(foreignCode);
                                }

                        );
                        this.saveOrUpdateBatch(list);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加或修改物料明细失败");
                }

                return true;
        }


        public List<MaterialDetail> list (String foreignCode) {
                if (StringUtils.isEmpty(foreignCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"请传入外键编码");
                }

                QueryWrapper<MaterialDetail> qw = new QueryWrapper();
                qw.eq("foreign_code",foreignCode);
                return materialDetailMapper.selectList(qw);
        }




}