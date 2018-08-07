package com.atguigu.ebusiness.service;

import com.atguigu.ebusiness.bean.BaseAttrInfo;
import com.atguigu.ebusiness.bean.BaseAttrValue;

import java.util.List;

public interface AttrService {

    List<BaseAttrInfo> getBaseAttrInfo(String catalog3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    void updateAttrInfo(BaseAttrInfo baseAttrInfo);

    void removeAttrInfo(String attrId);

    List<BaseAttrValue> getAttrValueList(String attrInfoId);

    void saveAttrValue(BaseAttrValue baseAttrValue);

    void updateAttrValue(BaseAttrValue baseAttrValue);

    void updateValueTableData(List<BaseAttrValue> valueList);

    List<BaseAttrInfo> getAttrListByCtg3Id(String catalog3Id);
}
