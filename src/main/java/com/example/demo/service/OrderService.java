package com.example.demo.service;

import com.example.demo.entity.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    public void save(Order order);
    public void updateStatus(Long id,String status);
}
