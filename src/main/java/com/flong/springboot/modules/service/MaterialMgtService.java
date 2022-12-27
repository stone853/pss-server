package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.modules.entity.Customer;
import com.flong.springboot.modules.entity.MaterialMgt;
import com.flong.springboot.modules.entity.dto.MaterialMgtDto;
import com.flong.springboot.modules.entity.vo.MaterialMgtVo;
import com.flong.springboot.modules.mapper.MaterialMgtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialMgtService extends ServiceImpl<MaterialMgtMapper, MaterialMgt> {
        @Autowired
        MaterialMgtMapper materialMgtMapper;

        public IPage<MaterialMgt> page (MaterialMgtDto materialMgtDto) {
                QueryWrapper<MaterialMgt> build = buildWrapper(materialMgtDto);
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
                        build.eq("materail_code",materialMgtDto.getMaterialCode());
                }
                if (materialMgtDto.getMaterialName() !=null && !"".equals(materialMgtDto.getMaterialName())) {
                        build.like("material_name",materialMgtDto.getMaterialName());
                }
                if (materialMgtDto.getType() !=null && !"".equals(materialMgtDto.getType())) {
                        build.eq("type",materialMgtDto.getType());
                }
                return build;
        }


        public int insert (MaterialMgt c) {
                return materialMgtMapper.insert(c);
        }

}
