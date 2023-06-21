package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Position;
import com.example.demo.mapper.PositionMapper;
import com.example.demo.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements PositionService {
    public static final int NO_OPERATING_POSITION=1, OPERATING_POSITION =2;

    @Autowired
    private PositionMapper positionMapper;

    @Override
    public List<Position> getOperatingPositions() {
        LambdaQueryWrapper<Position> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Position::getIdentity, OPERATING_POSITION);
        return positionMapper.selectList(lambdaQueryWrapper);
    }
}
