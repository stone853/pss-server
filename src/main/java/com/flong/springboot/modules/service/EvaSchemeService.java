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
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.EvaScheme;
import com.flong.springboot.modules.entity.dto.EvaSchemeDto;
import com.flong.springboot.modules.entity.vo.EvaSchemeVo;
import com.flong.springboot.modules.mapper.EvaSchemeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EvaSchemeService extends ServiceImpl<EvaSchemeMapper, EvaScheme> {
        @Autowired
        EvaSchemeMapper evaSchemeMapper;

        @Autowired
        EvaIndexService evaIndexService;



        public IPage<EvaSchemeVo> list (EvaSchemeDto evaSchemeDto) {
                return evaSchemeMapper.pageList(evaSchemeDto.getPage()==null ? new Page<>():evaSchemeDto.getPage(),evaSchemeDto);
        }

        public EvaScheme getOneByCode (String code) {
                QueryWrapper<EvaScheme> build = new QueryWrapper();
                build.eq("scheme_name",code);
                return evaSchemeMapper.selectOne(build);
        }

        private QueryWrapper<EvaScheme> buildWrapper(EvaSchemeDto evaSchemeDto) {
                QueryWrapper<EvaScheme> build = new QueryWrapper();
                if (evaSchemeDto.getSchemeName() !=null && !"".equals(evaSchemeDto.getSchemeName())) {
                        build.eq("scheme_name",evaSchemeDto.getSchemeName());
                }
                build.orderByDesc("upd_time");
                return build;
        }

        /**
         *??????
         * @param c
         */
        @Transactional
        public int insert (EvaScheme c) {
                String foreignCode = GeneratorKeyUtil.getSchemeNextCode();
                //??????
                int r = 0;
                try {
                        c.setUpdTime(UserHelper.getDateTime());
                        c.setCode(foreignCode);
                        r = evaSchemeMapper.insert(c);;
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"??????????????????");
                }


                evaIndexService.batchInsert(foreignCode,c.getEvaIndexList());
                return r;
        }

        public boolean openScheme (EvaScheme evaScheme) {
                String enableFlag = evaScheme.getEnableFlag();
                Integer id = evaScheme.getId();
                if (id == null || id == 0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"id????????????");
                }
                if (StringUtils.isEmpty(enableFlag)) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"enable_flag??????");
                }
                if ("1".equals(enableFlag)) {
                        QueryWrapper<EvaScheme> q = new QueryWrapper();
                        q.eq("enable_flag","1");
                        q.ne("id",id);
                        EvaScheme e = evaSchemeMapper.selectOne(q);
                        if (e !=null && e.getEnableFlag().equals("1")) {
                                throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"????????????????????????");
                        }

                }

                evaSchemeMapper.updateById(evaScheme);

                return true;
        }


        /**
         *??????
         * @param c
         */
        public void update (EvaScheme c) {
                int keyId = c.getId();
                String foreignCode = c.getCode();
                if (keyId ==0) {
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"id????????????");
                }
                //???????????????
                try {
                        c.setUpdTime(UserHelper.getDateTime());
                        evaSchemeMapper.updateById(c);
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"??????????????????");
                }

                evaIndexService.updateOrInsertOrDelete(foreignCode,c.getEvaIndexList());
        }




}
