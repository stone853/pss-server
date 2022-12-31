package com.flong.springboot.modules.controller;



import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.utils.FileUtil;
import com.flong.springboot.base.utils.GeneratorKeyUtil;
import com.flong.springboot.core.config.PssConfig;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.core.util.StringUtils;
import com.flong.springboot.modules.entity.Customer;
import com.flong.springboot.modules.entity.FileBean;
import com.flong.springboot.modules.entity.dto.CustomerDto;
import com.flong.springboot.modules.entity.vo.CustomerVo;
import com.flong.springboot.modules.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:客户控制层
 */
@Api(tags = "客户信息")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    PssConfig pssConfig;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    /**
     * 添加
     */
    @ApiOperation("增加客户信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "Customer",dataTypeClass = Customer.class , value ="")})
    @PostMapping("/v1/add")
    public int add(@RequestHeader("token") String token,@RequestBody Customer t) {
        List<FileBean> fileBeanList = t.getFileBeanList();
        if (fileBeanList !=null && fileBeanList.size() > 0) {
            t.setFilesC(JSONArray.toJSONString(fileBeanList));
        }
        return customerService.insert(t);
    }

    /**
     * 修改
     * @param customer
     */
    @PutMapping("/updateById")
    public void updateById(@RequestBody Customer customer) {
        List<FileBean> fileBeanList = customer.getFileBeanList();
        if (fileBeanList !=null && fileBeanList.size() > 0) {
            customer.setFilesC(JSONArray.toJSONString(fileBeanList));
        }
        customerService.updateById(customer);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        customerService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     * @param custCode
     */
    @GetMapping("/getOne/{custCode}")
    public Customer getOne(@RequestHeader("token") String token,@PathVariable("custCode") String custCode) {
        return customerService.getOneByCode(custCode);
    }


    @PostMapping("/page")
    public IPage<CustomerVo> page(@RequestHeader("token") String token, @RequestBody CustomerDto CustomerDto) {
        return customerService.pageList(CustomerDto);
    }

    @ApiOperation("上传文件")
    @PostMapping("/v1/uploadFiles")
    public FileBean uploadFiles(@RequestHeader("token") String token, @RequestParam("file") MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();

            String fileTyle=fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());

            String url = pssConfig.getCustomerFileUrl();

            String id = GeneratorKeyUtil.getFileNextId();

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



}
