package com.example.demo.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.common.R;
import com.example.demo.entity.Area;
import com.example.demo.entity.OperationLog;
import com.example.demo.service.ActivitiService;
import com.example.demo.service.AreaService;
import com.example.demo.service.OperationLogService;
import com.example.demo.service.OrderService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.ActivitiUtil;
import com.example.demo.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/process")
public class ProcessController {
    @Autowired
    ActivitiService activitiService;

    @Autowired
    OperationLogService operationLogService;

    @Autowired
    OrderService orderService;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    AreaService areaService;

    @GetMapping("/getDefine")
    public R getDefine(){
        List<ProcessDefinition> list=activitiService.getDefine();
        return R.success(ActivitiUtil.activitiResult(list));
    }


    @GetMapping("/getProcess")
    public R getInstance(int currentpage, int pagesize){
        List<ProcessInstance> list=activitiService.getInstance(currentpage,pagesize);
        return R.success(ActivitiUtil.activitiResult(list));
    }
    @GetMapping("/suspendDefine")
    public R suspendDefine(String id){
        boolean flag=activitiService.suspendDefine(id);
        return R.success(flag);
    }

    @GetMapping("/activateDefine")
    public R activateDefine(String id){
        boolean flag=activitiService.activateDefine(id);
        return R.success(flag);
    }

    /**
     * 获取当前用户的历史处理工单
     * @param currentpage
     * @param pagesize
     * @return
     */
    @GetMapping("/getHistory")
    public R history(int currentpage,int pagesize){
       List<HistoricActivityInstance> historyList=activitiService.getHistory(currentpage,pagesize);
       // 封装为Order实体类
        List<Order> orderList=new ArrayList<>();
        for(HistoricActivityInstance historicActivityInstance:historyList){
            String processInstanceId = historicActivityInstance.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            orderList.add(orderService.getExcludeContentById(processInstance.getBusinessKey()));
        }
//        return R.success(Util.activitiResult(list));
        return R.success(orderList);
    }

    /**
     * 保存新创建的工单
     * @param token
     * @param order
     * @return
     */
    @PostMapping("/save")
    public R save(@RequestHeader String token, @RequestBody Order order){
        DecodedJWT decode = JwtUtil.verifyToken(token);
        // 从token中获取职位名和地区id
        String createUser=decode.getClaim("username").asString();
        String areaId=decode.getClaim("areaId").asString();
        Area area=areaService.getById(areaId);
        order.setCreateUser(createUser);
        order.setAreaId(areaId);
        order.setAreaName(area.getCity()+area.getDistrict());

        activitiService.saveProcess(order);
        return R.success(0);
    }

    /**
     * 获取当前用户创建的工单
     * @param token
     * @return
     */
    @GetMapping("/myOrder")
    public R myOrder(@RequestHeader String token){
        DecodedJWT decode = JwtUtil.verifyToken(token);
        // 从token中获取职位名
        String username=decode.getClaim("username").asString();
        List<Order>list=activitiService.myOrder(username);
        return R.success(list);
    }

    /**
     * 查询用户待办任务
     * @param token
     * @return
     */
    @GetMapping("/myCommision")
    public R myCommission(@RequestHeader String token){
        // 获取token

        DecodedJWT decode = JwtUtil.verifyToken(token);
        // 从token中获取职位名和地区id
        String positionName=decode.getClaim("positionName").asString();
        String areaId=decode.getClaim("areaId").asString();
        // 调用service
        List<Task>taskList=activitiService.myCommission(positionName,areaId);
        // 封装为Order实体类
        List<Order> orderList=new ArrayList<>();
        for(Task task:taskList){
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            Order order = orderService.getExcludeContentById(processInstance.getBusinessKey());
            order.setStatus(task.getName());
            orderList.add(order);
        }
        return R.success(orderList);
    }

    /**
     * 处理待办任务
     * @param flag
     * @param orderId
     * @param token
     * @return
     */
    @GetMapping("/completeTask")
    public R completeTask(Boolean flag, String orderId, @RequestHeader String token){
        DecodedJWT decode = JwtUtil.verifyToken(token);
        String positionName=decode.getClaim("positionId").asString();
        String username=decode.getClaim("username").asString();
        int identity=decode.getClaim("identity").asInt();
        try {
            activitiService.completeTask(username, positionName, identity, orderId, flag);
            return R.success(flag);
        } catch (Exception e)  // 防止同一职位的人员同时进行操作而导致出错
        {
            return R.error("该工单已被他人处理");
        }
    }

    /**
     * 查询当前用户操作记录
     * @param token
     * @return
     */
    @GetMapping ("/operationLog")
    public R operationLog(@RequestHeader String token){
        DecodedJWT decode = JwtUtil.verifyToken(token);
        String username=decode.getClaim("username").asString();
        List<OperationLog> operationLogList=operationLogService.getByOperator(username);
        // 封装为Order实体类
        List<Order> orderList=new ArrayList<>();
        for(OperationLog operationLog:operationLogList){
            Order order=orderService.getExcludeContentById(operationLog.getOrderId());
            order.setStatus(operationLog.getTaskStatus()); // 更新状态
            orderList.add(order);
        }
        return R.success(orderList);
    }

    /**
     * 将工单转发给其他操作单位
     * @param
     * @param orderId
     * @return
     */
    @PostMapping("/setAssignee")
    public R setAssignee(String orderId, String positionId, @RequestHeader String token){
        if(positionId.equals("")){
            return R.error("未选择协助单位！");
        }
        else {
            String username=JwtUtil.verifyToken(token).getClaim("username").asString();
            String currPositionName = JwtUtil.verifyToken(token).getClaim("positionName").asString();
            try {
                activitiService.setAssignee(orderId, Integer.parseInt(positionId), username, currPositionName);
                return R.success("");
            } catch (Exception e){        // 防止同一职位的操作人员同时操作而导致出错
                return R.error("该工单已被他人处理");
            }
        }
    }

    /**
     * 获取预警工单
     * @param token
     * @return
     */
    @GetMapping("/myWarningTask")
    public R myWarningTask(@RequestHeader String token){
        // 获取token
        DecodedJWT decode = JwtUtil.verifyToken(token);
        // 从token中获取职位名和地区id
        String positionName=decode.getClaim("positionName").asString();
        String areaId=decode.getClaim("areaId").asString();
        // 调用service
        List<Task>taskList=activitiService.getWarningTask(positionName,areaId);
        // 封装为Order实体类
        List<Order> orderList=new ArrayList<>();
        for(Task task:taskList){
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            orderList.add(orderService.getExcludeContentById(processInstance.getBusinessKey()));
        }
//        return R.success(Util.activitiResult(orderList));
        return R.success(orderList);
    }

    /**
     * 获取超时工单
     * @return
     */
    @GetMapping("/timeout")
    public R timeout(){
        List<Order>list=activitiService.timeoutOrder();
        return R.success(ActivitiUtil.activitiResult(list));
    }

    /**
     * 获取所有工单
     * @return
     */
    @GetMapping("/allOrders")
    public R getAllOrders(){
        List<Order>list=activitiService.getAllOrders();
        return R.success(list);
    }

    /**
     * 获取工单详细信息
     * @param orderId
     * @return
     */
    @GetMapping("/orderDetails")
    public R getOrderDetails(String orderId){
        return R.success(orderService.getById(orderId));
    }
}
