package com.atguigu.ebusiness.manager.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.ebusiness.bean.BaseSaleAttr;
import com.atguigu.ebusiness.bean.SpuImage;
import com.atguigu.ebusiness.bean.SpuInfo;
import com.atguigu.ebusiness.bean.SpuSaleAttr;
import com.atguigu.ebusiness.manager.web.utils.WebUpLoadUtil;
import com.atguigu.ebusiness.service.SPUService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class SPUController {

    @Reference
    private SPUService spuService;

    @RequestMapping("/fileUpload")
    public String fileUpload(@RequestParam("file")MultipartFile file){
        String imgUrl = WebUpLoadUtil.uploadImage(file);
        return imgUrl;
    }

    @RequestMapping("deleteSpuInfoBySpuId")
    public String deleteSpuInfoBySpuId(@RequestParam("spuId") String spuId){

        try {
            spuService.deleteSpuInfoById(spuId);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "default";
        }
    }

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

    @RequestMapping("getSpuSaleAttrListBySpuId")
    public List<SpuSaleAttr> getSpuSaleAttrListBySpuId(@RequestParam("spuId") String spuId) {
        List<SpuSaleAttr> result = spuService.getSpuSaleAttrListBySpuId(spuId);
        return result;
    }

    @RequestMapping("getSpuImageListBySpuId")
    public List<SpuImage> getSpuImageListBySpuId(@RequestParam("spuId") String spuId){

        return spuService.getSpuImageListBySpuId(spuId);
    }

}
