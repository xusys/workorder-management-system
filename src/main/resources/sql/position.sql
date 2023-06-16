create table position
(
    id            int auto_increment comment '职位id'
        primary key,
    position_name varchar(30) null comment '职位名'
);
INSERT INTO `work_order`.`position` (`id`, `position_name`, `identity`) VALUES ('1', '区级经理', '1');
INSERT INTO `work_order`.`position` (`id`, `position_name`, `identity`) VALUES ('2', '市级经理', '1');
INSERT INTO `work_order`.`position` (`id`, `position_name`, `identity`) VALUES ('3', '省级经理', '1');
INSERT INTO `work_order`.`position` (`id`, `position_name`, `identity`) VALUES ('4', '网络运维单位', '2');
INSERT INTO `work_order`.`position` (`id`, `position_name`, `identity`) VALUES ('5', '基站运维单位', '2');
INSERT INTO `work_order`.`position` (`id`, `position_name`, `identity`) VALUES ('6', '客户故障维修单位', '2');



