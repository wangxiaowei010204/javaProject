package com.example.controller;

import java.util.List;

import javax.annotation.Resource;

import com.example.Model.UserTest;
import com.example.Service.UserService;
import com.github.pagehelper.PageInfo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {

    // 注入mapper类
    @Resource
    private UserService userService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    public UserTest getUser(@PathVariable long id) throws Exception {

        UserTest user = this.userService.getUserById(id);

        return user;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public PageInfo<UserTest> listUser(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "page-size", required = false, defaultValue = "5") int pageSize) {

        List<UserTest> result = userService. listUser(page, pageSize);
        // PageInfo包装结果，返回更多分页相关信息
        PageInfo<UserTest> pi = new PageInfo<UserTest>(result);

        return pi;
    }

}