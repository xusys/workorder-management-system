package com.example.demo.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class OperationTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        String operationPosition=delegateTask.getVariable("operationPosition").toString();
        delegateTask.setAssignee(operationPosition);
    }
}
