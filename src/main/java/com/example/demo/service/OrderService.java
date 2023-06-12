package com.example.demo.service;

import com.example.demo.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    public void save(Order order);
    public void updateStatus(String id,String status);
    public List<Order> getByCreateUser(String createUser);
    public List<Order> getById(String id) ;
}
