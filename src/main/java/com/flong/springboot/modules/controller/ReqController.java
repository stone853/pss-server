package com.flong.springboot.modules.controller;


import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.Req;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.dto.ReqDto;
import com.flong.springboot.modules.entity.vo.ReqVo;
import com.flong.springboot.modules.service.ReqService;
import com.flong.springboot.modules.service.OptLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:需求单控制层
 */
@Api(tags = "需求单")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/req")
public class ReqController {

    @Autowired
    private ReqService reqService;

    @Autowired
    private HttpServletRequest request;


    @Autowired
    private OptLogService optLogService;
    /**
     * 添加
     */
    @ApiOperation("增加需求单")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "Req",dataTypeClass = Req.class , value ="")})
    @PostMapping("/v1/add")
    public Req add(@RequestHeader("token") String token,@Validated @RequestBody Req t) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "需求单","需求单-新增");

        List<FileBean> fileBeanList = t.getFileBeanList();
        t.setFileC(JSONArray.toJSONString(fileBeanList));

        t.setOptUser(UserHelper.getUserId(token));
        return reqService.insert(t);
    }

    /**
     * 修改
     * @param Req
     */
    @ApiOperation("修改需求单")
    @PutMapping("/updateById")
    public void updateOrInsert(@RequestHeader("token") String token,@Validated @RequestBody Req Req) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "需求单","需求单-修改");

        List<FileBean> fileBeanList = Req.getFileBeanList();
        Req.setFileC(JSONArray.toJSONString(fileBeanList));

        reqService.insert(Req);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestHeader("token") String token,@RequestBody List<String> ids) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "需求单","需求单-删除");

        reqService.removeByIds(ids);
    }

    /**
     * 查询需求单详情
     *
     * @param id
     */
    @ApiOperation("查询需求单")
    @GetMapping("/getOne")
    public ReqVo getOne(@RequestHeader("token") String token, @RequestParam("id") int id) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "需求单","需求单-查详情");
        return reqService.getOneById(id);
    }

    /**
     * 查询合同详情
     *
     * @param reqCode
     */
    @ApiOperation("查询需求单")
    @GetMapping("/getOneByCode")
    public ReqVo getOneByCode(@RequestHeader("token") String token, @RequestParam("reqCode") String reqCode) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "需求单","需求单-查详情");
        return reqService.getOneByCode(UserHelper.getUserId(token),reqCode);
    }


    @ApiOperation("分页查询")
    @PostMapping("/page")
    public IPage<ReqVo> page(@RequestHeader("token") String token, @RequestBody ReqDto reqDto) {
        optLogService.insertOptLog(UserHelper.getUserId(token),UserHelper.getRealRequestIp(request),
                "需求单","需求单-首页");

        return reqService.pageList(UserHelper.getUserId(token),reqDto);
    }



}
