CREATE
DATABASE IF NOT EXISTS `sharding-order` CHARACTER SET 'utf8mb4';

USE
`sharding-order`;
create table t_order_202001
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202002
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202003
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202004
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202005
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202006
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202007
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202008
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202009
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202010
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202011
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202012
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
CREATE
DATABASE IF NOT EXISTS `sharding-order1` CHARACTER SET 'utf8mb4';

USE
`sharding-order1`;
create table t_order_202101
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202102
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202103
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202104
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202105
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202106
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202107
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202108
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202109
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202110
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202111
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202112
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);


CREATE
DATABASE IF NOT EXISTS `sharding-order2` CHARACTER SET 'utf8mb4';

USE
`sharding-order2`;
create table t_order_202201
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202202
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202203
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202204
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202205
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202206
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202207
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202208
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202209
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202210
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202211
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202212
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);


CREATE
DATABASE IF NOT EXISTS `sharding-order3` CHARACTER SET 'utf8mb4';

USE
`sharding-order3`;

create table t_order_202301
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202302
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202303
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202304
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202305
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202306
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202307
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202308
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202309
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202310
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202311
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);
create table t_order_202312
(
    id        varchar(128) not null
        primary key,
    order_no     int not null,
    user_id       varchar(128)          not null,
    create_time   datetime not null
);