package com.flong.springboot.modules.service;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.modules.entity.Feedback;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.dto.FeedbackDto;
import com.flong.springboot.modules.entity.dto.UserDto;
import com.flong.springboot.modules.entity.vo.FeedbackVo;
import com.flong.springboot.modules.mapper.FeedbackMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FeedbackService extends ServiceImpl<FeedbackMapper, Feedback> {
        @Autowired
        FeedbackMapper feedBackMapper;

        @Autowired
        UserService userService;

//        public FeedBackVo getOneByCode (String code) {
//                FeedBackVo c = FeedBackMapper.getOneByCode(code);
//                convertJsonArray(c);
//                return c;
//        }


        public Feedback insert (String userId,Feedback feedback) {
                List<FileBean> fileBeanList = feedback.getFileBeanList();

                feedback.setFileC(JSONArray.toJSONString(fileBeanList));

                feedback.setOptTime(UserHelper.getDateTime());
                feedback.setCompany(userService.getUserDeptCode(new UserDto().setUserId(userId)));
                this.save(feedback);
                return feedback;
        }



        public IPage<FeedbackVo> pageList (FeedbackDto feedBackDto) {
                IPage<FeedbackVo> pageList = feedBackMapper.pageList(feedBackDto.getPage()==null ? new Page<>():feedBackDto.getPage(),feedBackDto);
                List<FeedbackVo> feedBackList = pageList.getRecords();
                feedBackList.stream().forEach((p) -> {
                        convertJsonArray(p);

                        }
                );
                return  pageList;
        }


        /**
         * 根据ID查，详情
         * @param id
         * @return
         */
        public FeedbackVo getOneById (int id) {
                FeedbackVo feedbackVo =  feedBackMapper.getOneById(id);
                convertJsonArray(feedbackVo);
                return feedbackVo;
        }



        private FeedbackVo convertJsonArray (FeedbackVo c) {
                if (c !=null) {
                        c.setJsonArray(JSONArray.parseArray( c.getFileC()));
                }
                return c;
        }



}
