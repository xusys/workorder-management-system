package com.example.demo.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.common.R;
import com.example.demo.service.UserService;
import com.example.demo.entity.User;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

//    @Autowired
//    private Posi

    // 用户注册
    @PostMapping("/register")
    public R register(@RequestBody User user){
        if(userService.register(user)){
            return R.success(null);
        }
        else return R.error("用户名已存在");
    }

    // 用户登录
    @PostMapping("/login")
    public R login(@RequestBody User user){
        User userInfo=userService.login(user);
        if(userInfo!=null){
            String token= JwtUtil.createToken(userInfo);
            Map<String,Object> map=new HashMap<>();
            return R.success(map.put("token",token));
        }
        else return R.error("用户名或密码错误");
    }

    // 展示用户信息
//    @GetMapping("/user_info")
//    public R getUserInfo(@RequestHeader String token){
//        DecodedJWT verify=JwtUtil.verifyToken(token);
//        String positionName=
//    }
}
