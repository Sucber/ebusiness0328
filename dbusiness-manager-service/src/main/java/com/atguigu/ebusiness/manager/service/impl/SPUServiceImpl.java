package com.atguigu.ebusiness.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.ebusiness.bean.*;
import com.atguigu.ebusiness.manager.mapper.*;
import com.atguigu.ebusiness.service.SPUService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SPUServiceImpl implements SPUService{

    @Autowired
    private SPUInfoMapper spuInfoMapper;

    @Autowired
    SpuImageMapper spuImageMapper;

    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private SaleAttrMapper saleAttrMapper;

    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;

    private  Example spuInfoExample;

    {
        spuInfoExample = new Example(SpuInfo.class);
    }

    @Override
    public List<SpuInfo> getSpuList(String catalog3Id) {
        return spuInfoMapper.selectAll();
    }

    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {

        return saleAttrMapper.selectAll();
    }

    @Override
    public void saveSpu(SpuInfo spuInfo) {
        //保存spu基本信息
        spuInfoMapper.insertSelective(spuInfo);
        String id = spuInfo.getId();



        //保存spu销售属性
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();
        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
            spuSaleAttr.setSaleAttrId(id);
            spuSaleAttrMapper.insert(spuSaleAttr);
            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
            // 保存spu的销售属性值
            for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                spuSaleAttrValue.setSpuId(id);
                spuSaleAttrValueMapper.insert(spuSaleAttrValue);
            }
        }

        // 保存图片信息
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        for (SpuImage spuImage : spuImageList) {
            spuImage.setSpuId(id);
            spuImageMapper.insert(spuImage);
        }
    }

}
