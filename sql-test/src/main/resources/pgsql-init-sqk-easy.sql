-- '博客表';
create table t_blog
(
    id varchar(32) not null ,
    deleted BOOLEAN default false not null,
    create_by varchar(32) not null,
    create_time TIMESTAMP not null,
    update_by varchar(32) not null,
    update_time TIMESTAMP not null,
    title varchar(50) not null,
    content varchar(256) null ,
    url varchar(128) null ,
    star int not null ,
    publish_time TIMESTAMP null ,
    score decimal(18, 2) not null ,
    status int not null ,
    "order" decimal(18, 2) not null ,
    is_top BOOLEAN not null ,
    top BOOLEAN not null,
    primary key(id)
);

COMMENT ON TABLE t_blog IS '博客表';
COMMENT ON COLUMN t_blog.id IS '主键ID';
COMMENT ON COLUMN t_blog.deleted IS '是否删除';
COMMENT ON COLUMN t_blog.create_by IS '创建人';
COMMENT ON COLUMN t_blog.create_time IS '创建时间';
COMMENT ON COLUMN t_blog.update_by IS '更新人';
COMMENT ON COLUMN t_blog.update_time IS '更新时间';
COMMENT ON COLUMN t_blog.title IS '标题';
COMMENT ON COLUMN t_blog.content IS '内容';
COMMENT ON COLUMN t_blog.url IS '博客链接';
COMMENT ON COLUMN t_blog.star IS '点赞数';
COMMENT ON COLUMN t_blog.publish_time IS '发布时间';
COMMENT ON COLUMN t_blog.score IS '评分';
COMMENT ON COLUMN t_blog.status IS '状态';
COMMENT ON COLUMN t_blog."order" IS '排序';
COMMENT ON COLUMN t_blog.is_top IS '是否置顶';
COMMENT ON COLUMN t_blog.top IS '是否置顶';

create table t_topic
(
    id varchar(32) not null comment '主键ID'primary key,
    stars int not null comment '点赞数',
    title varchar(50) null comment '标题',
    create_time datetime not null comment '创建时间'
)comment '主题表';


create table t_topic_auto
(
    id int  not null comment '主键ID'primary key auto_increment,
    stars int not null comment '点赞数',
    title varchar(50) not null comment '标题',
    create_time datetime not null comment '创建时间'
)comment '主题表id自增';



create table t_sys_user
(
    id varchar(32) not null comment '主键ID'primary key,
    username varchar(50) null comment '姓名',
    phone varchar(250) null comment '手机号加密',
    id_card varchar(500) null comment '身份证编号',
    address text null comment '地址',
    create_time datetime not null comment '创建时间'
)comment '用户表';



create table t_logic_del_topic
(
    id varchar(32) not null comment '主键ID'primary key,
    stars int not null comment '点赞数',
    title varchar(50) null comment '标题',
    deleted tinyint(1)  not null comment '是否删除',
    create_time datetime not null comment '创建时间'
)comment '逻辑删除主题表';
create table t_logic_del_topic_custom
(
    id varchar(32) not null comment '主键ID'primary key,
    stars int not null comment '点赞数',
    title varchar(50) null comment '标题',
    deleted_at datetime   null comment '删除时间',
    deleted_user varchar(50)   null comment '删除人',
    create_time datetime not null comment '创建时间'
)comment '自定义逻辑删除主题表';


create table t_topic_interceptor
(
    id varchar(32) not null comment '主键ID'primary key,
    stars int not null comment '点赞数',
    title varchar(50) null comment '标题',
    create_time datetime not null comment '创建时间',
    create_by varchar(50) not null comment '创建人',
    update_time datetime not null comment '修改时间',
    update_by varchar(50) not null comment '修改人',
    tenant_id varchar(50) not null comment '租户'
)comment '拦截器主题表';


create table t_sys_user_encryption
(
    id varchar(32) not null comment '主键ID'primary key,
    name varchar(32) not null comment '名称',
    phone_not_support_like varchar(100) null comment '不支持like的手机号',
    phone_support_like varchar(200) null comment '支持like的手机号',
    address_not_support_like varchar(1024)  null comment '支持like的地址',
    address_support_like varchar(1024)  null comment '不支持like的地址'
)comment '用户字段加密表';


create table t_sys_user_track
(
    id varchar(32) not null comment '主键ID'primary key,
    username varchar(50) null comment '姓名',
    phone varchar(250) null comment '手机号加密',
    id_card varchar(500) null comment '身份证编号',
    address text null comment '地址',
    create_time datetime not null comment '创建时间'
)comment '用户追踪表';
create table t_sys_user_version
(
    id varchar(32) not null comment '主键ID'primary key,
    username varchar(50) null comment '姓名',
    phone varchar(250) null comment '手机号加密',
    id_card varchar(500) null comment '身份证编号',
    address text null comment '地址',
    version bigint not null comment '行版本',
    create_time datetime not null comment '创建时间'
)comment '用户版本表';
create table t_sys_user_version_del
(
    id varchar(32) not null comment '主键ID'primary key,
    username varchar(50) null comment '姓名',
    phone varchar(250) null comment '手机号加密',
    id_card varchar(500) null comment '身份证编号',
    address text null comment '地址',
    version bigint not null comment '行版本',
    create_time datetime not null comment '创建时间',
    deleted tinyint(1) not null comment '是否删除'
)comment '用户版本逻辑删除表';





create table category
(
    id varchar(32) not null,
    parent_id varchar(32)  null,
    name varchar(32)  null,
    primary key(id)
);
COMMENT ON TABLE category IS '类目树';
COMMENT ON COLUMN category.id IS '主键ID';
COMMENT ON COLUMN category.parent_id IS '父id';
COMMENT ON COLUMN category.name IS '类目名称';