package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Position {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String positionName;
    private Integer identity; // 职位身份标识，1为非操作单位，2为操作单位
}
