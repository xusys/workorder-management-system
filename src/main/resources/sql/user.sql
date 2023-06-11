create table user
(
    id          bigint auto_increment comment '主键'
        primary key,
    username    varchar(30) collate utf8mb4_bin null comment '用户名',
    password    varchar(255)                    null comment '密码',
    position_id int                             null comment '职位id',
    area_id     char(4)                             null comment '地区id',
    constraint foreign_area
        foreign key (area_id) references area (id),
    constraint foreign_position
        foreign key (position_id) references position (id)
);


