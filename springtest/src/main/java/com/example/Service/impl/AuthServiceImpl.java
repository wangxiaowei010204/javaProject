package com.example.Service.impl;

import javax.annotation.Resource;

import com.example.Model.UserTest;
import com.example.Service.AuthService;
import com.example.auth.AuthUser;
import com.example.auth.JwtUtil;
import com.example.config.SecurityConfig;
import com.example.repository.UserMapper;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtUtil jwtUtil;
    @Autowired
    private SecurityConfig securityConfig;
    // 注入mapper类
    @Resource
    private UserMapper userMapper;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService,
            JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    // 注册用户
    @Override
    public UserTest register(UserTest userToAdd) {
        //密码加密
        String password = userToAdd.getPassword();
        password = securityConfig.myEncoder().encode(password);
        userToAdd.setPassword(password);
        userMapper.register(userToAdd);
        return null;
    }

    // 登录
    @Override
    public String login(String username, String password) {
        // 认证用户，认证失败抛出异常，由JwtAuthError的commence类返回401
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 如果认证通过，返回jwt
        final AuthUser userDetails = (AuthUser) userDetailsService.loadUserByUsername(username);
        final String token = jwtUtil.generateToken(userDetails.getUser());

        return token;
    }

    // 刷新token
    @Override
    public String refresh(String oldToken) {
        String newToken = null;

        try {
            newToken = jwtUtil.refreshToken(oldToken);
        } catch (Exception e) {
            log.debug("异常详情", e);
            log.info("无效token");
        }
        return newToken;
    }
}