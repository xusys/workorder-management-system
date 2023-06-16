package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(value = "orders")
public class Order {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String createUser;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime updateTime;   // 最近更新时间
    private String orderName;
    private String content;   // 工单具体内容
    private String proDefId; // 流程定义id
    @TableField(exist = false)
    private String status; // 流程当前状态
    private String areaId; // 地区id
    private String areaName; // 地区名
    @TableField(exist = false)
    private Integer positionId;
}
