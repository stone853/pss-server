package com.flong.springboot.modules.controller;


import com.flong.springboot.core.constant.RequestCommonPathConstant;
import com.flong.springboot.core.util.ThreadTest;
import com.flong.springboot.modules.entity.dto.MaterialStockDto;
import com.flong.springboot.modules.entity.vo.MaterialStockDetailVo;
import com.flong.springboot.modules.entity.vo.MaterialStockVo;
import com.flong.springboot.modules.service.MaterialStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author:jinshi
 * @Date:2022-12-25
 * @Description:物料库存控制层
 */
@Api(tags = "物料库存")
@RestController
@RequestMapping(RequestCommonPathConstant.REQUEST_PROJECT_PATH+"/material/stock")
public class MaterialStockController {

    @Autowired
    private MaterialStockService materialStockService;

    @Autowired
    private HttpServletRequest request;


    @ApiOperation("库存明细查询（不分页）")
    @PostMapping("/v1/findAll")
    public List<MaterialStockVo> findAll (@RequestHeader("token") String token, @RequestBody MaterialStockDto materialStockDto) {
        return materialStockService.findAll(materialStockDto);
    }

    @ApiOperation("查询库存详情-入库-出库（不分页）")
    @PostMapping("/v1/findStockDetail")
    public List<MaterialStockDetailVo> findStockDetail (@RequestHeader("token") String token, @RequestBody MaterialStockDto materialStockDto) {
        return materialStockService.findStockDetail(materialStockDto);
    }


//    /**
//     * 修改数量
//     */
//    @ApiOperation("修改数量")
//    @PutMapping("/subOutOrder")
//    public void subOutOrder(@RequestHeader("token") String token) {
//        for (int i = 0 ; i < 50;i ++) {
//            ThreadTest t = new ThreadTest();
//            t.setMaterialStockService(materialStockService);
//            t.start();
//        }
//
//    }

}
