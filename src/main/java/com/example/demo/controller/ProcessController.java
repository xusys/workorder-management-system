package com.example.demo.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.common.R;
import com.example.demo.service.ActivitiService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.Util;
import com.example.demo.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/process")
public class ProcessController {
    @Autowired
    ActivitiService activitiService;
    @GetMapping("/getDefine")
    public R getDefine(){
        List<ProcessDefinition> list=activitiService.getDefine();
        Map<String,Object>map =new HashMap<>();
        map.put("processDefineList",(Util.activitiResult(list)));
        return R.success(map);
    }


    @GetMapping("/getProcess")
    public R  getInstance(int currentpage, int pagesize){
        List<ProcessInstance> list=activitiService.getInstance(currentpage,pagesize);
        Map<String,Object>map =new HashMap<>();
        map.put("processInstanceList",(Util.activitiResult(list)));
        return R.success(map);
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

    @GetMapping("/getHistory")
    public  R history(int currentpage,int pagesize){
       List<HistoricActivityInstance>list=activitiService.getHistory(currentpage,pagesize);
        Map<String,Object>map =new HashMap<>();
        map.put("processInstanceList",(Util.activitiResult(list)));
        return R.success(map);
    }
    @PostMapping("/save")
    public R save(HttpServletRequest request, @RequestBody Order order){
        activitiService.saveProcess(order);
        return R.success(0);
    }
    @GetMapping("/myOrder")
    public R myOrder(String username){
        List<Order>list=activitiService.myOrder(username);
        return  R.success(Util.activitiResult(list));
    }

    /**
     * 查询用户待办任务
     * @param token
     * @return
     */
    @GetMapping("/myCommision")
    public R myCommission(String token){
        // 获取token
        DecodedJWT decode = JwtUtil.verifyToken(token);
        // 从token中获取职位名和地区id
        String positionName=decode.getClaim("positionName").asString();
        String areaId=decode.getClaim("areaId").asString();
        // 调用service
        List<Task>list=activitiService.myCommission(positionName,areaId);
        return R.success(Util.activitiResult(list));
    }
    @GetMapping("/completeTask")
    public  R completeTask(Boolean flag,String assignee, Long orderId){
        activitiService.completeTask(assignee,orderId,flag);
        return R.success(flag);
    }

    /**
     * 获取预警工单
     * @param token
     * @return
     */
    @GetMapping("/myWarningTask")
    public R myWarningTask(String token){
        // 获取token
        DecodedJWT decode = JwtUtil.verifyToken(token);
        // 从token中获取职位名和地区id
        String positionName=decode.getClaim("positionName").asString();
        String areaId=decode.getClaim("areaId").asString();
        // 调用service
        List<Task>list=activitiService.getWarningTask(positionName,areaId);
        return R.success(list);
    }
}
