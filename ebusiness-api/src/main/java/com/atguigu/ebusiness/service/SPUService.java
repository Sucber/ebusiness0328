package com.atguigu.ebusiness.service;

import com.atguigu.ebusiness.bean.BaseSaleAttr;
import com.atguigu.ebusiness.bean.SpuInfo;

import java.util.List;

public interface SPUService {
    List<SpuInfo> getSpuList(String catalog3Id);

    List<BaseSaleAttr> baseSaleAttrList();

    void saveSpu(SpuInfo spuInfo);
}
