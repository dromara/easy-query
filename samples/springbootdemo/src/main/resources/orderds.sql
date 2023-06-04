CREATE
DATABASE IF NOT EXISTS `easy-sharding-test` CHARACTER SET 'utf8mb4';
USE
`easy-sharding-test`;
create table order_ds
(
    id       varchar(32) not null comment '主键ID' primary key,
    uid      varchar(50) not null comment '用户id',
    order_no int         not null comment '订单号'
)comment '订单表';
CREATE
DATABASE IF NOT EXISTS `easy-sharding-test1` CHARACTER SET 'utf8mb4';
USE
`easy-sharding-test1`;
create table order_ds
(
    id       varchar(32) not null comment '主键ID' primary key,
    uid      varchar(50) not null comment '用户id',
    order_no int         not null comment '订单号'
)comment '订单表';
CREATE
DATABASE IF NOT EXISTS `easy-sharding-test2` CHARACTER SET 'utf8mb4';
USE
`easy-sharding-test2`;
create table order_ds
(
    id       varchar(32) not null comment '主键ID' primary key,
    uid      varchar(50) not null comment '用户id',
    order_no int         not null comment '订单号'
)comment '订单表';
CREATE
DATABASE IF NOT EXISTS `easy-sharding-test3` CHARACTER SET 'utf8mb4';
USE
`easy-sharding-test3`;
create table order_ds
(
    id       varchar(32) not null comment '主键ID' primary key,
    uid      varchar(50) not null comment '用户id',
    order_no int         not null comment '订单号'
)comment '订单表';