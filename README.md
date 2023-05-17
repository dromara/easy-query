<p align="center">
  <img height="340" src="./imgs/logo.png">
</p>

## 📚 文档
[GITHUB地址](https://xuejmnet.github.io/easy-query-doc/) | [GITEE地址](https://xuejm.gitee.io/easy-query-doc/)


- [使用介绍](#使用介绍)
    - [简介](#简介)
    - [如何获取最新版本](#如何获取最新版本)
    - [安装](#安装)
- [开始](#开始)
    - [单表查询](#单表查询)
    - [多表查询](#多表查询)
    - [复杂查询](#复杂查询)
    - [动态表名](#动态表名)
    - [新增](#新增)
    - [修改](#修改)
    - [删除](#删除)
- [捐赠](#捐赠)


# 使用介绍
`easy-query` 🚀 一款高性能、轻量级、多功能的Java对象查询ORM框架支持分库分表读写分离

## 简介
`easy-query`是一款没有任何依赖的JAVA ORM 框架，十分轻量，拥有非常高的性能，支持单表查询、多表查询、union、子查询、分页、动态表名、VO对象查询返回、逻辑删、全局拦截、数据库列加密(支持高性能like查询)、数据追踪差异更新、乐观锁、多租户、分库、分表、读写分离，支持框架全功能外部扩展定制，拥有强类型表达式。


## 如何获取最新版本

[https://central.sonatype.com/](https://central.sonatype.com/) 搜索`easy-query`获取最新安装包

## 安装
以下是`spring-boot`环境和控制台模式的安装
### spring-boot

```xml
<properties>
    <easy-query.version>0.5.1</easy-query.version>
</properties>
<dependency>
    <groupId>com.easy-query</groupId>
    <artifactId>sql-springboot-starter</artifactId>
    <version>${easy-query.version}</version>
</dependency>
```
### console
以mysql为例
```xml
<properties>
    <easy-query.version>0.5.1</easy-query.version>
</properties>
<dependency>
    <groupId>com.easy-query</groupId>
    <artifactId>sql-mysql</artifactId>
    <version>${easy-query.version}</version>
</dependency>
```


# 开始
sql脚本
```sql
create table t_topic
(
    id varchar(32) not null comment '主键ID'primary key,
    stars int not null comment '点赞数',
    title varchar(50) null comment '标题',
    create_time datetime not null comment '创建时间'
)comment '主题表';

create table t_blog
(
    id varchar(32) not null comment '主键ID'primary key,
    deleted tinyint(1) default 0 not null comment '是否删除',
    create_by varchar(32) not null comment '创建人',
    create_time datetime not null comment '创建时间',
    update_by varchar(32) not null comment '更新人',
    update_time datetime not null comment '更新时间',
    title varchar(50) not null comment '标题',
    content varchar(256) null comment '内容',
    url varchar(128) null comment '博客链接',
    star int not null comment '点赞数',
    publish_time datetime null comment '发布时间',
    score decimal(18, 2) not null comment '评分',
    status int not null comment '状态',
    `order` decimal(18, 2) not null comment '排序',
    is_top tinyint(1) not null comment '是否置顶',
    top tinyint(1) not null comment '是否置顶'
)comment '博客表';
```
查询对象
```java



@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -4834048418175625051L;

    @Column(primaryKey = true)
    private String id;
    /**
     * 创建时间;创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间;修改时间
     */
    private LocalDateTime updateTime;
    /**
     * 创建人;创建人
     */
    private String createBy;
    /**
     * 修改人;修改人
     */
    private String updateBy;
    /**
     * 是否删除;是否删除
     */
    @LogicDelete(strategy = LogicDeleteStrategyEnum.BOOLEAN)
    private Boolean deleted;
}


@Data
@Table("t_topic")
@ToString
public class Topic {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    private LocalDateTime createTime;
}

@Data
@Table("t_blog")
public class BlogEntity extends BaseEntity{

    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 博客链接
     */
    private String url;
    /**
     * 点赞数
     */
    private Integer star;
    /**
     * 发布时间
     */
    private LocalDateTime publishTime;
    /**
     * 评分
     */
    private BigDecimal score;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 排序
     */
    private BigDecimal order;
    /**
     * 是否置顶
     */
    private Boolean isTop;
    /**
     * 是否置顶
     */
    private Boolean top;
}

```
## 单表查询
```java
Topic topic = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "3"))
                .firstOrNull();

==> Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` = ? LIMIT 1
==> Parameters: 3(String)
<== Time Elapsed: 15(ms)
<== Total: 1           
```

## 多表查询
```java
Topic topic = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3"))
                .firstOrNull();

==> Preparing: SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ? LIMIT 1
==> Parameters: false(Boolean),3(String)
<== Time Elapsed: 2(ms)
<== Total: 1
```

## 复杂查询
join + group +分页
```java

EasyPageResult<BlogEntity> page = easyQuery
        .queryable(Topic.class).asTracking()
        .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
        .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
        .groupBy((t, t1)->t1.column(BlogEntity::getId))
        .select(BlogEntity.class, (t, t1) -> t1.column(BlogEntity::getId).columnSum(BlogEntity::getScore))
        .toPageResult(1, 20);

==> Preparing: SELECT t1.`id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` LIMIT 20
==> Parameters: false(Boolean)
<== Time Elapsed: 5(ms)
<== Total: 20
```
## 动态表名
```java

String sql = easyQuery.queryable(BlogEntity.class)
        .asTable(a->"aa_bb_cc")
        .where(o -> o.eq(BlogEntity::getId, "123"))
        .toSQL();

 SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `aa_bb_cc` t WHERE t.`deleted` = ? AND t.`id` = ?       
```

## 新增
```java

Topic topic = new Topic();
topic.setId(String.valueOf(0));
topic.setStars(100);
topic.setTitle("标题0");
topic.setCreateTime(LocalDateTime.now().plusDays(i));

long rows = easyQuery.insertable(topic).executeRows();
//返回结果rows为1
==> Preparing: INSERT INTO `t_topic` (`id`,`stars`,`title`,`create_time`) VALUES (?,?,?,?) 
==> Parameters: 0(String),100(Integer),标题0(String),2023-03-16T21:34:13.287(LocalDateTime)
<== Total: 1
```

## 修改
```java
long rows = easyQuery.updatable(Topic.class)
                .set(Topic::getStars, 12)
                .where(o -> o.eq(Topic::getId, "2"))
                .executeRows();
//rows为1
easyQuery.updatable(Topic.class)
                    .set(Topic::getStars, 12)
                    .where(o -> o.eq(Topic::getId, "2"))
                    .executeRows(1,"更新失败");
//判断受影响行数并且进行报错,如果当前操作不在事务内执行那么会自动开启事务!!!会自动开启事务!!!会自动开启事务!!!来实现并发更新控制,异常为:EasyQueryConcurrentException 
//抛错后数据将不会被更新

==> Preparing: UPDATE t_topic SET `stars` = ? WHERE `id` = ?
==> Parameters: 12(Integer),2(String)
<== Total: 1
```

## 删除

```java
long l = easyQuery.deletable(Topic.class)
                    .where(o->o.eq(Topic::getTitle,"title998"))
                    .executeRows();
==> Preparing: DELETE FROM t_topic WHERE `title` = ?
==> Parameters: title998(String)
<== Total: 1

Topic topic = easyQuery.queryable(Topic.class).whereId("997").firstNotNull("未找到当前主题数据");
long l = easyQuery.deletable(topic).executeRows();

==> Preparing: DELETE FROM t_topic WHERE `id` = ?
==> Parameters: 997(String)
<== Total: 1
```

## 捐赠
<img src="./imgs/zfb.jpg" title="JetBrains" width=200 />
<img src="./imgs/wx.jpg" title="JetBrains" width=222 />
[博客](https://www.cnblogs.com/xuejiaming)

QQ群:170029046

个人QQ:326308290(欢迎技术支持提供您宝贵的意见)

个人邮箱:326308290@qq.com