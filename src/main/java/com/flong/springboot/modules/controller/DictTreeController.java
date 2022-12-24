package com.flong.springboot.modules.controller;



import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.DictTree;
import com.flong.springboot.modules.service.DictTreeService;
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
 * @Description:字典树控制层
 */
@Api(tags = "字典树信息")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/dictTree")
public class DictTreeController {

    @Autowired
    private DictTreeService dictTreeService;

    /**
     * 添加
     */
    @ApiOperation("增加字典树信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "dictTree",dataTypeClass = DictTree.class , value ="")})
    @PostMapping("/v1/add")
    public int add(@RequestHeader("token") String token,@RequestBody DictTree t) {
        return dictTreeService.insert(t);
    }

    /**
     * 修改
     * @param dictTree
     */
    @PutMapping("/updateById")
    public void updateById(@RequestBody DictTree dictTree) {
        dictTreeService.updateById(dictTree);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        dictTreeService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     * @param dictTreeId
     */
    @GetMapping("/getOne/{dictTreeId}")
    public DictTree getOne(@RequestHeader("token") String token,String dictTreeId) {
        return dictTreeService.getById(dictTreeId);
    }


    @ApiOperation("查询所有字典树信息")
    @GetMapping("/findAllDictTree")
    public List<DictTree> findAllDictTree() {
        return dictTreeService.findAll();
    }



    @ApiOperation("查询字典树")
    @GetMapping("/finddictTreeByRoleCode")
    public List<DictTree> querydictTreesByRoleCode(@RequestHeader("token") String token,String type) {
        return dictTreeService.findByType(type);
    }




}
