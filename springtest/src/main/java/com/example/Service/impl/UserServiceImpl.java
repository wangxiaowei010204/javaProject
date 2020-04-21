package com.example.Service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.example.Model.UserTest;
import com.example.Service.UserService;
import com.example.repository.UserMapper;
import com.github.pagehelper.PageHelper;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    // 注入mapper类
    @Resource
    private UserMapper userMapper;

    @Override
    public UserTest getUserById(long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<UserTest> listUser(int page, int pageSize) {
        List<UserTest> result = null;
        try {
            // 调用pagehelper分页，采用starPage方式。starPage应放在Mapper查询函数之前
            PageHelper.startPage(page, pageSize); //每页的大小为pageSize，查询第page页的结果
            PageHelper.orderBy("id ASC "); //进行分页结果的排序
            result = userMapper.selectUser();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}