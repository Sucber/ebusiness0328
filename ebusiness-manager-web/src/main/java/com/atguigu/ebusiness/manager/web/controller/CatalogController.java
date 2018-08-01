package com.atguigu.ebusiness.manager.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.ebusiness.bean.BaseCatalog1;
import com.atguigu.ebusiness.bean.BaseCatalog2;
import com.atguigu.ebusiness.bean.BaseCatalog3;
import com.atguigu.ebusiness.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CatalogController {

    @Reference
    private CatalogService catalogService;

    @RequestMapping("getBaseCatalog1List")
    public List<BaseCatalog1> getBaseCatalog1List(){


        return catalogService.getBaseCatalog1List();
    }

    @RequestMapping("getBaseCatalog2ListByCataLog1Id")
    public List<BaseCatalog2> getBaseCatalog2ListByCataLog1Id(@RequestParam("id") String id){

        return catalogService.getBaseCatalog2ListByCataLog1Id(id);
    }

    @RequestMapping("getBaseCatalog3ListByCataLog2Id")
    public List<BaseCatalog3> getBaseCatalog3ListByCataLog2Id(@RequestParam("id") String id){

        return catalogService.getBaseCatalog3ListByCataLog2Id(id);
    }

}
