package com.atguigu.ebusiness.manager.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.ebusiness.bean.BaseAttrInfo;
import com.atguigu.ebusiness.bean.BaseAttrValue;
import com.atguigu.ebusiness.service.AttrService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AttrController {

    @Reference
    private AttrService attrService;

    @RequestMapping("getBaseAttrInfo")
    public List<BaseAttrInfo> getBaseAttrInfo(@RequestParam("id") String id){
        return attrService.getBaseAttrInfo(id);
    }

    @RequestMapping("getAttrListByCtg3Id")
    public List<BaseAttrInfo> getAttrListByCtg3Id(@RequestParam("catalog3Id") String catalog3Id){
        return attrService.getAttrListByCtg3Id(catalog3Id);
    }

    @RequestMapping("saveAttrInfo")
    public String saveAttrInfo(BaseAttrInfo baseAttrInfo){
        try {
            attrService.saveAttrInfo(baseAttrInfo);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "default";
        }
    }

    @RequestMapping("updateAttrInfo")
    public String updateAttrInfo(BaseAttrInfo baseAttrInfo){
        try {
            attrService.updateAttrInfo(baseAttrInfo);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "default";
        }
    }

    @RequestMapping("removeAttrInfo")
    public String removeAttrInfo(String attrId){
        try {
            attrService.removeAttrInfo(attrId);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "default";
        }
    }
    @RequestMapping("getAttrValueList")
    public List<BaseAttrValue> getAttrValueList(String attrId){
        return attrService.getAttrValueList(attrId);
    }

    @RequestMapping("saveAttrValue")
    public String saveAttrValue(BaseAttrValue baseAttrValue){
        try {
            attrService.saveAttrValue(baseAttrValue);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "false";
        }
    }
    @RequestMapping("updateAttrValue")
    public String updateAttrValue(BaseAttrValue baseAttrValue){
        try {
            attrService.updateAttrValue(baseAttrValue);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "false";
        }
    }

    @RequestMapping("updateValueTableData")
    public String updateValueTableData(List<BaseAttrValue> valueList){
        try {
            attrService.updateValueTableData(valueList);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "default";
        }
    }

}
