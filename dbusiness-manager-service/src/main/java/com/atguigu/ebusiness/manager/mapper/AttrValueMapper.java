package com.atguigu.ebusiness.manager.mapper;

import com.atguigu.ebusiness.bean.BaseAttrValue;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AttrValueMapper extends Mapper<BaseAttrValue>{
    @Select("select id from base_attr_value where attr_id = #{attrId}")
    List<String> selectValueIdByAttrId(String attrId);
}
