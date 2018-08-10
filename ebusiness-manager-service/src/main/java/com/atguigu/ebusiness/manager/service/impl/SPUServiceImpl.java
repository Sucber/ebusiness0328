package com.atguigu.ebusiness.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.ebusiness.bean.*;
import com.atguigu.ebusiness.manager.mapper.*;
import com.atguigu.ebusiness.service.SPUService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

@Service
public class SPUServiceImpl implements SPUService {

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

    private Example spuInfoExample;

    private Example spuSaleAttrExample;

    private Example spuImageExample;

    private Example spuSaleAttrValueExample;

    {
        spuInfoExample = new Example(SpuInfo.class);
        spuSaleAttrExample = new Example(SpuSaleAttr.class);
        spuImageExample = new Example(SpuImage.class);
        spuSaleAttrValueExample = new Example(SpuSaleAttrValue.class);
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
        if (!spuSaleAttrList.isEmpty()) {
            for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {
                spuSaleAttr.setSpuId(id);
                spuSaleAttrMapper.insert(spuSaleAttr);

                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                if(spuSaleAttrValueList != null && !(spuSaleAttrValueList.isEmpty())) {
                    // 保存spu的销售属性值
                    for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                        spuSaleAttrValue.setSpuId(id);
                        spuSaleAttrValueMapper.insert(spuSaleAttrValue);
                    }
                }
            }
        }

        // 保存图片信息
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();
        if (spuImageList != null && !spuImageList.isEmpty()) {
            for (SpuImage spuImage : spuImageList) {
                spuImage.setSpuId(id);
                spuImageMapper.insert(spuImage);
            }
        }
    }

    public void updateSpu(SpuInfo spuInfo) {
        //更新spu基本信息
        spuInfoMapper.updateByPrimaryKey(spuInfo);
        String spuId = spuInfo.getId();
        //更新spu相关销售属性列表

        //更新spu相关图片列表
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListBySpuId(String spuId) {
        spuSaleAttrExample.clear();
        spuSaleAttrExample.createCriteria().andEqualTo("spuId",spuId);
        List<SpuSaleAttr> spuSaleAttrs = spuSaleAttrMapper.selectByExample(spuSaleAttrExample);
        for (SpuSaleAttr spuSaleAttr : spuSaleAttrs) {
            spuSaleAttrValueExample.clear();
            Example.Criteria criteria = spuSaleAttrValueExample.createCriteria();
            criteria.andEqualTo("saleAttrId",spuSaleAttr.getSaleAttrId());
            criteria.andEqualTo("spuId",spuId);
            List<SpuSaleAttrValue> spuSaleAttrValues = spuSaleAttrValueMapper.selectByExample(spuSaleAttrValueExample);
            spuSaleAttr.setSpuSaleAttrValueList(spuSaleAttrValues);
        }
        return spuSaleAttrs;
    }

    /**
     * 通过spuid获取spu图片列表
     * @param spuId
     * @return List<SpuImage>
     */
    @Override
    public List<SpuImage> getSpuImageListBySpuId(String spuId) {
        spuImageExample.clear();
        spuImageExample.createCriteria().andEqualTo("spuId",spuId);
        return spuImageMapper.selectByExample(spuImageExample);
    }
    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Map<String, String> stringStringHashMap) {
        return spuSaleAttrValueMapper.selectSpuSaleAttrListCheckBySku(stringStringHashMap);
    }

    @Override
    public void deleteSpuInfoById(String spuId) {
        //spu销售属性相关的example清理
        spuSaleAttrValueExample.clear();
        spuSaleAttrExample.clear();
        //spu销售属性相关的判断条件
        spuSaleAttrValueExample.createCriteria().andEqualTo("spuId",spuId);
        spuSaleAttrExample.createCriteria().andEqualTo("spuId",spuId);
        //删除spu销售属性
        spuSaleAttrMapper.deleteByExample(spuSaleAttrExample);
        spuSaleAttrValueMapper.deleteByExample(spuSaleAttrValueExample);

        spuInfoMapper.deleteByPrimaryKey(spuId);

    }
}
