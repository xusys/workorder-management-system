package com.example.demo.service;

import com.example.demo.entity.Order;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ActivitiService {
    List<ProcessDefinition> getDefine();

    List<ProcessInstance> getInstance(int currentpage, int pagesize);

    boolean suspendDefine(String id);

    boolean activateDefine(String id);

    List<HistoricActivityInstance> getHistory(int currentpage, int pagesize);

    @Transactional
    void saveProcess(Order order);

    @Transactional
    void completeTask(String username, String positionName, int identity, String orderId, Boolean flag) throws Exception;

    List<Task> myCommission(String positionId, String areaId);

    @Transactional
    List<Order> myOrder(String username);

    @Transactional
    void setAssignee(String orderId, int positionId, String username, String currPositionName) throws Exception;

    // 获取超时工单
    List<Order> timeoutOrder();

    List<Task> getWarningTask(String positionName, String areaId);

    List<Order> getAllOrders();
}
