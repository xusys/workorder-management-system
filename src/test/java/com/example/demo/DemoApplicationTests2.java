package com.example.demo;

import com.example.demo.mapper.OrderMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class DemoApplicationTests2 {
    @Autowired
    UserMapper userMapper;

    @Autowired
    OrderMapper orderMapper;

    @Test
    public void testUserSave(){
        User user=new User();
        user.setUsername("test");
        user.setPassword("123");
        user.setAreaId("0100");
        user.setPositionId(1);
        userMapper.insert(user);
    }

    @Test
    public void testAddOrder(){
        Order order =new Order();
        order.setOrderName("test");
        order.setCreateUser("admin");
        order.setContent("test...");
        order.setProDefId("aaa");
        orderMapper.insert(order);
    }

    @Test
    public void testGetAllOrder() {
        List<Order> orderList = orderMapper.selectList(null);
        System.out.println(orderList);
    }
}
