package com.atguigu.ebusiness.manager.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.ebusiness.bean.SkuInfo;
import com.atguigu.ebusiness.manager.service.SKUService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@RestController
public class SKUController {

    @Reference
    private SKUService skuService;

    @RequestMapping("getSkuListBySpuId")
    public List<SkuInfo> getSkuListBySpuId(@RequestParam("spuId") String spuId){

        return skuService.getSkuListBySpuId(spuId);
    }

    @RequestMapping("saveSku")
    public String saveSku(SkuInfo skuInfo){
        try {
            skuService.saveSku(skuInfo);
            return "success";
        }catch (Exception e){
            return "default";
        }


    }

}
