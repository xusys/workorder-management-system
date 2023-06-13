package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     * @param user
     * @return userInfo
     */
    @Override
    public User login(User user) {
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, user.getUsername());
        User userInfo=userMapper.selectOne(lambdaQueryWrapper);
        if(userInfo != null && passwordEncoder.matches(user.getPassword(), userInfo.getPassword())){
            return userInfo;
        }
        else return null;
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
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

    /**
     * 通过用户名查找用户
     * @param username
     * @return
     */
    @Override
    public User getByUsername(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername,username);
        return userMapper.selectOne(lambdaQueryWrapper);
    }
}
