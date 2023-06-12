package com.example.demo.service;

import com.example.demo.entity.Order;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

           ProcessInstance processInstance=runtimeService.startProcessInstanceById(order.getProDefId());
           orderService.save(order);
          runtimeService.updateBusinessKey(processInstance.getId(),order.getId().toString());
        Task task=taskService.createTaskQuery().processInstanceBusinessKey(processInstance.getBusinessKey()).singleResult();
        orderService.updateStatus(order.getId(),task.getName());

    }
    @Transactional
    public  void completeTask(String assignee,Long orderId,Boolean flag){
        Task task=taskService.createTaskQuery().processInstanceBusinessKey(orderId.toString()).taskAssignee(assignee).singleResult();
        taskService.setVariable(task.getId(),"var",flag);
        taskService.complete(task.getId());

    }
    public List<Task> myCommission(String username){
        List<Task>list=taskService.createTaskQuery().taskAssignee(username).list();
       return list;
    }
    public List<Order> myOrder(String username){
        List<Order>list=orderService.getByCreateUser(username);
        for(Order order:list){
            HistoricActivityInstanceQuery historicActivityInstanceQuery= historyService.createHistoricActivityInstanceQuery();
            HistoricProcessInstance historicProcessInstance=historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(order.getId().toString()).singleResult();
            List<HistoricActivityInstance> historicActivityInstanceList=historicActivityInstanceQuery.processInstanceId(historicProcessInstance.getId()).list();
            order.setStatus(historicActivityInstanceList.get(historicActivityInstanceList.size()-1).getActivityName());
        }
        return list;


    }

}

