package com.example.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.entity.Position;

import java.util.List;

public interface PositionService extends IService<Position> {
    public List<Position> getOperatingPositions();
}
