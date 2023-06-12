package com.example.demo.service;

import com.example.demo.entity.OperationLog;

import java.util.List;

public interface OperationLogService {
    public List<OperationLog> getByOperator(String operator);
    public void save(OperationLog operationLog);
}
