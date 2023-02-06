package com.flong.springboot.modules.controller;



import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.modules.entity.MaterialMgt;
import com.flong.springboot.modules.entity.dto.MaterialMgtDto;
import com.flong.springboot.modules.entity.vo.MaterialMgtVo;
import com.flong.springboot.modules.service.MaterialMgtService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:物料管理控制层
 */
@Api(tags = "物料管理")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/materailmgt")
public class MaterialMgtController {

    @Autowired
    private MaterialMgtService materialMgtService;

    /**
     * 添加
     */
    @ApiOperation("增加物料管理信息")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "MaterialMgt",dataTypeClass = MaterialMgt.class , value ="")})
    @PostMapping("/v1/add")
    public int add(@RequestHeader("token") String token,@RequestBody MaterialMgt t) {
        t.setCreateUser(UserHelper.getUserId(token));
        return materialMgtService.insert(t);
    }

    /**
     * 修改
     * @param materialMgt
     */
    @PutMapping("/updateById")
    public void updateById(@RequestBody MaterialMgt materialMgt) {
        materialMgtService.updateById(materialMgt);
    }
    /**
     * 删除通过多个主键Id进行删除
     * @param ids
     */
    @DeleteMapping("/deleteByIds")
    public void deleteByIds(@RequestBody List<String> ids) {
        materialMgtService.removeByIds(ids);
    }

    /**
     * 通过指定Id进行查询
     *
     * @param materailCode
     */
    @GetMapping("/getOne/{materailCode}")
    public MaterialMgt getOne(@RequestHeader("token") String token,@PathVariable("materailCode") String materailCode) {
        return materialMgtService.getOneByCode(materailCode);
    }

    /**
     * 物料管理分页，参数有多个使用下标索引进行处理.如果有两个参数(如物料管理名和地址)：conditionList[0].fieldName=MaterialMgtName、 conditionList[0].fieldName=address
     * 未转码请求分页地址: http://localhost:7011/MaterialMgt/page?conditionList[0].fieldName=MaterialMgtName&conditionList[0].operation=LIKE&conditionList[0].value=周
     * 已转码请求分页地址: http://localhost:7011/MaterialMgt/page?conditionList[0].fieldName=MaterialMgtName&conditionList[0].operation=LIKE&conditionList[0].value=%E5%91%A8
     * @return
     */
    @PostMapping("/page")
    public IPage<MaterialMgt> page(@RequestHeader("token") String token, @RequestBody MaterialMgtDto materialMgtDto) {
        return materialMgtService.page(materialMgtDto);
    }

    @PostMapping("/pageList")
    public List<MaterialMgtVo> pageList(@RequestHeader("token") String token, @RequestBody MaterialMgtDto materialMgt) {
        return materialMgtService.pageList(materialMgt);
    }


}
