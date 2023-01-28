package com.flong.springboot.core.util;

import com.flong.springboot.modules.service.MaterialStockService;



public class ThreadTest extends Thread{


    MaterialStockService materialStockService;


    public void setMaterialStockService(MaterialStockService materialStockService) {
        this.materialStockService = materialStockService;
    }

    @Override
    public void run(){
        materialStockService.subOutOrder("CL00001",1);
    }
}
