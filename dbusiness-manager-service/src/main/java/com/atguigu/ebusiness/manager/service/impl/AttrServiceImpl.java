package com.atguigu.ebusiness.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.ebusiness.bean.BaseAttrInfo;
import com.atguigu.ebusiness.bean.BaseAttrValue;
import com.atguigu.ebusiness.manager.mapper.AttrInfoMapper;
import com.atguigu.ebusiness.manager.mapper.AttrValueMapper;
import com.atguigu.ebusiness.service.AttrService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class AttrServiceImpl implements AttrService {

    @Autowired
    private AttrInfoMapper attrInfoMapper;

    @Autowired
    private AttrValueMapper attrValueMapper;

    private Example attrInfoExample;

    private Example attrValueExample;

    {
        attrInfoExample = new Example(BaseAttrInfo.class);
        attrValueExample = new Example(BaseAttrValue.class);

    }


    @Override
    public List<BaseAttrInfo> getBaseAttrInfo(String catalog3Id) {

        attrInfoExample.createCriteria().andEqualTo("catalog3Id",catalog3Id);

        return attrInfoMapper.selectByExample(attrInfoExample);
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //插入一条AttfInfo数据
        attrInfoMapper.insertSelective(baseAttrInfo);
        //获取从数据库获得的attrInfo（需要attrid）
        Example.Criteria criteria = attrInfoExample.createCriteria();
        criteria.andEqualTo("catalog3Id",baseAttrInfo.getCatalog3Id());
        criteria.andEqualTo("attrName",baseAttrInfo.getAttrName());
        BaseAttrInfo backAttrInfo = attrInfoMapper.selectOneByExample(attrInfoExample);
        //插入属性值

        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if(attrValueList == null){
            return;
        }
        for (BaseAttrValue baseAttrValue : attrValueList) {
            baseAttrValue.setAttrId(backAttrInfo.getId());
            attrValueMapper.insert(baseAttrValue);
        }
    }

    @Override
    public void updateAttrInfo(BaseAttrInfo baseAttrInfo) {
        //清理example
        attrValueExample.clear();
        attrInfoExample.clear();

        //安全机制&更改属性名
        if(baseAttrInfo==null){
            throw new NullPointerException("baseAttrInfo为空");
        }
        BaseAttrInfo dbAttrInfo = attrInfoMapper.selectByPrimaryKey(baseAttrInfo.getId());
        if(!dbAttrInfo.getAttrName().equals(baseAttrInfo.getAttrName())){
            attrInfoMapper.updateByPrimaryKey(baseAttrInfo);
        }

        //获得数据库中所有attrid下的valueName
        attrValueExample.createCriteria().andEqualTo("attrId",baseAttrInfo.getId());
        List<BaseAttrValue> dbValueList = attrValueMapper.selectByExample(attrValueExample);
        //插入属性值
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if(attrValueList == null){
            return;
        }
//        //遍历baseAttrInfo中的valueList
//        //若有已存在的
//        for (BaseAttrValue baseAttrValue : baseAttrInfo.getAttrValueList()) {
//            if (dbValueList.contains(baseAttrValue)){
//                attrValueMapper.updateByPrimaryKey(baseAttrValue);
//            }else{
//                attrValueMapper.insert(baseAttrValue);
//            }
//        }
        
        
        for (BaseAttrValue baseAttrValue : attrValueList) {
            baseAttrValue.setAttrId(baseAttrInfo.getId());
            attrValueMapper.insert(baseAttrValue);
        }
    }

    @Override
    public void removeAttrInfo(String attrId) {
        //清空相应属性值
        Example.Criteria criteria = attrValueExample.createCriteria();
        criteria.andEqualTo("attrId",attrId);
        attrValueMapper.deleteByExample(attrValueExample);
        //删除属性信息
        attrInfoMapper.deleteByPrimaryKey(attrId);
    }

    @Override
    public List<BaseAttrValue> getAttrValueList(String attrId) {
        attrValueExample.clear();
        attrValueExample.createCriteria().andEqualTo("attrId",attrId);
        System.out.println(attrValueExample);
        return attrValueMapper.selectByExample(attrValueExample);
    }

    @Override
    public void saveAttrValue(BaseAttrValue baseAttrValue) {
        attrValueMapper.insert(baseAttrValue);
    }

    @Override
    public void updateAttrValue(BaseAttrValue baseAttrValue) {
        attrValueMapper.updateByPrimaryKey(baseAttrValue);
    }

    @Override
    public void updateValueTableData(List<BaseAttrValue> valueList) {

    }
}
