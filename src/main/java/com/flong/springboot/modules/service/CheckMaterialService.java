package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.CheckMaterial;
import com.flong.springboot.modules.entity.MaterialStock;
import com.flong.springboot.modules.entity.dto.CheckMaterialDto;
import com.flong.springboot.modules.entity.vo.CheckMaterialVo;
import com.flong.springboot.modules.mapper.CheckMaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CheckMaterialService extends ServiceImpl<CheckMaterialMapper, CheckMaterial> {
        @Autowired
        CheckMaterialMapper checkMaterialMapper;

        @Autowired
        MaterialStockService materialStockService ;

        @Transactional
        public CheckMaterial insert (CheckMaterial cm) {
                List<MaterialStock> mgtList = cm.getCheckMaterialList();
                if (mgtList ==null || mgtList.size() == 0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"请输入要盘点的物料");
                }

                StringBuilder sb = new StringBuilder();
                mgtList.stream().forEach((p) -> {
                        Integer id = p.getId();
                        if (id == null || id == 0) {
                                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"物料id不能为空");
                        }
                        if (p.getActQuantity().intValue() != p.getQuantity().intValue()) {
                               cm.setCheckResult("1");//有盈亏
                        }
                        p.setUpdTime(UserHelper.getDateTime());
                        sb.append(p.getMaterialName()+",");
                });
                cm.setMaterialName(sb.toString());
                cm.setBillCode(GeneratorKeyUtil.getBillNextCode());
                cm.setOptTime(UserHelper.getDateTime());
                cm.setCheckMaterial(JSONArray.toJSONString(mgtList));
                checkMaterialMapper.insert(cm);

                mgtList.stream().forEach(p->p.setQuantity(p.getActQuantity()));
                materialStockService.updateBatchById(mgtList);

                return cm;
        }

        public IPage<CheckMaterialVo> pageList (CheckMaterialDto checkMaterialDto) {
                IPage<CheckMaterialVo> pageList = checkMaterialMapper.pageList(checkMaterialDto.getPage()==null ? new Page<>():checkMaterialDto.getPage(),checkMaterialDto);
                List<CheckMaterialVo> checkList = pageList.getRecords();
                checkList.stream().forEach((p) -> {
//                        p.setJsonArray(JSONArray.parseArray( p.getFileC()));
//                        p.setCheckMaterialJsonArray(JSONArray.parseArray(p.getCheckMaterial()));
                        convertJsonArray(p);
                });
                return  pageList;
        }

        /**
         * 根据ID查，详情
         * @param id
         * @return
         */
        public CheckMaterialVo getOneById (int id) {
                CheckMaterialVo c = checkMaterialMapper.getOneById(id);
                return convertJsonArray(c);
        }

        /**
         * 根据CODE查，详情
         * @param billCode
         * @return
         */
        public CheckMaterialVo getOneByCode (String billCode) {
                CheckMaterialVo c = checkMaterialMapper.getOneByCode(billCode);
                return convertJsonArray(c);
        }

        private CheckMaterialVo convertJsonArray (CheckMaterialVo c) {
                if (c !=null) {
                        c.setJsonArray(JSONArray.parseArray( c.getFileC()));
                        c.setCheckMaterialJsonArray(JSONArray.parseArray(c.getCheckMaterial()));
                }
                return c;
        }
}