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
    public List<Order> getExcludeByCreateUser(String createUser) {
        LambdaQueryWrapper<Order> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Order.class,order->!order.getColumn().equals("content")) // 排除工单内容字段
                .eq(Order::getCreateUser, createUser);
        return orderMapper.selectList(lambdaQueryWrapper);
    }

    public Order getById(String id) {
        return orderMapper.selectById(id);
    }

    /**
     * 获取排除工单内容外的所有工单数据
     * @param
     * @return
     */
    @Override
    public List<Order> getAllExcludeContent() {
        LambdaQueryWrapper<Order> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Order.class,order->!order.getColumn().equals("content")); // 排除工单内容字段
        return orderMapper.selectList(lambdaQueryWrapper);
    }

    public Order getExcludeContentById(String id){
        LambdaQueryWrapper<Order> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.select(Order.class,order->!order.getColumn().equals("content")).eq(Order::getId, id);
        return orderMapper.selectOne(lambdaQueryWrapper);
    }
}
