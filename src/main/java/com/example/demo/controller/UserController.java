package com.example.demo.controller;

import com.example.demo.common.R;
import com.example.demo.service.UserService;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public R register(@RequestBody User user){
        if(userService.register(user)){
            return R.success(null);
        }
        else return R.error("用户名已存在");
    }

    @PostMapping("/login")
    public R login(@RequestBody User user){
        if(userService.login(user)){
            return R.success(null);
        }
        else return R.error("用户名或密码错误");
    }
}
