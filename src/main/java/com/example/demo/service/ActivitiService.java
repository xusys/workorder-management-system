package com.example.demo.service;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivitiService {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService;
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



}
