package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.CheckMaterial;
import com.flong.springboot.modules.entity.MaterialCheckLog;
import com.flong.springboot.modules.entity.MaterialDetailLog;
import com.flong.springboot.modules.entity.MaterialStock;
import com.flong.springboot.modules.entity.dto.CheckMaterialDto;
import com.flong.springboot.modules.entity.vo.CheckMaterialVo;
import com.flong.springboot.modules.mapper.CheckMaterialMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CheckMaterialService extends ServiceImpl<CheckMaterialMapper, CheckMaterial> {
        @Autowired
        CheckMaterialMapper checkMaterialMapper;

        @Autowired
        MaterialStockService materialStockService ;

        @Autowired
        MaterialCheckLogService materialCheckLogService;

        @Transactional
        public CheckMaterial insert (CheckMaterial cm) {
                List<MaterialStock> mgtList = cm.getCheckMaterialList();
                if (mgtList ==null || mgtList.size() == 0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"请输入要盘点的物料");
                }

                String billCode = GeneratorKeyUtil.getBillNextCode();

                //盘点流水
                List<MaterialCheckLog> materialCheckLogList = new ArrayList<>();

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

                        //记录盘点流水
                        MaterialCheckLog mcl = new MaterialCheckLog();
                        mcl.setQuantity(p.getActQuantity().intValue() - p.getQuantity().intValue());
                        mcl.setForeignCode(billCode);
                        mcl.setMaterialCode(p.getMaterialCode());
                        mcl.setMaterialName(p.getMaterialName());
                        materialCheckLogList.add(mcl);

                });
                cm.setMaterialName(sb.toString());
                cm.setBillCode(billCode);
                cm.setOptTime(UserHelper.getDateTime());
                cm.setCheckMaterial(JSONArray.toJSONString(mgtList));
                checkMaterialMapper.insert(cm);




                mgtList.stream().forEach((p)-> {
                        p.setQuantity(p.getActQuantity());//修改库存值
                });
                materialStockService.updateBatchById(mgtList);


                //插入盘点流水
                materialCheckLogService.updateOrInsertOrDelete(billCode,materialCheckLogList);
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

        /**
         * 作废
         * @param checkMaterial
         */
        @Transactional
        public void cancel (CheckMaterial checkMaterial) {
                String billCode = checkMaterial.getBillCode();
                if (StringUtils.isEmpty(billCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"作废盘点号不能为空");
                }
                QueryWrapper<MaterialCheckLog> q = new QueryWrapper<>();
                q.eq("foreign_code",billCode);
                List<MaterialCheckLog> list = materialCheckLogService.list(q);

                list.stream().forEach((p) ->{
                        Integer quantity = p.getQuantity();
                        if (quantity.intValue() < 0) {
                                materialStockService.subInOrder(p.getMaterialCode(),p.getMaterialName(),"盘点作废-增加",-quantity);
                        } else if  (quantity.intValue() > 0) {
                                materialStockService.subOutOrder(p.getMaterialCode(),quantity);
                        }
                });

                //删除流水
                QueryWrapper<MaterialCheckLog> dw = new QueryWrapper();
                dw.eq("foreign_code",billCode);
                materialCheckLogService.remove(dw);

                //修改盘点单状态
                UpdateWrapper<CheckMaterial> upd = new UpdateWrapper<>();
                upd.set("bill_status","0");
                upd.eq("bill_code",billCode);
                this.update(upd);
        }
}
