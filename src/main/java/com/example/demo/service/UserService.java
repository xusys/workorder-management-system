package com.example.demo.service;

import com.example.demo.entity.User;

import java.util.List;

public interface UserService {
    public User login(User user);
    public boolean register(User user);
    public User getByUsername(String username);
}
