package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private Integer positionId;
    private String areaId;
    @TableField(exist = false)
    private String positionName;
    @TableField(exist = false)
    private Integer identity;  // 身份标识
}
