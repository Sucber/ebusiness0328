package com.atguigu.ebusiness.manager.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.ebusiness.bean.BaseSaleAttr;
import com.atguigu.ebusiness.bean.SpuInfo;
import com.atguigu.ebusiness.service.SPUService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SPUController {

    @Reference
    private SPUService spuService;

    @RequestMapping("saveSpu")
    public String saveSpu(SpuInfo spuInfo){
        try {
            spuService.saveSpu(spuInfo);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }

    }

    @RequestMapping("baseSaleAttrList")
    public List<BaseSaleAttr> baseSaleAttrList(){

        List<BaseSaleAttr> baseSaleAttrs = spuService.baseSaleAttrList();
        return baseSaleAttrs;

    }

    @RequestMapping("getSpuList")
    public List<SpuInfo> getSpuList(String catalog3Id){
        return spuService.getSpuList(catalog3Id);
    }
}
