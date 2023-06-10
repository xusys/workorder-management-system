create table area
(
    id       char(4)     not null comment '主键'
        primary key,
    city     varchar(50) null comment '城市名',
    district varchar(50) null comment '区（县）名'
);


