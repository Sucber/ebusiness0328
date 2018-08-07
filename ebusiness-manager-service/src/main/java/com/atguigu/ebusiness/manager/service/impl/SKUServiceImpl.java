package com.atguigu.ebusiness.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.ebusiness.bean.SkuAttrValue;
import com.atguigu.ebusiness.bean.SkuImage;
import com.atguigu.ebusiness.bean.SkuInfo;
import com.atguigu.ebusiness.manager.mapper.SkuAttrValueMapper;
import com.atguigu.ebusiness.manager.mapper.SKUImageMapper;
import com.atguigu.ebusiness.manager.mapper.SKUInfoMapper;
import com.atguigu.ebusiness.manager.service.SKUService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SKUServiceImpl implements SKUService{

    @Autowired
    private SKUInfoMapper skuInfoMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    private SKUImageMapper skuImageMapper;


    private Example skuInfoExample;

    {
        skuInfoExample = new Example(SkuInfo.class);
    }

    @Override
    public List<SkuInfo> getSkuListBySpuId(String spuId) {
        //清空example中的查询条件等
        skuInfoExample.clear();
        //重新写入查询条件并返回查询结果
        skuInfoExample.createCriteria().andEqualTo("spuId",spuId);
        return skuInfoMapper.selectByExample(skuInfoExample);
    }

    @Override
    public void saveSku(SkuInfo skuInfo) {
        //保存sku基本信息
        skuInfoMapper.insertSelective(skuInfo);
        //保存sku属性值列表信息
        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
        if(skuAttrValueList != null && !skuAttrValueList.isEmpty()){
            for (SkuAttrValue skuAttrValue : skuAttrValueList) {
                skuAttrValue.setSkuId(skuInfo.getId());
                skuAttrValueMapper.insert(skuAttrValue);
            }
        }
        //保存sku图片信息
        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        if(skuImageList != null && !skuImageList.isEmpty()) {
            for (SkuImage skuImage : skuImageList) {
                skuImage.setSkuId(skuInfo.getId());
                skuImageMapper.insert(skuImage);
            }
        }
    }
}
