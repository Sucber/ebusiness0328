package com.atguigu.ebusiness.item.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.ebusiness.bean.SkuInfo;
import com.atguigu.ebusiness.bean.SkuSaleAttrValue;
import com.atguigu.ebusiness.bean.SpuSaleAttr;
import com.atguigu.ebusiness.manager.service.SKUService;
import com.atguigu.ebusiness.service.SPUService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Reference
    private SKUService skuService;

    @Reference
    private SPUService spuService;

    @RequestMapping("{skuId}.html")
    public String item(@PathVariable("skuId")String skuId, ModelMap map){
        //获取sku基本信息
        SkuInfo skuInfo = skuService.getSkuBySkuId(skuId);
        map.put("skuInfo",skuInfo);
        String spuId = skuInfo.getSpuId();

        List<SkuInfo> infos = skuService.getSkuSaleAttrValueListBySpu(spuId);
        HashMap<String,String> saleAttrMap = new HashMap<>();
        //总体思路：将sku销售属性值作为key，重复的skuid作为value（Map中key唯一，value不唯一）
        for (SkuInfo info : infos) {
            String v = info.getId();
            //获取sku销售属性列表
            List<SkuSaleAttrValue> skuSaleAttrValueList = info.getSkuSaleAttrValueList();
            String k = "";
            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                k = k + "|" + skuSaleAttrValue.getSaleAttrValueId();
            }
            saleAttrMap.put(k,v);
        }
        String skuJson = JSON.toJSONString(saleAttrMap);
        map.put("skuJson",skuJson);
        //获取销售列表，并将数据封装到ModelMap传输到页面上
        Map<String,String> idsMap = new HashMap<>();
        idsMap.put("skuId",skuId);
        idsMap.put("spuId",spuId);
        List<SpuSaleAttr> spuSaleAttrList= spuService.getSpuSaleAttrListCheckBySku(idsMap);
        map.put("spuSaleAttrListCheckBySku",spuSaleAttrList);
        return "item";
    }

}
