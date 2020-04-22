package com.example.Service;

import java.util.List;

import com.example.Model.UserTest;

public interface UserService {
    
    public UserTest getUserById(long userId);

    public List<UserTest> listUser(int page,int pageSize);

    public UserTest updateUserNickname(long userId, String nickname);
}