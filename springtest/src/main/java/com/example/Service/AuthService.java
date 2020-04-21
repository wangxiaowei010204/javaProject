package com.example.Service;

import com.example.Model.UserTest;

public interface AuthService {

    UserTest register(UserTest userToAdd);

    String login(String username, String password);

    String refresh(String oldToken);

}