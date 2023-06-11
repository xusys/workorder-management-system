package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean login(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(User::getPassword).eq(User::getUsername, user.getUsername());
        String passwordEncode=userMapper.selectOne(lambdaQueryWrapper).getPassword();
        System.out.println(passwordEncode);
        return passwordEncode != null && passwordEncoder.matches(user.getPassword(), passwordEncode);
    }

    @Override
    public boolean register(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        if(userMapper.selectOne(lambdaQueryWrapper) == null){
            user.setPassword(passwordEncoder.encode(user.getPassword())); // 密码加密
            userMapper.insert(user);
            return true;
        }
        else return false;
    }
}
