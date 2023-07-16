
create table school_class
(
    id varchar(32) not null comment '主键ID'primary key,
    name varchar(32) not null comment '班级名称'
)comment '班级表';


create table school_student
(
    id varchar(32) not null comment '主键ID'primary key,
    class_id varchar(32) not null comment '班级id',
    name varchar(32) not null comment '学生名称'
)comment '学生表';
create table school_student_address
(
    id varchar(32) not null comment '主键ID'primary key,
    student_id varchar(32) not null comment '学生id',
    address varchar(128) not null comment '学生地址'
)comment '学生地址表';