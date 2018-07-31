package com.atguigu.ebusiness.user.controller;

import com.atguigu.ebusiness.bean.UserAddress;
import com.atguigu.ebusiness.service.UserService;
import com.atguigu.ebusiness.bean.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("userInfoList")
    public ResponseEntity<List<UserInfo>> userInfoList(){

        List<UserInfo> userInfoList = userService.userInfoList();
        return ResponseEntity.ok(userInfoList);
    }

    @ResponseBody
    @RequestMapping("userAddressList")
    public ResponseEntity<List<UserAddress>> userAddresssList(){

        List<UserAddress> userAddressList = userService.userAddressList();
        return ResponseEntity.ok(userAddressList);
    }
}
