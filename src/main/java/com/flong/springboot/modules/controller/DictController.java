package com.flong.springboot.modules.controller;



import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.Dict;
import com.flong.springboot.modules.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author:jinshi
 * @Date:2020-08-16
 * @Description:字典控制层
 */
@Api(tags = "字典信息")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 添加
     */
    @ApiOperation("增加字典信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "dict",dataTypeClass = Dict.class , value ="")})
    @PostMapping("/v1/add")
    public boolean add(@RequestHeader("token") String token,@RequestBody Dict t) {
        return dictService.save(t);
    }

    /**
     * 修改
     * @param dict
     */
    @PutMapping("/updateById")
    public void updateById(@RequestBody Dict dict) {
        dictService.updateById(dict);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        dictService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     * @param type
     */
    @GetMapping("/getDictsByType/{type}")
    public List<Dict> getDictsByType(@RequestHeader("token") String token,@PathVariable("type") String type) {
        return dictService.getDictsByType(type);
    }

    /**
     * 字典分页，参数有多个使用下标索引进行处理.如果有两个参数(如字典名和地址)：conditionList[0].fieldName=dictName、 conditionList[0].fieldName=address
     * 未转码请求分页地址: http://localhost:7011/dict/page?conditionList[0].fieldName=dictName&conditionList[0].operation=LIKE&conditionList[0].value=周
     * 已转码请求分页地址: http://localhost:7011/dict/page?conditionList[0].fieldName=dictName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
     * @return
     */
    @PostMapping("/findAll")
    public List<Dict> findAll(@RequestHeader("token") String token) {
        return dictService.list();
    }
}
