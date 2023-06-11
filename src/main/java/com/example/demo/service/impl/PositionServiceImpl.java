package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Position;
import com.example.demo.mapper.PositionMapper;
import com.example.demo.service.PositionService;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {
}
