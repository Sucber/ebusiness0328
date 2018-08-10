package com.atguigu.ebusiness.manager.service;

import com.atguigu.ebusiness.bean.SkuInfo;

import java.util.List;

public interface SKUService {

    List<SkuInfo> getSkuListBySpuId(String spuId);

    void saveSku(SkuInfo skuInfo);

    SkuInfo getSkuBySkuId(String skuId);

    List<SkuInfo> getSkuSaleAttrValueListBySpu(String spuId);
}
