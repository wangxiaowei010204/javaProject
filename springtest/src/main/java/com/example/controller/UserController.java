package com.example.controller;

import java.util.List;

import javax.annotation.Resource;

import com.example.Model.UserTest;
import com.example.Service.UserService;
import com.github.pagehelper.PageInfo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
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

    // 注入RedisTemplate
    @Resource
    private RedisTemplate<String, Object> redis;

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = "application/json")
    public UserTest getUser(@PathVariable long id) throws Exception {

        UserTest user = this.userService.getUserById(id);

        return user;
    }

    // 修改用户信息，测试删除缓存
    @RequestMapping(value = "/{id}/change-nick", method = RequestMethod.POST, produces = "application/json")
    public UserTest changeNickname(@PathVariable long id) throws Exception {

        String nick = "abc-" + Math.random();
        UserTest user = this.userService.updateUserNickname(id, nick);

        return user;
    }

    // 使用RedisTemplate访问redis服务器
    @RequestMapping(value = "/redis", method = RequestMethod.GET, produces = "application/json")
    public String redis() throws Exception {

        // 设置键"project-name"，值"qikegu-springboot-redis-demo"
        redis.opsForValue().set("project-name", "qikegu-springboot-redis-demo");
        String value = (String) redis.opsForValue().get("project-name");

        return value;
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public PageInfo<UserTest> listUser(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "page-size", required = false, defaultValue = "5") int pageSize) {

        List<UserTest> result = userService.listUser(page, pageSize);
        // PageInfo包装结果，返回更多分页相关信息
        PageInfo<UserTest> pi = new PageInfo<UserTest>(result);

        return pi;
    }

}