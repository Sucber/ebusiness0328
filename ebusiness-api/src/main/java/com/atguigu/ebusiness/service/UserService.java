package com.atguigu.ebusiness.service;

import com.atguigu.ebusiness.bean.UserAddress;
import com.atguigu.ebusiness.bean.UserInfo;

import java.util.List;

public interface UserService {

    public List<UserInfo> userInfoList();

    int deleteUserInfoByUserId(int id);

    int saveUserInfo(UserInfo userInfo);

    int updateUserInfo(UserInfo userInfo);

    public List<UserAddress> userAddressList();

    public int saveUserAddress(UserAddress userAddress);

    public int updateUserAddress(UserAddress userAddress);

    public int deleteUserAddressByUserId(int id);

}
