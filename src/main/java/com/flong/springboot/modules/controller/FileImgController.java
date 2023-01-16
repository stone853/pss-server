package com.flong.springboot.modules.controller;

import com.flong.springboot.base.utils.FileUtil;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.config.PssConfig;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.FileBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:文件上传下载控制层
 */
@Api(tags = "图片预览")
@RestController
@RequestMapping("/img")
public class FileImgController {



    @Autowired
    PssConfig pssConfig;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;





    @ApiOperation("预览文件")
    @GetMapping("/v1/showImg")
    public String showFile( @RequestParam("filePath") String filePath) {
        FileUtil fu = new FileUtil();
        return fu.showImg(request,response,filePath);
    }



}
