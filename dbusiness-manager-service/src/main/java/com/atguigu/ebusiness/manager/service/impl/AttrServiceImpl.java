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

    @Override
    public List<BaseAttrInfo> getBaseAttrInfo(String catalog3Id) {
        Example example = new Example(BaseAttrInfo.class);
        example.createCriteria().andEqualTo("catalog3Id",catalog3Id);

        return attrInfoMapper.selectByExample(example);
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //插入一条AttfInfo数据
        attrInfoMapper.insertSelective(baseAttrInfo);
        //获取从数据库获得的attrInfo（需要attrid）
        Example example = new Example(BaseAttrInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("catalog3Id",baseAttrInfo.getCatalog3Id());
        criteria.andEqualTo("attrName",baseAttrInfo.getAttrName());
        BaseAttrInfo backAttrInfo = attrInfoMapper.selectOneByExample(example);
        //插入属性值
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        for (BaseAttrValue baseAttrValue : attrValueList) {
            baseAttrValue.setAttrId(backAttrInfo.getId());
            attrValueMapper.insert(baseAttrValue);
        }
    }

    @Override
    public void updateAttrInfo(BaseAttrInfo baseAttrInfo) {
        attrInfoMapper.updateByPrimaryKey(baseAttrInfo);
        //获取从数据库获得的attrInfo（需要attrid）
        Example example = new Example(BaseAttrInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("catalog3Id",baseAttrInfo.getCatalog3Id());
        criteria.andEqualTo("attrName",baseAttrInfo.getAttrName());
        BaseAttrInfo backAttrInfo = attrInfoMapper.selectOneByExample(example);
        //插入属性值
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        for (BaseAttrValue baseAttrValue : attrValueList) {
            baseAttrValue.setAttrId(backAttrInfo.getId());
            attrValueMapper.insert(baseAttrValue);
        }
    }

    @Override
    public void removeAttrInfo(String attrId) {
        //清空相应属性值
        Example example = new Example(BaseAttrValue.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("attrId",attrId);
        attrValueMapper.deleteByExample(example);
        //删除属性信息
        attrInfoMapper.deleteByPrimaryKey(attrId);
    }
}
