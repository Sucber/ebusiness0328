package com.atguigu.ebusiness.service;

import com.atguigu.ebusiness.bean.BaseCatalog1;
import com.atguigu.ebusiness.bean.BaseCatalog2;
import com.atguigu.ebusiness.bean.BaseCatalog3;

import java.util.List;

public interface CatalogService {

    List<BaseCatalog1> getBaseCatalog1List();

    List<BaseCatalog2> getBaseCatalog2ListByCataLog1Id(String id);

    List<BaseCatalog3> getBaseCatalog3ListByCataLog2Id(String id);
}
