package com.example.demo.service.impl;

import com.example.demo.entity.OperationLog;
import com.example.demo.entity.Order;
import com.example.demo.service.OperationLogService;
import com.example.demo.service.OrderService;
import com.example.demo.service.PositionService;
import com.example.demo.service.UserService;
import com.example.demo.utils.AreaUtil;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ActivitiServiceImpl implements com.example.demo.service.ActivitiService {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    HistoryService historyService;
    @Autowired
    OrderService orderService;
    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;
    @Autowired
    OperationLogService operationLogService;

    @Autowired
    PositionService positionService;
    @Override
    public List<ProcessDefinition> getDefine(){

        ProcessDefinitionQuery processDefinitionQuery=repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> definitionList=processDefinitionQuery.list();
        return definitionList;
    }

    @Override
    public List<ProcessInstance> getInstance(int currentpage, int pagesize){
        ProcessInstanceQuery processInstanceQuery=runtimeService.createProcessInstanceQuery();
        List<ProcessInstance> processInstanceList=processInstanceQuery.listPage(currentpage,pagesize);
        return  processInstanceList;
    }

    @Override
    public boolean suspendDefine(String id){
        try {
            repositoryService.suspendProcessDefinitionById(id);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean activateDefine(String id){
        try {
            repositoryService.activateProcessDefinitionById(id);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public List<HistoricActivityInstance> getHistory(int currentpage, int pagesize){

        HistoricActivityInstanceQuery historicActivityInstanceQuery=historyService.createHistoricActivityInstanceQuery();
        List<HistoricActivityInstance> historicActivityInstanceList=historicActivityInstanceQuery.listPage(currentpage,pagesize);
        return historicActivityInstanceList;
    }

    /**
     * 新增工单
     * @param order
     */
    @Override
    @Transactional
    public void saveProcess(Order order)  {
        String positionName= positionService.getById(order.getPositionId()).getPositionName();
        Map<String,Object> map=new HashMap<>();
        map.put("operationAssignee",positionName);
        ProcessInstance processInstance=runtimeService.startProcessInstanceById(order.getProDefId(),map);
        orderService.save(order);
        runtimeService.updateBusinessKey(processInstance.getId(),order.getId());
        runtimeService.setVariableLocal(processInstance.getId(),"area",order.getAreaId());
//        Task task=taskService.createTaskQuery().processInstanceBusinessKey(processInstance.getBusinessKey()).singleResult();
//        orderService.updateStatus(order.getId(),task.getName());
    }


    @Override
    @Transactional
    public  void completeTask(String username, String positionName, int identity, String orderId, Boolean flag) throws Exception {
        Task task=taskService.createTaskQuery().processInstanceBusinessKey(orderId).singleResult();
        // 防止同一职位的人员同时进行操作而导致出错
        if(!task.getAssignee().equals(positionName)){
            throw new Exception();
        }
        taskService.setVariableLocal(task.getId(),"var",flag);
        taskService.complete(task.getId());
        // 将该操作记录至operation_log日志表中
        String taskStatus;
        if(identity==1){
            taskStatus = flag ? "审批通过":"驳回";
        }
        else{
            taskStatus="完成工单";
        }
        OperationLog operationLog=new OperationLog();
        operationLog.setPosition(positionName);
        operationLog.setTaskId(task.getId());
        operationLog.setTaskStatus(taskStatus);
        operationLog.setOperator(username);
        operationLog.setOrderId(orderId);
        operationLogService.save(operationLog);
    }

    @Override
    public List<Task> myCommission(String positionId, String areaId){
        return taskService.createTaskQuery()
                .taskAssignee(positionId)
                .processVariableValueLike("area", AreaUtil.addWildcards(areaId)).list();
    }

    @Override
    @Transactional
    public List<Order> myOrder(String username){
        List<Order>list=orderService.getExcludeByCreateUser(username);
        for(Order order:list){
            HistoricActivityInstanceQuery historicActivityInstanceQuery= historyService.createHistoricActivityInstanceQuery();
            HistoricProcessInstance historicProcessInstance=historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(order.getId()).singleResult();
            List<HistoricActivityInstance> historicActivityInstanceList=historicActivityInstanceQuery.processInstanceId(historicProcessInstance.getId()).orderByHistoricActivityInstanceStartTime().asc().list();
            order.setStatus(historicActivityInstanceList.get(historicActivityInstanceList.size()-1).getActivityName());
        }
        return list;
    }

    @Override
    @Transactional
    public void setAssignee(String orderId, int positionId, String username, String currPositionName) throws Exception {
        String positionName=positionService.getById(positionId).getPositionName();
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(orderId).singleResult();
        // 防止同一职位的操作人员同时操作而导致出错
        if(!currPositionName.equals(task.getAssignee())) {
            throw new Exception();
        }
        String taskId=task.getId();
        taskService.setAssignee(taskId, positionName);
        // 将该操作记录至operation_log日志表中
        OperationLog operationLog=new OperationLog();
        operationLog.setOrderId(orderId);
        operationLog.setPosition(positionName);
        operationLog.setTaskId(taskId);
        operationLog.setTaskStatus("转发工单至"+positionName);
        operationLog.setOperator(username);
        operationLogService.save(operationLog);
    }

    @Override
    // 获取超时工单
    public List<Order> timeoutOrder(){
        List<Order>orderList=new ArrayList<>();
        List<HistoricTaskInstance> list=historyService // 历史任务Service
                .createHistoricTaskInstanceQuery() // 创建历史任务实例查询
//                .processDefinitionKey("new")
                .taskDeleteReasonLike("boundary%")
                .list();
        for(HistoricTaskInstance historicTaskInstance:list){
            HistoricProcessInstance historicProcessInstance=historyService.createHistoricProcessInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).singleResult();
            Order order=orderService.getExcludeContentById(historicProcessInstance.getBusinessKey());
            orderList.add(order);
        }
        return orderList;
    }

    /**
     * 查询预警任务
     * @param positionName
     * @param areaId
     * @return
     */
    @Override
    public List<Task> getWarningTask(String positionName, String areaId){
        long duration=1000*24*60*60; // 1天的毫秒数
//        long duration=1000;
        Date nowDate=new Date();
        List<Task>list=taskService.createTaskQuery()
                .taskAssignee(positionName)
                .processVariableValueLike("area",AreaUtil.addWildcards(areaId))
                .list();
        // 提取出距离创建时间已经超过 2天 的任务
        list.removeIf(task -> (nowDate.getTime() - task.getCreateTime().getTime()) / duration < 10);
        return list;
    }

    @Override
    public List<Order> getAllOrders(){
        List<Order>list=orderService.getAllExcludeContent();
        // 获取工单当前状态
        for(Order order:list){
            HistoricActivityInstanceQuery historicActivityInstanceQuery= historyService.createHistoricActivityInstanceQuery();
            try {
                HistoricProcessInstance historicProcessInstance=historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(order.getId()).singleResult();
                List<HistoricActivityInstance> historicActivityInstanceList=historicActivityInstanceQuery.processInstanceId(historicProcessInstance.getId()).orderByHistoricActivityInstanceStartTime().asc().list();
                order.setStatus(historicActivityInstanceList.get(historicActivityInstanceList.size()-1).getActivityName());
            }catch (Exception e){
                System.out.println("error");
            }
        }
        return list;
    }
}

