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

import java.util.ArrayList;
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
        attrInfoExample.clear();
        attrInfoExample.createCriteria().andEqualTo("catalog3Id", catalog3Id);

        return attrInfoMapper.selectByExample(attrInfoExample);
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        //插入一条AttfInfo数据
        attrInfoMapper.insertSelective(baseAttrInfo);
        //插入属性值

        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if (attrValueList == null) {
            return;
        }
        for (BaseAttrValue baseAttrValue : attrValueList) {
            baseAttrValue.setAttrId(baseAttrInfo.getId());
            attrValueMapper.insert(baseAttrValue);
        }
    }

    @Override
    public void updateAttrInfo(BaseAttrInfo baseAttrInfo) {
        //清理example
        attrValueExample.clear();
        attrInfoExample.clear();

        //安全机制&更改属性名
        if (baseAttrInfo == null) {
            throw new NullPointerException("baseAttrInfo为空");
        }
        BaseAttrInfo dbAttrInfo = attrInfoMapper.selectByPrimaryKey(baseAttrInfo.getId());
        if (!dbAttrInfo.getAttrName().equals(baseAttrInfo.getAttrName())) {
            attrInfoMapper.updateByPrimaryKey(baseAttrInfo);
        }

        //判断valueList是否有数据
        List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
        if (attrValueList == null) {
            return;
        }
        //获得数据库中所有attrid下的attrValueId
        List<String> attrValueIdsFormDB = attrValueMapper.selectValueIdByAttrId(baseAttrInfo.getId());
        //指定一个比较大的长度，以免数据过多导致集合扩容次数过多
        List<String> attrValueIdsFormInfo = new ArrayList<String>(100);

        //遍历baseAttrInfo中的valueList
        //若有已存在的valueId，则update数据，若无数据，则insert数据
        for (BaseAttrValue baseAttrValue : baseAttrInfo.getAttrValueList()) {
            if (attrValueIdsFormDB.contains(baseAttrValue.getId())){
                attrValueMapper.updateByPrimaryKey(baseAttrValue);
            }else{
                attrValueMapper.insert(baseAttrValue);
            }
            //将每个valueid封装到另一个list中（减少循环迭代次数从而达到性能损耗降低）
            attrValueIdsFormInfo.add(baseAttrValue.getId());
        }

        //删除用户编辑时删除的属性
        /**
         * 思路：1.遍历数据库中的id
         *      2.与传输过来的id进行判断，若数据库有某个id，而传输过来的id不存在，则删除,反之则不执行操作
         */

        for (String attrValueId : attrValueIdsFormDB) {
            if(!attrValueIdsFormInfo.contains(attrValueId)){
                attrValueMapper.deleteByPrimaryKey(attrValueId);
            }
        }



    }

    @Override
    public void removeAttrInfo(String attrId) {
        //清空相应属性值
        Example.Criteria criteria = attrValueExample.createCriteria();
        criteria.andEqualTo("attrId", attrId);
        attrValueMapper.deleteByExample(attrValueExample);
        //删除属性信息
        attrInfoMapper.deleteByPrimaryKey(attrId);
    }

    @Override
    public List<BaseAttrValue> getAttrValueList(String attrId) {
        attrValueExample.clear();
        attrValueExample.createCriteria().andEqualTo("attrId", attrId);
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

    @Override
    public List<BaseAttrInfo> getAttrListByCtg3Id(String catalog3Id) {
        List<BaseAttrInfo> baseAttrInfoList = this.getBaseAttrInfo(catalog3Id);
        if (baseAttrInfoList == null || baseAttrInfoList.isEmpty()) {
            return null;
        }
        for (BaseAttrInfo attrInfo : baseAttrInfoList) {
            List<BaseAttrValue> attrValueList = this.getAttrValueList(attrInfo.getId());
            if (attrValueList != null && !attrValueList.isEmpty()) {
                attrInfo.setAttrValueList(attrValueList);
            }
        }
        return baseAttrInfoList;
    }
}
