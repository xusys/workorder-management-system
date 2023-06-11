package com.example.demo.service.impl;

import com.example.demo.entity.Order;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public void save(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public void updateStatus(Long id, String status) {
        Order order=new Order();
        order.setId(id);
        order.setStatus(status);
        orderMapper.updateById(order);
    }
}
