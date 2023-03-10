package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.Customer;
import com.flong.springboot.modules.entity.MaterialDetail;
import com.flong.springboot.modules.entity.MaterialDetailSend;
import com.flong.springboot.modules.entity.MaterialMgt;
import com.flong.springboot.modules.entity.dto.MaterialMgtDto;
import com.flong.springboot.modules.entity.vo.MaterialMgtVo;
import com.flong.springboot.modules.mapper.MaterialMgtMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialMgtService extends ServiceImpl<MaterialMgtMapper, MaterialMgt> {
        @Autowired
        MaterialMgtMapper materialMgtMapper;

        @Autowired
        MaterialDetailService materialDetailService;

        public IPage<MaterialMgt> page (MaterialMgtDto materialMgtDto) {
                QueryWrapper<MaterialMgt> build = buildWrapper(materialMgtDto);

                String supplierCode = materialMgtDto.getSupplierCode();
                if (!StringUtils.isEmpty(supplierCode)) { //供应商优先查询自己下面的物料
                     QueryWrapper<MaterialDetail> q = new QueryWrapper<>();
                     q.eq("foreign_code",supplierCode);
                     List<MaterialDetail> mdList = materialDetailService.list(q);
                     if (mdList !=null && mdList.size() >0) {
                             List<String> materialCodes = mdList.stream().map(MaterialDetail::getMaterialCode).distinct().collect(Collectors.toList());
                             build.in("material_code",materialCodes);
                             return materialMgtMapper.selectPage(materialMgtDto.getPage()==null ? new Page<>() : materialMgtDto.getPage(),build);
                     }
                }

                return materialMgtMapper.selectPage(materialMgtDto.getPage()==null ? new Page<>() : materialMgtDto.getPage(),build);
        }

        public List<MaterialMgtVo> pageList (MaterialMgtDto materialMgt) {

                return materialMgtMapper.pageList(materialMgt.getPage()==null ? new Page<>():materialMgt.getPage(),materialMgt);
        }

        public MaterialMgt getOneByCode (String code) {
                QueryWrapper<MaterialMgt> build = new QueryWrapper<MaterialMgt>();
                build.eq("material_code",code);
                return materialMgtMapper.selectOne(build);
        }

        private QueryWrapper<MaterialMgt> buildWrapper(MaterialMgtDto materialMgtDto) {
                QueryWrapper<MaterialMgt> build = new QueryWrapper<>();
                if (materialMgtDto.getMaterialCode() !=null && !"".equals(materialMgtDto.getMaterialCode())) {
                        build.like("material_code",materialMgtDto.getMaterialCode());
                }
                if (materialMgtDto.getMaterialName() !=null && !"".equals(materialMgtDto.getMaterialName())) {
                        build.like("material_name",materialMgtDto.getMaterialName());
                }
                if (materialMgtDto.getMaterialModel() !=null && !"".equals(materialMgtDto.getMaterialModel())) {
                        build.like("material_model",materialMgtDto.getMaterialModel());
                }
                if (materialMgtDto.getType() !=null && !"".equals(materialMgtDto.getType())) {
                        build.eq("type",materialMgtDto.getType());
                }
                if (materialMgtDto.getCodes() !=null && materialMgtDto.getCodes().size() >0) {
                        build.in("type",materialMgtDto.getCodes());
                }
                return build;
        }


        public int insert (MaterialMgt c) {
                String materialCode = c.getMaterialCode();
                if (StringUtils.isEmpty(materialCode)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"物料编码不能为空");
                }
                QueryWrapper<MaterialMgt> q = new QueryWrapper<>();
                q.eq("material_code",materialCode);
                q.last("limit 1");
                MaterialMgt m = this.getOne(q);
                if (m !=null) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"物料编码已存在");
                }

                c.setCreateTime(UserHelper.getDateTime());
                return materialMgtMapper.insert(c);
        }
}
