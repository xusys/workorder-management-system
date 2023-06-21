package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class Area {
    @TableId(type = IdType.INPUT)
    private String id;
    private String city;
    private String district;
}
