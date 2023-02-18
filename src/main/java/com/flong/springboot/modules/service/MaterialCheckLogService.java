package com.flong.springboot.modules.service;



import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.MaterialCheckLog;
import com.flong.springboot.modules.entity.vo.MaterialStockDetailVo;
import com.flong.springboot.modules.mapper.MaterialCheckLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialCheckLogService extends ServiceImpl<MaterialCheckLogMapper, MaterialCheckLog> {

        @Autowired
        MaterialCheckLogMapper materialCheckLogMapper;




        /**
         * insert or update ,没有则delete
         * @param foreignCode
         * @param list
         * @return
         */
        public boolean updateOrInsertOrDelete (String foreignCode,List<MaterialCheckLog> list) {
                //查询库中目前有的物料明细
                if (StringUtils.isEmpty(foreignCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"请输入foreignCode编码");
                }
                //先删除
                try {
                        QueryWrapper<MaterialCheckLog> dw = new QueryWrapper();
                        dw.eq("foreign_code",foreignCode);
                        materialCheckLogMapper.delete(dw);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"删除物料明细失败");
                }

                if (list == null || list.size() == 0) {//控直接返回
                        return true;
                }
                //判断物料code重复
                List<String> tempList = list.stream().map((p) -> p.getMaterialCode()).distinct().collect(Collectors.toList());
                if (tempList.size() != list.size()) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"物料重复");
                }

                //修改物料明细，insert or update
                try {
                        list.stream().forEach((p) ->{
                                        if (null == p.getMaterialCode()) {
                                                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"物料编码不能为空");
                                        }
                                        p.setForeignCode(foreignCode);
                                }

                        );
                        this.saveBatch(list);
                }catch (ServiceException e) {
                        throw e;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"添加或修改物料明细失败");
                }

                return true;
        }




}