package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志 实体类
 */
@Data
public class OperationLog {
    @TableId(type = IdType.AUTO)
    private String id;
    private String operator; // 操作人
    private String position; // 操作人职位
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createTime; // 操作时间
    private String orderId; //工单号
    private String taskId; // 任务id
    private String taskStatus; // 任务状态
}
