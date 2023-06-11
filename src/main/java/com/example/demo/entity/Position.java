package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Position {
    @TableId(type= IdType.AUTO)
    private Integer id;
    private String positionName;
}
