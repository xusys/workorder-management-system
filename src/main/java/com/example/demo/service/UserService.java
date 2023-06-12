package com.example.demo.service;

import com.example.demo.entity.User;

public interface UserService {
    public User login(User user);
    public boolean register(User user);
    public User getByUsername(String username);
}
