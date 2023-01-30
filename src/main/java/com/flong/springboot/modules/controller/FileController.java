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
@Api(tags = "文件上传下载")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/file")
public class FileController {



    @Autowired
    PssConfig pssConfig;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;



    @ApiOperation("上传文件")
    @PostMapping("/v1/uploadFiles")
    public FileBean uploadFiles(@RequestHeader("token") String token, @RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            String fileTyle=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());

            String id = GeneratorKeyUtil.getFileNextId();

            String url = pssConfig.getCustomerFileUrl()+ UserHelper.getDate()+"/" +id+"/";

            FileUtil fu = new FileUtil();
            fu.uploadFile(file.getBytes(),url, fileName);

            return new FileBean().setId(id)
                    .setName(fileName)
                    .setType(fileTyle)
                    .setUrl(url+fileName);

        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"上传文件失败");
        }
    }


    @ApiOperation("下载文件")
    @GetMapping("/v1/downloadFiles")
    public String downloadFiles(@RequestHeader("token") String token, @RequestParam("downloadPath") String downloadPath) {
        FileUtil fu = new FileUtil();
        return fu.downloadFile(request,response,downloadPath);

    }

    @ApiOperation("预览文件")
    @GetMapping("/v1/showImg")
    public String showFile( @RequestParam("filePath") String filePath) {
        FileUtil fu = new FileUtil();
        return fu.showImg(request,response,filePath);
    }



}
