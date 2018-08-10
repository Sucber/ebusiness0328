package com.atguigu.ebusiness.manager.mapper;

import com.atguigu.ebusiness.bean.SpuSaleAttr;
import com.atguigu.ebusiness.bean.SpuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SpuSaleAttrValueMapper extends Mapper<SpuSaleAttrValue>{
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(Map<String, String> stringStringHashMap);
}
