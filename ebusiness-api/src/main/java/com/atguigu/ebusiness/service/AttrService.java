package com.atguigu.ebusiness.service;

import com.atguigu.ebusiness.bean.BaseAttrInfo;

import java.util.List;

public interface AttrService {

    List<BaseAttrInfo> getBaseAttrInfo(String catalog3Id);

    void saveAttrInfo(BaseAttrInfo baseAttrInfo);

    void updateAttrInfo(BaseAttrInfo baseAttrInfo);

    void removeAttrInfo(String attrId);
}
