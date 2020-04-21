package com.example.repository;

import java.util.List;

import com.example.Model.UserTest;

public interface UserMapper {
    
    // 对应xml映射文件元素的ID
    UserTest selectByPrimaryKey(long id);

    // 列出用户，对应xml映射文件元素的ID
    List<UserTest> selectUser();

    //根据用户名获取用户信息
    UserTest loadUserByUsername(String nickname);

    //注册用户
    void register(UserTest userTest);
}