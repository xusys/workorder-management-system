package com.example.demo;

import com.example.demo.service.ActivitiService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    TaskService taskService;
    @Autowired
    HistoryService historyService;
    @Autowired
    RuntimeService runtimeService;
    @Test
    public void test1(){
        String fileName = "ererewe.jpg";
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        System.out.println(suffix);
    }
    @Test
    public void testDeployment() {
        // 读取 activiti.cfg.xml 配置文件，创建 ProcessEngine 的同时会创建表

        Deployment deployment=repositoryService.createDeployment().addClasspathResource("processes/process1.bpmn20.xml").addClasspathResource("processes/diagram.png").name("流程").deploy();
        System.out.println(deployment.getId());
    }
    @Test
    public void testPersonalTaskList() {
        // 读取 activiti.cfg.xml 配置文件，创建 ProcessEngine 的同时会创建表


        List<Task> list=taskService.createTaskQuery().processDefinitionKey("process1").list();
        for(Task task:list)
        {

            System.out.println("任务id "+task.getId());
            System.out.println("流程示例 "+task.getProcessInstanceId());
            System.out.println("审批人 "+task.getAssignee());

        }
    }
    @Test
    public void testStartProcess() {
        // 读取 activiti.cfg.xml 配置文件，创建 ProcessEngine 的同时会创建表

//        Map<String,Object> map=new HashMap<>();
//        map.put("num",4);
//        map.put("assignee0","小李2");
//        map.put("assignee1","小王2");
//        map.put("assignee2","小张2");
        // runtimeService.setVariable(executionId, "evection", evection);
        for (int i=0;i<10;i++) {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process1");

            System.out.println(processInstance.getId());
        }

    }
    @Test
    public void testCompleteTask() {
        // 读取 activiti.cfg.xml 配置文件，创建 ProcessEngine 的同时会创建表


        List<Task> list=taskService.createTaskQuery().processDefinitionKey("Third").taskAssignee("李").list();
        //taskService.setVariable(taskId, "evection", evection);
        for(Task task:list)
        {//taskService.complete(task.getId(),map);
            taskService.complete(task.getId());
            System.out.println("complete");
        }

    }
    @Test
    public void testProcessDefinition() {

        ProcessDefinitionQuery processDefinitionQuery=repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition>definitionList=processDefinitionQuery.orderByProcessDefinitionVersion().desc().list();
        for (ProcessDefinition processDefinition:definitionList)
        {
            System.out.println("流程定义ID "+processDefinition.getId());
            System.out.println("流程定义name "+processDefinition.getName());
            System.out.println("流程定义key "+processDefinition.getKey());
            System.out.println("流程定义version "+processDefinition.getVersion());
            System.out.println("流程定义部署ID "+processDefinition.getDeploymentId());
            System.out.println("流程描述 "+ processDefinition.getDescription());
            System.out.println("\n");
        }
    }
    @Test
    public void testProcessInstance() {

        List<ProcessInstance>list=runtimeService.createProcessInstanceQuery().processDefinitionKey("process1").list();
        for (ProcessInstance processInstance:list)
        {
            System.out.println("流程实例ID "+processInstance.getProcessInstanceId());
            System.out.println("流程定义ID "+processInstance.getProcessDefinitionId());
            System.out.println("流程是否执行完成 "+processInstance.isEnded());
            System.out.println("流程是否暂停 "+processInstance.isSuspended());
            System.out.println("流程部署ID "+processInstance.getDeploymentId());
            System.out.println("businessKey "+processInstance.getBusinessKey());

        }
    }
    @Test
    public  void testDeleteDeployment(){
        repositoryService.activateProcessDefinitionByKey("Second");

    }
    @Test
    public  void testDeleteProcess(){

        runtimeService.deleteProcessInstance("1","2");
    }
    @Test
    public void testHistory(){

        HistoricActivityInstanceQuery historicActivityInstanceQuery=historyService.createHistoricActivityInstanceQuery();
        historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime().asc();
        List<HistoricActivityInstance>list=historicActivityInstanceQuery.list();
        for (HistoricActivityInstance historicActivityInstance:list)
        {
            System.out.println(historicActivityInstance.getActivityId());
            System.out.println(historicActivityInstance.getActivityName());
            System.out.println(historicActivityInstance.getProcessDefinitionId());
            System.out.println(historicActivityInstance.getProcessInstanceId());
            System.out.println('\n');
        }
    }
    @Test
    public void testSuspendAllProcess(){

        ProcessDefinition processDefinition=repositoryService.createProcessDefinitionQuery().processDefinitionKey("First").singleResult();
        boolean suspend=processDefinition.isSuspended();
        String definitionId=processDefinition.getId();
        if(suspend)
        {
            repositoryService.activateProcessDefinitionById(definitionId,true,null);
            System.out.println("流程定义已激活 "+definitionId);
        }
        else
        {
            repositoryService.suspendProcessDefinitionById(definitionId,true,null);
            System.out.println("流程定义已挂起 "+definitionId);
        }


    }
    @Test
    public void testSuspend(){
        ProcessInstance processInstance=runtimeService.createProcessInstanceQuery().processInstanceId("45001").singleResult();
        boolean suspend= processInstance.isSuspended();
        String instanceId=processInstance.getId();
        if(suspend)
        {
            runtimeService.activateProcessInstanceById(instanceId);
            System.out.println("流程已激活 "+instanceId);
        }
        else
        {
            runtimeService.suspendProcessInstanceById(instanceId);
            System.out.println("流程已挂起 "+instanceId);
        }


    }

    @Test
    public void findGroupTaskList() {
        // 流程定义key
        String processDefinitionKey = "Third";
        // 任务候选人
        String candidateUser = "赵";
        //  获取processEngine

        //查询组任务
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey(processDefinitionKey)
                .taskCandidateUser(candidateUser)//根据候选人查询
                .list();
        for (Task task : list) {
            System.out.println("----------------------------");
            System.out.println("流程实例id：" + task.getProcessInstanceId());
            System.out.println("任务id：" + task.getId());
            System.out.println("任务负责人：" + task.getAssignee());
            System.out.println("任务名称：" + task.getName());
        }
    }
    @Test
    public void testStartProcess2() {
        // 读取 activiti.cfg.xml 配置文件，创建 ProcessEngine 的同时会创建表



        ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("Third","105");

        System.out.println(processInstance.getId());
    }
    @Test
    public void claimTask(){

        //要拾取的任务id
        String taskId = "82510";
        //任务候选人id
        String userId = "李";
        //拾取任务
        //即使该用户不是候选人也能拾取(建议拾取时校验是否有资格)
        //校验该用户有没有拾取任务的资格
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .taskCandidateUser(userId)//根据候选人查询
                .singleResult();
        if(task!=null){
            //拾取任务
            taskService.claim(taskId, userId);
            System.out.println("任务拾取成功");
        }
    }
    @Test
    public void setAssigneeToGroupTask() {
        //  获取processEngine

        // 当前待办任务
        String taskId = "6004";
        // 任务负责人
        String userId = "zhangsan2";
        // 校验userId是否是taskId的负责人，如果是负责人才可以归还组任务
        Task task = taskService
                .createTaskQuery()
                .taskId(taskId)
                .taskAssignee(userId)
                .singleResult();
        if (task != null) {
            // 如果设置为null，归还组任务,该 任务没有负责人
            taskService.setAssignee(taskId, null);
        }
    }
    @Test
    public void setAssigneeToCandidateUser() {
        //  获取processEngine

        // 当前待办任务
        String taskId = "6004";
        // 任务负责人
        String userId = "zhangsan2";
// 将此任务交给其它候选人办理该 任务
        String candidateuser = "zhangsan";
        // 校验userId是否是taskId的负责人，如果是负责人才可以归还组任务
        Task task = taskService
                .createTaskQuery()
                .taskId(taskId)
                .taskAssignee(userId)
                .singleResult();
        if (task != null) {
            taskService.setAssignee(taskId, candidateuser);
        }
    }

}
