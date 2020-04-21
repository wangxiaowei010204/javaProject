package com.example.auth;

import javax.annotation.Resource;

import com.example.Model.UserTest;
import com.example.repository.UserMapper;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService implements UserDetailsService {

    // 注入mapper类
    @Resource
    private UserMapper userMapper;

    // 加载用户信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 此处应从数据库加载用户信息，为简便起见，直接创建一个用户
        // password的值：$2a$10$EmsokMb6Vkav7m61kY0PtO.ZCLe0h.uJqVAZW7YYBpSUxd/DMkZuG，
        // 是明文123456使用BCryptPasswordEncoder加密的值
        //UserTest user = new UserTest(1l, "abc1", "18768129283", "$2a$10$EmsokMb6Vkav7m61kY0PtO.ZCLe0h.uJqVAZW7YYBpSUxd/DMkZuG", "user");
       
        UserTest user =userMapper.loadUserByUsername(username);
        AuthUser authUser = new AuthUser(user);
        
        return (UserDetails) authUser;
    }
}