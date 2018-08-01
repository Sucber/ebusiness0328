package com.atguigu.ebusiness.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.ebusiness.bean.BaseCatalog1;
import com.atguigu.ebusiness.bean.BaseCatalog2;
import com.atguigu.ebusiness.bean.BaseCatalog3;
import com.atguigu.ebusiness.manager.mapper.Catalog1Mapper;
import com.atguigu.ebusiness.manager.mapper.Catalog2Mapper;
import com.atguigu.ebusiness.manager.mapper.Catalog3Mapper;
import com.atguigu.ebusiness.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    private Catalog1Mapper catalog1Mapper;

    @Autowired
    private Catalog2Mapper catalog2Mapper;

    @Autowired
    private Catalog3Mapper catalog3Mapper;

    @Override
    public List<BaseCatalog1> getBaseCatalog1List() {

        return catalog1Mapper.selectAll();
    }

    @Override
    public List<BaseCatalog2> getBaseCatalog2ListByCataLog1Id(String catalog1Id) {
        Example example = new Example(BaseCatalog2.class);
        example.createCriteria().andEqualTo("catalog1Id",catalog1Id);
        return catalog2Mapper.selectByExample(example);
    }

    @Override
    public List<BaseCatalog3> getBaseCatalog3ListByCataLog2Id(String catalog2Id) {
        Example example = new Example(BaseCatalog3.class);
        example.createCriteria().andEqualTo("catalog2Id",catalog2Id);
        return catalog3Mapper.selectByExample(example);
    }


}
