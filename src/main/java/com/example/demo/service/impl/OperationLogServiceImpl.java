package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.entity.OperationLog;
import com.example.demo.mapper.OperatorLogMapper;
import com.example.demo.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationLogServiceImpl implements OperationLogService {
    @Autowired
    OperatorLogMapper operatorLogMapper;

    // 根据操作人查操作询日志
    @Override
    public List<OperationLog> getByOperator(String operator) {
        LambdaQueryWrapper<OperationLog> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OperationLog::getOperator,operator);
        return operatorLogMapper.selectList(lambdaQueryWrapper);
    }
}
