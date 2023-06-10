package entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName(value = "orders")
public class Order {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String createUser;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime updateTime;   // 最近更新时间
    private String orderName;
    private String content;   // 工单具体内容
    private String proDefId; // 流程定义id
    private String status; // 流程当前状态
}
