create table operation_log
(
    id          bigint auto_increment comment '主键'
        primary key,
    operator    varchar(30)                        null comment '操作人',
    position    varchar(30)                        null comment '操作人员职位',
    create_time datetime default CURRENT_TIMESTAMP not null comment '操作时间',
    order_id    varchar(255)                       null comment '工单号',
    task_id     varchar(255)                       null comment '任务id',
    task_status varchar(50)                        null comment '任务状态'
);

create index order_id
    on operation_log (order_id);


