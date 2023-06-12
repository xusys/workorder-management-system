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

import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    UserService userService;
    @Autowired
    OperationLogService operationLogService;

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
    public  void completeTask(String username,String positionId,Long orderId,Boolean flag){
        Task task=taskService.createTaskQuery().processInstanceBusinessKey(orderId.toString()).taskAssignee(positionId).singleResult();
        taskService.setVariableLocal(task.getId(),"var",flag);
        taskService.complete(task.getId());
        OperationLog operationLog=new OperationLog();
        operationLog.setPosition(positionId);
        operationLog.setTaskId(task.getId());
        operationLog.setTaskStatus(flag.toString());
        operationLog.setOperator(username);
        operationLogService.save(operationLog);

    }
    public List<Task> myCommission(String positionId,String areaId){
        List<Task>list=taskService.createTaskQuery().taskAssignee(positionId).processVariableValueLike("area", AreaUtil.addWildcards(areaId)).list();
        for ( Task task :list){

        }
       return list;
    }
    @Transactional
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
    public Boolean setAssignee(String username,String taskId){
        User user=userService.getByUsername(username);
        if(user!=null&&user.getPositionId()==0){
            taskService.setAssignee(taskId,username);
            return true;
        }
        else return false;
    }
    public List<OperationLog> showOperationLog(String username){
       List<OperationLog>list=operationLogService.getByOperator(username);
        return list;
    }
    public List<Order> timeoutOrder(){
        List<Order>orderList=new ArrayList<>();
        List<HistoricTaskInstance>list=historyService.createHistoricTaskInstanceQuery().taskDeleteReasonLike("%%").list();
        for(HistoricTaskInstance historicTaskInstance:list){
            HistoricProcessInstance historicProcessInstance=historyService.createHistoricProcessInstanceQuery().processInstanceId(historicTaskInstance.getProcessInstanceId()).singleResult();
            Order order=orderService.getById(historicProcessInstance.getBusinessKey()).get(0);
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
    public List<Task> getWarningTask(String positionName, String areaId){
        long duration=1000*24*60*60; // 1天的毫秒数
        Date nowDate=new Date();
        List<Task>list=taskService.createTaskQuery()
                .taskAssignee(positionName)
                .processVariableValueLike("areaId",AreaUtil.addWildcards(areaId))
                .list();
        // 提取出距离创建时间已经超过 2天 的任务
        list.removeIf(task -> (nowDate.getTime() - task.getCreateTime().getTime()) / duration > 2);
        return list;
    }

}

