package com.atguigu.ebusiness.manager.mapper;

import com.atguigu.ebusiness.bean.SkuInfo;
import com.atguigu.ebusiness.bean.SkuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SkuSaleAttrValueMapper extends Mapper<SkuSaleAttrValue>{
    List<SkuInfo> selectSkuSaleAttrValueListBySpu(String spuId);
}
