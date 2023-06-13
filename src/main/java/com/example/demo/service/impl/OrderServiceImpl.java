package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.entity.Order;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public void save(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public void updateStatus(String id, String status) {
        Order order=new Order();
        order.setId(id);
        order.setStatus(status);
        orderMapper.updateById(order);
    }

    @Override
    public List<Order> getByCreateUser(String createUser) {
        LambdaQueryWrapper<Order> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Order::getCreateUser, createUser);
        return orderMapper.selectList(lambdaQueryWrapper);
    }
    public Order getById(String id) {
        return orderMapper.selectById(id);
    }
    @Override
    public List<Order>getAll(){
        LambdaQueryWrapper<Order> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        return orderMapper.selectList(lambdaQueryWrapper);
    }
}
