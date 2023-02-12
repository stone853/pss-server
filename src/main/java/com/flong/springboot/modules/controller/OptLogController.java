package com.flong.springboot.modules.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.dto.OptLogDto;
import com.flong.springboot.modules.entity.vo.OptLogVo;
import com.flong.springboot.modules.service.OptLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:操作日志控制层
 */
@Api(tags = "操作日志")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/opt/log")
public class OptLogController {

    @Autowired
    private OptLogService optLogService;


    @PostMapping("/page")
    public IPage<OptLogVo> page(@RequestHeader("token") String token, @RequestBody OptLogDto optLogDto) {

        return optLogService.pageList(optLogDto);
    }




}
