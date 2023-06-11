package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    public boolean login(User user);
    public boolean register(User user);
}
