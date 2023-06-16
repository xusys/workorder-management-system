package com.example.demo.service;

import com.example.demo.entity.OperationLog;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
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
public class ActivitiService {
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

    public void test(){
        System.out.println(repositoryService);
    }
    public List<ProcessDefinition> getDefine(){

        ProcessDefinitionQuery processDefinitionQuery=repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> definitionList=processDefinitionQuery.list();
        return definitionList;
    }
    public List<ProcessInstance> getInstance(int currentpage, int pagesize){
        ProcessInstanceQuery processInstanceQuery=runtimeService.createProcessInstanceQuery();
        List<ProcessInstance> processInstanceList=processInstanceQuery.listPage(currentpage,pagesize);
        return  processInstanceList;
    }
    public boolean suspendDefine(String id){
        try {
            repositoryService.suspendProcessDefinitionById(id);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }
    public boolean activateDefine(String id){
        try {
            repositoryService.activateProcessDefinitionById(id);
            return true;
        }catch (Exception e)
        {
            return false;
        }
    }
    public List<HistoricActivityInstance> getHistory(int currentpage,int pagesize){

        HistoricActivityInstanceQuery historicActivityInstanceQuery=historyService.createHistoricActivityInstanceQuery();
        List<HistoricActivityInstance> historicActivityInstanceList=historicActivityInstanceQuery.listPage(currentpage,pagesize);
        return historicActivityInstanceList;

    }
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

    @Transactional
    public  void completeTask(String username,String positionName,String orderId,Boolean flag){
        Task task=taskService.createTaskQuery().processInstanceBusinessKey(orderId).singleResult();
        taskService.setVariableLocal(task.getId(),"var",flag);
        OperationLog operationLog=new OperationLog();
        operationLog.setPosition(positionName);
        operationLog.setTaskId(task.getId());
        operationLog.setTaskStatus(flag.toString());
        operationLog.setOperator(username);
        ProcessInstance processInstance=runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
        operationLog.setOrderId(processInstance.getBusinessKey());
        taskService.complete(task.getId());
        operationLogService.save(operationLog);
    }
    public List<Task> myCommission(String positionId,String areaId){
        List<Task>list=taskService.createTaskQuery().taskAssignee(positionId).processVariableValueLike("area", AreaUtil.addWildcards(areaId)).list();
        return list;
    }
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

    public void setAssignee(String orderId,int positionId){
        String positionName=positionService.getById(positionId).getPositionName();
        String taskId = taskService.createTaskQuery().processInstanceBusinessKey(orderId).singleResult().getId();
        taskService.setAssignee(taskId, positionName);
    }

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
        //System.out.println(orderList.size());
        return orderList;
    }

    /**
     * 查询预警任务
     * @param positionName
     * @param areaId
     * @return
     */
    public List<Task> getWarningTask(String positionName, String areaId){
//        long final duration=1000*24*60*60; // 1天的毫秒数
        long duration=1000;
        Date nowDate=new Date();
        List<Task>list=taskService.createTaskQuery()
                .taskAssignee(positionName)
                .processVariableValueLike("area",AreaUtil.addWildcards(areaId))
                .list();
        // 提取出距离创建时间已经超过 2天 的任务
        list.removeIf(task -> (nowDate.getTime() - task.getCreateTime().getTime()) / duration < 10);
        return list;
    }

    public List<Order> getAllOrders(){
        List<Order>list=orderService.getAllExcludeContent();
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

