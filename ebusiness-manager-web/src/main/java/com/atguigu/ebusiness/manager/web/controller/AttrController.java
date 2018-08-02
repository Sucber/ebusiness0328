package com.atguigu.ebusiness.manager.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.ebusiness.bean.BaseAttrInfo;
import com.atguigu.ebusiness.service.AttrService;
import com.fasterxml.jackson.databind.ser.Serializers;
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
    public String removeAttrInfo(String id){
        try {
            attrService.removeAttrInfo(id);
            return "success";
        }catch (Exception e){
            e.printStackTrace();
            return "default";
        }
    }

}
