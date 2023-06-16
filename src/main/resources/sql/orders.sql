create table orders
(
    id          varchar(255)                                 not null comment '工单号'
        primary key,
    create_user varchar(30)                            null comment '发起人',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time datetime     default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    order_name  varchar(20)                            null comment '工单名称',
    content     varchar(255)                           null comment '工单具体内容',
    pro_def_id  varchar(64)                            null comment '流程定义id',
    status      varchar(100) default '填写工单'        null comment '流程状态',
    area_id     char(4)  default '0000' comment '地区id',
    area_name   varchar(100) comment '地区名'
);


