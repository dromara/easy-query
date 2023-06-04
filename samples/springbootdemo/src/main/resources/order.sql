CREATE
DATABASE IF NOT EXISTS `easy-sharding-test` CHARACTER SET 'utf8mb4';
USE
`easy-sharding-test`;
create table testuser
(
    Id        varchar(128) not null
        primary key,
    Name      varchar(128) not null,
    Age       int          not null,
    `order`   varchar(255) null,
    delete_at datetime null
);
create table order_00
(
    id       varchar(32) not null comment '主键ID' primary key,
    uid      varchar(50) not null comment '用户id',
    order_no int         not null comment '订单号'
)comment '订单表';
create table order_01
(
    id       varchar(32) not null comment '主键ID' primary key,
    uid      varchar(50) not null comment '用户id',
    order_no int         not null comment '订单号'
)comment '订单表';
create table order_02
(
    id       varchar(32) not null comment '主键ID' primary key,
    uid      varchar(50) not null comment '用户id',
    order_no int         not null comment '订单号'
)comment '订单表';
create table order_03
(
    id       varchar(32) not null comment '主键ID' primary key,
    uid      varchar(50) not null comment '用户id',
    order_no int         not null comment '订单号'
)comment '订单表';
create table order_04
(
    id       varchar(32) not null comment '主键ID' primary key,
    uid      varchar(50) not null comment '用户id',
    order_no int         not null comment '订单号'
)comment '订单表';

create table t_order_202201
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202202
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202203
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202204
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202205
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202206
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202207
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202208
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202209
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202210
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202211
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202212
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202301
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202302
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202303
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202304
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';
create table t_order_202305
(
    id          varchar(32) not null comment '主键ID' primary key,
    uid         varchar(50) not null comment '用户id',
    order_no    int         not null comment '订单号',
    create_time datetime    not null comment '创建时间'
)comment '订单表';