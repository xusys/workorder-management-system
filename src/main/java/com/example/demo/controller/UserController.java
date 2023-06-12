package com.example.demo.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.common.R;
import com.example.demo.entity.Area;
import com.example.demo.service.AreaService;
import com.example.demo.service.PositionService;
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

    @Autowired
    private PositionService positionService;

    @Autowired
    private AreaService areaService;

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
            // 获取职位名
            String positionName=positionService.getById(userInfo.getPositionId()).getPositionName();
            userInfo.setPositionName(positionName);
            // 获取所属地区
            Area area=areaService.getById(userInfo.getAreaId());
            String areaName=area.getCity()+area.getDistrict();
            // 创建token
            String token= JwtUtil.createToken(userInfo);
            // 返回token和用户信息
            Map<String,Object> map=new HashMap<>();
            map.put("token",token);
            map.put("username",userInfo.getUsername());
            map.put("position",positionName);
            map.put("area",areaName);
            return R.success(map);
        }
        else return R.error("用户名或密码错误");
    }

}
