package com.atguigu.ebusiness.service;

import com.atguigu.ebusiness.bean.BaseSaleAttr;
import com.atguigu.ebusiness.bean.SpuImage;
import com.atguigu.ebusiness.bean.SpuInfo;
import com.atguigu.ebusiness.bean.SpuSaleAttr;

import java.util.List;

public interface SPUService {
    List<SpuInfo> getSpuList(String catalog3Id);

    List<BaseSaleAttr> baseSaleAttrList();

    void saveSpu(SpuInfo spuInfo);

    List<SpuSaleAttr> getSpuSaleAttrListBySpuId(String spuId);

    List<SpuImage> getSpuImageListBySpuId(String spuId);
}
