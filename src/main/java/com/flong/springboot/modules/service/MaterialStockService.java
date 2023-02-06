package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.MaterialStock;
import com.flong.springboot.modules.entity.dto.MaterialStockDto;
import com.flong.springboot.modules.entity.dto.MaterialStockLogDto;
import com.flong.springboot.modules.entity.vo.ContractPurchaseVo;
import com.flong.springboot.modules.entity.vo.MaterialStockDetailVo;
import com.flong.springboot.modules.entity.vo.MaterialStockLogVo;
import com.flong.springboot.modules.entity.vo.MaterialStockVo;
import com.flong.springboot.modules.mapper.MaterialStockMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialStockService extends ServiceImpl<MaterialStockMapper, MaterialStock> {


        @Autowired
        MaterialStockMapper materialStockMapper;


        /**
         * 查库存
         * @param materialStockDto
         * @return
         */
        public List<MaterialStockVo> findAll (MaterialStockDto materialStockDto) {
                return materialStockMapper.findAll(materialStockDto);
        }

        /**
         * 查库存详情
         * @param materialStockDto
         * @return
         */
        public IPage<MaterialStockDetailVo> pageList (MaterialStockDto materialStockDto) {
                return materialStockMapper.pageList(materialStockDto.getPage()==null ? new Page<>():materialStockDto.getPage(),materialStockDto);
        }

        /**
         * 查库存流水
         * @param materialStockLogDto
         * @return
         */
        public IPage<MaterialStockLogVo> pageMaterialLogList (MaterialStockLogDto materialStockLogDto) {
                return materialStockMapper.pageMaterialLogList(materialStockLogDto.getPage()==null ? new Page<>():materialStockLogDto.getPage(),materialStockLogDto);
        }

        public void subOutOrder (String materialCode,int outQuantity) {
                if (StringUtils.isEmpty(materialCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改物料code为空");
                }

                QueryWrapper<MaterialStock> q = new QueryWrapper<>();
                q.eq("material_code",materialCode);
                q.last("limit 1");
                MaterialStock m = this.getOne(q);
                if (m.getQuantity().intValue() < outQuantity) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"库存余额不足");
                }

                if (outQuantity !=0) {
                        LambdaUpdateWrapper<MaterialStock> uSql = new LambdaUpdateWrapper<>();
                        uSql.setSql("quantity = IFNULL(quantity,0)-" + outQuantity);
                        uSql.eq(MaterialStock::getMaterialCode, materialCode);
                        materialStockMapper.update(null,uSql);
                }

        }


        public synchronized void subInOrder (String materialCode,String materialName,String remark,int inQuantity) {
                if (StringUtils.isEmpty(materialCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"修改物料code为空");
                }

                QueryWrapper<MaterialStock> q = new QueryWrapper<>();
                q.eq("material_code",materialCode);
                q.last("limit 1");
                MaterialStock m = this.getOne(q);

                if (m == null ){
                        m = new MaterialStock();
                        m.setMaterialCode(materialCode);
                        m.setMaterialName(materialName);
                        m.setQuantity(inQuantity);
                        m.setRemark(remark);
                        m.setUpdTime(UserHelper.getDateTime());
                        this.save(m);
                        return;
                }

                if (inQuantity !=0) {
                        LambdaUpdateWrapper<MaterialStock> uSql = new LambdaUpdateWrapper<>();
                        uSql.setSql("quantity = IFNULL(quantity,0)+" + inQuantity);
                        uSql.eq(MaterialStock::getMaterialCode, materialCode);
                        materialStockMapper.update(null,uSql);
                }

        }





}
