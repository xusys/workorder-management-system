package com.example.demo.service;

import com.example.demo.entity.Order;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
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
    public void saveProcess(String processDefinitionId,Order order)  {

           ProcessInstance processInstance=runtimeService.startProcessInstanceById(processDefinitionId);
           order.setProDefId(processDefinitionId);
           //oderservice.save(order);
    }
    @Transactional
    public  void completeTask(String assignee,String processInstanceId){
        Task task=taskService.createTaskQuery().taskAssignee(assignee).processInstanceId(processInstanceId).singleResult();
        taskService.complete(task.getId());
        //oderservice.updateStatus(task.getName());
    }


}
