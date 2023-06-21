package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Area;
import com.example.demo.mapper.AreaMapper;
import com.example.demo.service.AreaService;
import org.springframework.stereotype.Service;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {
}
