package com.atguigu.ebusiness.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.ebusiness.bean.UserAddress;
import com.atguigu.ebusiness.bean.UserInfo;
import com.atguigu.ebusiness.service.UserService;
import com.atguigu.ebusiness.user.mapper.UserAddressMapper;
import com.atguigu.ebusiness.user.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly = false)
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserInfo> userInfoList() {

        return userInfoMapper.selectAll();
    }

    @Override
    public int deleteUserInfoByUserId(int id){
        return userInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int saveUserInfo(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }

    @Override
    public int updateUserInfo(UserInfo userInfo) {
        return userInfoMapper.updateByPrimaryKey(userInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserAddress> userAddressList() {

        return userAddressMapper.selectAll();
    }

    @Override
    public int deleteUserAddressByUserId(int id){
        return userAddressMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int saveUserAddress(UserAddress userAddress) {
        return userAddressMapper.insert(userAddress);
    }

    @Override
    public int updateUserAddress(UserAddress userAddress) {
        return userAddressMapper.updateByPrimaryKey(userAddress);
    }


}
