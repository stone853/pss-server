package com.flong.springboot.modules.service;



import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.MaterialDetailLog;
import com.flong.springboot.modules.entity.vo.MaterialDetailLogVo;
import com.flong.springboot.modules.mapper.MaterialDetailLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialDetailLogService extends ServiceImpl<MaterialDetailLogMapper, MaterialDetailLog> {

        @Autowired
        MaterialDetailLogMapper materialDetailLogMapper;


        /**
         * 根据foreignCode 批量新增
         * @param list
         * @param foreignCode
         * @param  type 1 入库 2 出库 3 盘点
         * @return
         */
        public boolean batchInsert (String foreignCode,List<MaterialDetailLog> list,String type) {
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

                                List<FileBean> fileBeanList = p.getFileBeanList();
                                if (fileBeanList !=null && fileBeanList.size() > 0) {
                                        p.setFileC(JSONArray.toJSONString(fileBeanList));
                                }

                                p.setDetailId(GeneratorKeyUtil.getMaterialDetailNextCode())
                                        .setForeignCode(foreignCode);
                                p.setSourceType(type);
                                }

                        );
                        this.saveBatch(list);
                } catch (ServiceException s) {
                        throw s;
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
        public boolean updateOrInsertOrDelete (String foreignCode,List<MaterialDetailLog> list,String type) {
                //查询库中目前有的物料明细
                if (StringUtils.isEmpty(foreignCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"请输入foreignCode编码");
                }
                if (list == null || list.size() == 0) {
                        QueryWrapper<MaterialDetailLog> dw = new QueryWrapper();
                        dw.eq("foreign_code",foreignCode);
                        materialDetailLogMapper.delete(dw);
                        return true;
                }
                //判断物料code重复
                List<String> tempList = list.stream().map(MaterialDetailLog::getMaterialCode).distinct().collect(Collectors.toList());
                if (tempList.size() != list.size()) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"物料编码重复");
                }


                //不在里面的先删除
                List<Integer> detailIdsB = list.stream().map(MaterialDetailLog::getId).collect(Collectors.toList());
                detailIdsB.removeIf(p -> p== null);
                try {
                        QueryWrapper<MaterialDetailLog> dw = new QueryWrapper();
                        if (detailIdsB !=null && detailIdsB.size() >0) {
                                dw.notIn("id",detailIdsB);
                        }
                        dw.eq("foreign_code",foreignCode);
                        materialDetailLogMapper.delete(dw);
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
                                        p.setSourceType(type);

                                        //设置fileBean
                                        FileBean f = new FileBean();
                                        p.setFileC(f.fileBeanListToString(p.getFileBeanList()));
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


        public List<MaterialDetailLogVo> list (String foreignCode) {

                return materialDetailLogMapper.findAll(foreignCode);
        }




}