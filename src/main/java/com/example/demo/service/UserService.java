package com.example.demo.service;

import entity.User;

public interface UserService {
    public boolean login(User user);
    public boolean register(User user);
}
