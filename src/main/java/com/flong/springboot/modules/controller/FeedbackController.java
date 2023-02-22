package com.flong.springboot.modules.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.Feedback;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.dto.FeedbackDto;
import com.flong.springboot.modules.entity.vo.FeedbackVo;
import com.flong.springboot.modules.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:意见反馈控制层
 */
@Api(tags = "意见反馈")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;


    /**
     * 添加
     */
    @ApiOperation("增加意见反馈信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "Feedback",dataTypeClass = Feedback.class , value ="")})
    @PostMapping("/v1/add")
    public Feedback add(@RequestHeader("token") String token,@Validated @RequestBody Feedback t) {
        String userId = UserHelper.getUserId(token);
        t.setOptUser(userId);
        return feedbackService.insert(userId,t);
    }

    /**
     * 修改
     * @param Feedback
     */
    @PutMapping("/v1/updateById")
    public void updateById(@RequestHeader("token") String token,@Validated @RequestBody Feedback Feedback) {
        List<FileBean> fileBeanList = Feedback.getFileBeanList();
        Feedback.setFileC(JSONArray.toJSONString(fileBeanList));

        feedbackService.updateById(Feedback);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/v1/deleteByIds")
    public void deleteByIds(@RequestHeader("token") String token,@RequestBody List<String> ids) {
        feedbackService.removeByIds(ids);
    }

    /**
     * 查询合同详情
     *
     * @param id
     */
    @GetMapping("/v1/getOne")
    public FeedbackVo getOne(@RequestHeader("token") String token, @RequestParam("id") int id) {
        return feedbackService.getOneById(id);
    }



//    /**
//     * 意见反馈分页，参数有多个使用下标索引进行处理.如果有两个参数(如意见反馈名和地址)：conditionList[0].fieldName=FeedbackName、 conditionList[0].fieldName=address
//     * 未转码请求分页地址: http://localhost:7011/Feedback/page?conditionList[0].fieldName=FeedbackName&conditionList[0].operation=LIKE&conditionList[0].value=周
//     * 已转码请求分页地址: http://localhost:7011/Feedback/page?conditionList[0].fieldName=FeedbackName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
//     * @return
//     */
    @PostMapping("/v1/page")
    public IPage<FeedbackVo> page(@RequestHeader("token") String token, @RequestBody FeedbackDto feedbackDto) {
        feedbackDto.setUserId(UserHelper.getUserId(token));
        return feedbackService.pageList(feedbackDto);
    }



}
