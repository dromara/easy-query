package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.api.pagination.EasyPageResult;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryOrderByInvalidOperationException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.func.DefaultColumnFunction;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLExpressionUtil;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.test.dto.BlogEntityTest;
import com.easy.query.test.dto.BlogEntityTest2;
import com.easy.query.test.dto.TopicGroupTestDTO;
import com.easy.query.test.dto.TopicRequest;
import com.easy.query.test.dto.TopicSubQueryBlog;
import com.easy.query.test.dto.TopicTypeVO;
import com.easy.query.test.dto.TopicUnion;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicType;
import com.easy.query.test.entity.TopicTypeJson;
import com.easy.query.test.entity.TopicTypeJsonValue;
import com.easy.query.test.entity.TopicTypeTest1;
import com.easy.query.test.entity.TopicTypeTest2;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.enums.TopicTypeEnum;
import com.easy.query.test.func.SQLTestFunc;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.navigateflat.MyUserHome2;
import com.easy.query.test.vo.BlogEntityVO1;
import com.easy.query.test.vo.BlogEntityVO2;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author xuejiaming
 * @FileName: QueryTest.java
 * @Description: 文件说明
 * @Date: 2023/3/16 17:31
 */
public class QueryTest extends BaseTest {

    //    public  static interface  ITable{
//        String getName();
//    }
//    @Data
//    @Table("t_tablea")
//    public  static class  TableA implements ITable{
//        private String name;
//
//    }
//    @Data
//    @Table("t_tableb")
//    public  static class  TableB implements ITable{
//        private String name;
//
//    }
//
//    public <T extends ITable> void aa(Class<T> clazz){
//        String propertyName = EasyLambdaUtil.getPropertyName(T::getName);
//        List<SysUser> sysUsers = easyQuery.queryable(clazz)
//                .select(SysUser.class, o -> o.columnAs(T::getName, SysUser::getUsername))
//                .toList();
//    }
    @Test
    public void query0() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        SysUser sysUser = easyEntityQuery.queryable(SysUser.class)
                .where(s -> s.id().eq("123xxx"))
                .firstOrNull();
        Assert.assertNull(sysUser);

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxx(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void query0_2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        SysUser sysUser = easyEntityQuery.queryable(SysUser.class)
                .where(s -> s.id().eq("123xxx"))
                .singleOrNull();
        Assert.assertNull(sysUser);

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxx(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void query0_1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        SysUser sysUser = easyEntityQuery.queryable(SysUser.class)
                .asAlias("x")
                .where(s -> s.id().eq("123xxx"))
                .firstOrNull();
        Assert.assertNull(sysUser);

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT x.`id`,x.`create_time`,x.`username`,x.`phone`,x.`id_card`,x.`address` FROM `easy-query-test`.`t_sys_user` x WHERE x.`id` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123xxx(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void query1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        BlogEntity blogEntity = easyEntityQuery.queryable(BlogEntity.class)
                .asAlias("x")
                .where(s -> s.id().eq("123"))
                .firstOrNull();
        Assert.assertNull(blogEntity);

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT x.`id`,x.`create_time`,x.`update_time`,x.`create_by`,x.`update_by`,x.`deleted`,x.`title`,x.`content`,x.`url`,x.`star`,x.`publish_time`,x.`score`,x.`status`,x.`order`,x.`is_top`,x.`top` FROM `t_blog` x WHERE x.`deleted` = ? AND x.`id` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void query2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        BlogEntity blogEntity = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.id().eq("97");
                }).firstOrNull();


        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("97", blogEntity.getId());

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),97(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));


    }

    @Test
    public void query3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        BlogEntity blogEntity = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.id().eq("97");
                }).limit(1).select(" 1 ").firstOrNull();


        Assert.assertNotNull(blogEntity);
        Assert.assertNull(blogEntity.getId());

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT  1  FROM `t_blog` WHERE `deleted` = ? AND `id` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),97(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void query4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        BlogEntity blogEntity = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.id().eq("97");
                }).limit(1).select("1 as id").firstOrNull();


        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("1", blogEntity.getId());

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT 1 as id FROM `t_blog` WHERE `deleted` = ? AND `id` = ? LIMIT 1", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),97(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void query5() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> blogs = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.id().in(Arrays.asList("97", "98"));
                }).toList();
        Assert.assertNotNull(blogs);
        Assert.assertEquals(2, blogs.size());
        Assert.assertEquals(false, blogs.get(0).getIsTop());
        Assert.assertEquals(false, blogs.get(0).getTop());
        Assert.assertEquals(true, blogs.get(1).getIsTop());
        Assert.assertEquals(true, blogs.get(1).getTop());


        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),97(String),98(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void query5_1() {
        String[] ids = {"97", "98"};


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> blogs = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.id().in(ids);
                }).toList();
        Assert.assertNotNull(blogs);
        Assert.assertEquals(2, blogs.size());
        Assert.assertEquals(false, blogs.get(0).getIsTop());
        Assert.assertEquals(false, blogs.get(0).getTop());
        Assert.assertEquals(true, blogs.get(1).getIsTop());
        Assert.assertEquals(true, blogs.get(1).getTop());


        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),97(String),98(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void query5_2() {
        String[] ids = {"97", "98"};


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> blogs = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.star().le(100);
                    b.id().notIn(ids);
                }).toList();
        Assert.assertNotNull(blogs);
        Assert.assertEquals(98, blogs.size());


        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `star` <= ? AND `id` NOT IN (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),100(Integer),97(String),98(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void query6() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> blogs = easyEntityQuery.queryable(BlogEntity.class).whereById("97").toList();
        Assert.assertNotNull(blogs);
        Assert.assertEquals(1, blogs.size());
        Assert.assertEquals("97", blogs.get(0).getId());
        Assert.assertEquals(false, blogs.get(0).getIsTop());
        Assert.assertEquals(false, blogs.get(0).getTop());


        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),97(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

    }

    @Test
    public void query7() {
        List<BlogEntity> blogEntities = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.star().le(100);
                }).orderBy(b -> b.createTime().asc())
                .toList();
        LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        int i = 0;
        for (BlogEntity blog : blogEntities) {
            String indexStr = String.valueOf(i);
            Assert.assertEquals(indexStr, blog.getId());
            Assert.assertEquals(indexStr, blog.getCreateBy());
            Assert.assertEquals(begin.plusDays(i), blog.getCreateTime());
            Assert.assertEquals(indexStr, blog.getUpdateBy());
            Assert.assertEquals(begin.plusDays(i), blog.getUpdateTime());
            Assert.assertEquals("title" + indexStr, blog.getTitle());
//            Assert.assertEquals("content" + indexStr, blog.getContent());
            Assert.assertEquals("http://blog.easy-query.com/" + indexStr, blog.getUrl());
            Assert.assertEquals(i, (int) blog.getStar());
            Assert.assertEquals(0, new BigDecimal("1.2").compareTo(blog.getScore()));
            Assert.assertEquals(i % 3 == 0 ? 0 : 1, (int) blog.getStatus());
            Assert.assertEquals(0, new BigDecimal("1.2").multiply(BigDecimal.valueOf(i)).compareTo(blog.getOrder()));
            Assert.assertEquals(i % 2 == 0, blog.getIsTop());
            Assert.assertEquals(i % 2 == 0, blog.getTop());
            Assert.assertEquals(false, blog.getDeleted());
            i++;
        }
    }

    @Test
    public void query7_1() {
        String sql = "SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `star` <= ? ORDER BY `create_time` ASC";

        List<BlogEntity> blogEntities = easyEntityQuery.sqlQuery(sql, BlogEntity.class, Arrays.asList(false, 100));
        LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        int i = 0;
        for (BlogEntity blog : blogEntities) {
            String indexStr = String.valueOf(i);
            Assert.assertEquals(indexStr, blog.getId());
            Assert.assertEquals(indexStr, blog.getCreateBy());
            Assert.assertEquals(begin.plusDays(i), blog.getCreateTime());
            Assert.assertEquals(indexStr, blog.getUpdateBy());
            Assert.assertEquals(begin.plusDays(i), blog.getUpdateTime());
            Assert.assertEquals("title" + indexStr, blog.getTitle());
//            Assert.assertEquals("content" + indexStr, blog.getContent());
            Assert.assertEquals("http://blog.easy-query.com/" + indexStr, blog.getUrl());
            Assert.assertEquals(i, (int) blog.getStar());
            Assert.assertEquals(0, new BigDecimal("1.2").compareTo(blog.getScore()));
            System.out.println("---------------" + i);
            Assert.assertEquals(i % 3 == 0 ? 0 : 1, (int) blog.getStatus());
            Assert.assertEquals(0, new BigDecimal("1.2").multiply(BigDecimal.valueOf(i)).compareTo(blog.getOrder()));
            Assert.assertEquals(i % 2 == 0, blog.getIsTop());
            Assert.assertEquals(i % 2 == 0, blog.getTop());
            Assert.assertEquals(false, blog.getDeleted());
            i++;
        }
    }

    @Test
    public void query7_2() {
        String sql = "SELECT `id`,`create_time` AS createTime,`update_time` AS updateTime,`create_by` AS createBy,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time` AS publishTime,`score`,`status`,`order`,`is_top`  AS isTop,`top` FROM `t_blog` WHERE `deleted` = ? AND `star` <= ? ORDER BY `create_time` ASC";
        List<BlogEntity> blogEntities = easyEntityQuery.sqlQuery(sql, BlogEntity.class, Arrays.asList(false, 100));
        LocalDateTime begin = LocalDateTime.of(2020, 1, 1, 1, 1, 1);
        int i = 0;
        for (BlogEntity blog : blogEntities) {
            String indexStr = String.valueOf(i);
            Assert.assertEquals(indexStr, blog.getId());
            Assert.assertEquals(indexStr, blog.getCreateBy());
            Assert.assertEquals(begin.plusDays(i), blog.getCreateTime());
            Assert.assertEquals(indexStr, blog.getUpdateBy());
            Assert.assertEquals(begin.plusDays(i), blog.getUpdateTime());
            Assert.assertEquals("title" + indexStr, blog.getTitle());
//            Assert.assertEquals("content" + indexStr, blog.getContent());
            Assert.assertEquals("http://blog.easy-query.com/" + indexStr, blog.getUrl());
            Assert.assertEquals(i, (int) blog.getStar());
            Assert.assertEquals(0, new BigDecimal("1.2").compareTo(blog.getScore()));
            Assert.assertEquals(i % 3 == 0 ? 0 : 1, (int) blog.getStatus());
            Assert.assertEquals(0, new BigDecimal("1.2").multiply(BigDecimal.valueOf(i)).compareTo(blog.getOrder()));
            Assert.assertEquals(i % 2 == 0, blog.getIsTop());
            Assert.assertEquals(i % 2 == 0, blog.getTop());
            Assert.assertEquals(false, blog.getDeleted());
            i++;
        }
    }

    @Test
    public void query8() {
        String sql = easyEntityQuery
                .queryable(Topic.class)
                .limit(1).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` LIMIT 1", sql);
        List<Topic> topics = easyEntityQuery
                .queryable(Topic.class)
                .limit(1)
                .toList();
        Assert.assertNotNull(topics);
        Assert.assertEquals(1, topics.size());
        Topic topic = easyEntityQuery
                .queryable(Topic.class)
                .firstOrNull();
        Assert.assertNotNull(topic);
    }

    @Test
    public void query9() {
        Topic topic = easyEntityQuery
                .queryable(Topic.class)
                .where(o -> o.id().eq("3"))
                .firstOrNull();
        Assert.assertNotNull(topic);
        List<Topic> topics = easyEntityQuery
                .queryable(Topic.class)
                .where(o -> o.id().eq("3"))
                .toList();
        Assert.assertNotNull(topics);
        Assert.assertEquals(1, topics.size());
    }

    @Test
    public void query10() {
        String toSql = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, b2) -> t_topic.id().eq(b2.id()))
                .where(o -> o.id().eq("3"))
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ?", toSql);
        Topic topic = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t_topic, b2) -> t_topic.id().eq(b2.id()))
                .where(o -> o.id().eq("3"))
                .firstOrNull();
        Assert.assertNotNull(topic);
        String toSql1 = easyEntityQuery.queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t_topic, b2) -> {
                    b2.title().isNotNull();
                    t_topic.id().eq("3");
                })
                .select(BlogEntity.class, (t, t1) -> t1.FETCHER.allFields())
                .toSQL();

        Assert.assertEquals("SELECT t1.`id`,t1.`create_time`,t1.`update_time`,t1.`create_by`,t1.`update_by`,t1.`deleted`,t1.`title`,t1.`content`,t1.`url`,t1.`star`,t1.`publish_time`,t1.`score`,t1.`status`,t1.`order`,t1.`is_top`,t1.`top` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL AND t.`id` = ?",
                toSql1);

        List<BlogEntity> blogEntities = easyEntityQuery.queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t_topic, b2) -> {
                    b2.title().isNotNull();
                    t_topic.id().eq("3");
                })
                .select(BlogEntity.class, (t, t1) -> t1.FETCHER.allFields())
                .toList();
        Assert.assertNotNull(blogEntities);


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<BlogEntity> blogEntities1 = easyEntityQuery.queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t_topic, b2) -> {
                    b2.title().isNotNull();
                    t_topic.id().eq("3");
                })
                .select(BlogEntity.class, (t, t1) -> t1.FETCHER.allFieldsExclude(t1.id()))
                .toList();


        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t1.`create_time`,t1.`update_time`,t1.`create_by`,t1.`update_by`,t1.`deleted`,t1.`title`,t1.`content`,t1.`url`,t1.`star`,t1.`publish_time`,t1.`score`,t1.`status`,t1.`order`,t1.`is_top`,t1.`top` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL AND t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void query10_1() {
        String toSql = easyEntityQuery
                .queryable(Topic.class)
                .asAlias("y")
                .leftJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .asAlias("z")
                .where(o -> o.id().eq("3"))
                .toSQL();
        Assert.assertEquals("SELECT y.`id`,y.`stars`,y.`title`,y.`create_time` FROM `t_topic` y LEFT JOIN `t_blog` z ON z.`deleted` = ? AND y.`id` = z.`id` WHERE y.`id` = ?",
                toSql);
    }

    @Test
    public void query11() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        EntityQueryable<TopicProxy, Topic> sql = easyEntityQuery
                .queryable(Topic.class)
                .where(o -> o.id().eq("3"));
        Assert.assertNotNull(sql);
        List<String> titles = Arrays.asList("1", "2", "3");
        List<BlogEntity> topics = easyEntityQuery
                .queryable(BlogEntity.class)
                .leftJoin(sql, (a, b) -> a.id().eq(b.id()))
                .where(o -> {
                    o.id().isNotNull();
                    o.id().eq("3");
                })
                .where((a, b) -> {
                    a.id().isNotNull();
                    a.id().eq("3");
                })
                .where((a, b) -> {
                    a.id().isNotNull();
                    b.id().eq("3");
                })
                .where((a, b) -> {
                    a.id().isNotNull();
                    b.id().eq("3");
                    a.or(() -> {
                        for (String title : titles) {
                            a.title().like(title);
                        }
                    });// where t.id is not null and t1.id=3 and(t.title like ? or t.title like ? or t.title like ? ....)
                })
                .toList();
        Assert.assertNotNull(topics);
        Assert.assertEquals(1, topics.size());


        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` = ?) t2 ON t.`id` = t2.`id` WHERE t.`deleted` = ? AND t.`id` IS NOT NULL AND t.`id` = ? AND t.`id` IS NOT NULL AND t.`id` = ? AND t.`id` IS NOT NULL AND t2.`id` = ? AND t.`id` IS NOT NULL AND t2.`id` = ? AND (t.`title` LIKE ? OR t.`title` LIKE ? OR t.`title` LIKE ?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("3(String),false(Boolean),3(String),3(String),3(String),3(String),%1%(String),%2%(String),%3%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }


    @Test
    public void query11_2() {
        EntityQueryable<TopicProxy, Topic> sql = easyEntityQuery
                .queryable(Topic.class)
                .where(o -> o.id().eq("3"));
        Assert.assertNotNull(sql);
        String sql1 = easyEntityQuery
                .queryable(BlogEntity.class)
                .leftJoin(sql, (a, b) -> a.id().eq(b.id()))
                .where(o -> {
                    o.id().isNotNull();
                    o.id().eq("3");
                })
                .select(BlogEntity.class, (a, b) -> Select.of(
                        a.id(),
                        b.expression().sqlSegment("rank() over(order by {0} desc) as rank1", c -> {
                            c.expression(b.stars());
                        })
                ))
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,rank() over(order by t2.`stars` desc) as rank1 FROM `t_blog` t LEFT JOIN (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` = ?) t2 ON t.`id` = t2.`id` WHERE t.`deleted` = ? AND t.`id` IS NOT NULL AND t.`id` = ?", sql1);
    }

    @Test
    public void query11_3() {

        EntityQueryable<TopicProxy, Topic> sql = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.id().eq("3");
                });

        Assert.assertNotNull(sql);
        String sql1 = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(sql, (o, o1) -> o.id().eq(o1.id()))
                .where((o, o1) -> {
                    o.id().isNotNull();
                    o.id().eq("3");
                })
                .select(Map.class, (o, o1) -> {
                    return Select.of(
                            o.id(),
                            o1.expression().sqlSegment("rank() over(order by {0} desc) as rank1", c -> {
                                c.expression(o1.stars());
                            })
                    );
                }).toSQL();

        Assert.assertEquals("SELECT t.`id`,rank() over(order by t2.`stars` desc) as rank1 FROM `t_blog` t LEFT JOIN (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` = ?) t2 ON t.`id` = t2.`id` WHERE t.`deleted` = ? AND t.`id` IS NOT NULL AND t.`id` = ?", sql1);
    }

    @Test
    public void query11_1() {
        EntityQueryable<TopicProxy, Topic> sql = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.id().eq("3");
                });
        Assert.assertNotNull(sql);
        String sql1 = easyEntityQuery
                .queryable(BlogEntity.class)
                .leftJoin(sql, (a, b) -> a.id().eq(b.id()))
                .where(o -> {
                    o.id().isNotNull();
                    o.id().eq("3");
                }).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` = ?) t2 ON t.`id` = t2.`id` WHERE t.`deleted` = ? AND t.`id` IS NOT NULL AND t.`id` = ?", sql1);
    }

    @Test
    public void query12() {
        EasyPageResult<Topic> topicPageResult = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.id().isNotNull();
                })
                .orderBy(t_topic -> t_topic.id().asc())
                .toPageResult(2, 20);
        List<Topic> data = topicPageResult.getData();
        Assert.assertEquals(20, data.size());
    }

    @Test
    public void query13() {
        EasyPageResult<BlogEntity> page = easyEntityQuery.queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t_topic, b2) -> t_topic.id().eq(b2.id()))
                .where((t_topic, b2) -> {
                    t_topic.title().isNotNull();
                    b2.id().eq("3");
                }).select(BlogEntity.class, (t_topic, b2) -> Select.of(
                        b2.FETCHER.allFieldsExclude(b2.id())
                ))
                .toPageResult(1, 20);

        Assert.assertEquals(1, page.getTotal());
        Assert.assertEquals(1, page.getData().size());
    }

    @Test
    public void query14() {
        {
            EasyPageResult<BlogEntity> page = easyEntityQuery.queryable(Topic.class)
                    .asTracking()
                    .innerJoin(BlogEntity.class, (t_topic, b2) -> t_topic.id().eq(b2.id()))
                    .where((t_topic, b2) -> {
                        b2.title().isNotNull();
                    }).groupBy((t_topic, b2) -> GroupKeys.of(b2.id()))
                    .select(BlogEntity.class, group -> Select.of(
                            group.key1(),
                            group.groupTable().t2.score().sum()
                    ))
                    .toPageResult(1, 20);
            Assert.assertEquals(100, page.getTotal());
            Assert.assertEquals(20, page.getData().size());
        }
        {

            EasyPageResult<BlogEntity> page = easyEntityQuery.queryable(Topic.class)
                    .asTracking()
                    .innerJoin(BlogEntity.class, (t_topic, b2) -> t_topic.id().eq(b2.id()))
                    .where((t_topic, b2) -> {
                        b2.title().isNotNull();
                    }).groupBy((t_topic, b2) -> GroupKeys.of(b2.id()))
                    .toPageResult(BlogEntity.class, 1, 20);

            Assert.assertEquals(100, page.getTotal());
            Assert.assertEquals(20, page.getData().size());
        }
    }

    @Test
    public void query15() {

        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        String sql = easyEntityQuery
                .queryable(Topic.class).whereObject(topicRequest).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `create_time` > ?", sql);
    }

    @Test
    public void query16() {

        EntityQueryable<BlogEntityProxy, BlogEntity> sql = easyEntityQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.id().eq(t1.id()))
                .where((t_topic, b2) -> {
                    b2.title().isNotNull();
                }).groupBy((t_topic, b2) -> GroupKeys.of(b2.id()))
                .select(group -> new BlogEntityProxy()
                        .id().set(group.key1())
                        .score().set(group.groupTable().t2.score().sum())
                );

        String countSql = sql.cloneQueryable().select("COUNT(1)").toSQL();
        Assert.assertEquals("SELECT COUNT(1) FROM (SELECT t1.`id` AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id`) t2", countSql);
        String limitSql = sql.cloneQueryable().limit(2, 2).toSQL();
        Assert.assertEquals("SELECT t1.`id` AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id` LIMIT 2 OFFSET 2", limitSql);
        String sql1 = sql.cloneQueryable().select(Long.class, o -> o.id().count()).toSQL();
        Assert.assertEquals("SELECT COUNT(t2.`id`) FROM (SELECT t1.`id` AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY t1.`id`) t2", sql1);
    }

    @Test
    public void query16_1() {
        Queryable<BlogEntity> sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.columnFunc(SQLTestFunc.ifNULL(BlogEntity::getId)))
                .select(BlogEntity.class, (t, t1) -> t1.columnFuncAs(SQLTestFunc.ifNULL(BlogEntity::getId), BlogEntity::getId).columnSum(BlogEntity::getScore));
        Queryable<BlogEntity> blogEntityQueryable = sql.cloneQueryable();
        String countSql = sql.cloneQueryable().select("COUNT(1)").toSQL();
        Assert.assertEquals("SELECT COUNT(1) FROM (SELECT IFNULL(t1.`id`,'') AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY IFNULL(t1.`id`,'')) t2", countSql);
        String limitSql = sql.limit(2, 2).toSQL();
        Assert.assertEquals("SELECT IFNULL(t1.`id`,'') AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY IFNULL(t1.`id`,'') LIMIT 2 OFFSET 2", limitSql);
        String sql1 = blogEntityQueryable.select(Long.class, o -> o.columnCount(BlogEntity::getId)).toSQL();
        Assert.assertEquals("SELECT COUNT(t2.`id`) AS `id` FROM (SELECT IFNULL(t1.`id`,'') AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY IFNULL(t1.`id`,'')) t2", sql1);
    }

    @Test
    public void query16_2x() {
        Long aLong = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.columnFunc(SQLTestFunc.ifNULL(BlogEntity::getId)))
                .select(BlogEntity.class, (t, t1) -> t1.columnFuncAs(SQLTestFunc.ifNULL(BlogEntity::getId), BlogEntity::getId).columnSum(BlogEntity::getScore))
                .distinct()
                .select(Long.class, o -> o.columnCount(BlogEntity::getId)).firstOrNull();
        System.out.println(aLong);
    }

    @Test
    public void query16_1_1() {
        Queryable<BlogEntity> sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.columnFunc(SQLTestFunc.ifNULL(BlogEntity::getId)))
                .select(BlogEntity.class, (t, t1) -> t1.groupKeysAs(0, BlogEntity::getId).columnSum(BlogEntity::getScore));
        Queryable<BlogEntity> blogEntityQueryable = sql.cloneQueryable();
        String countSql = sql.cloneQueryable().select("COUNT(1)").toSQL();
        Assert.assertEquals("SELECT COUNT(1) FROM (SELECT IFNULL(t1.`id`,'') AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY IFNULL(t1.`id`,'')) t2", countSql);
        String limitSql = sql.limit(2, 2).toSQL();
        Assert.assertEquals("SELECT IFNULL(t1.`id`,'') AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY IFNULL(t1.`id`,'') LIMIT 2 OFFSET 2", limitSql);
        String sql1 = blogEntityQueryable.select(Long.class, o -> o.columnCount(BlogEntity::getId)).toSQL();
        Assert.assertEquals("SELECT COUNT(t2.`id`) AS `id` FROM (SELECT IFNULL(t1.`id`,'') AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY IFNULL(t1.`id`,'')) t2", sql1);
    }

    @Test
    public void query16_1_2() {
        Queryable<BlogEntity> sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.sqlNativeSegment("IFNULL({0},'''')", c -> {
                    c.expression(BlogEntity::getId);
                }))
                .select(BlogEntity.class, (t, t1) -> t1.groupKeysAs(0, BlogEntity::getId).columnSum(BlogEntity::getScore));
        Queryable<BlogEntity> blogEntityQueryable = sql.cloneQueryable();
        String countSql = sql.cloneQueryable().select("COUNT(1)").toSQL();
        Assert.assertEquals("SELECT COUNT(1) FROM (SELECT IFNULL(t1.`id`,'') AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY IFNULL(t1.`id`,'')) t2", countSql);
        String limitSql = sql.limit(2, 2).toSQL();
        Assert.assertEquals("SELECT IFNULL(t1.`id`,'') AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY IFNULL(t1.`id`,'') LIMIT 2 OFFSET 2", limitSql);
        String sql1 = blogEntityQueryable.select(Long.class, o -> o.columnCount(BlogEntity::getId)).toSQL();
        Assert.assertEquals("SELECT COUNT(t2.`id`) AS `id` FROM (SELECT IFNULL(t1.`id`,'') AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY IFNULL(t1.`id`,'')) t2", sql1);
    }

    @Test
    public void query16_1_3() {
        Queryable<BlogEntity> sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.sqlNativeSegment("IFNULL({0},'')", c -> {
                    c.keepStyle().expression(BlogEntity::getId);
                }))
                .select(BlogEntity.class, (t, t1) -> t1.groupKeysAs(0, BlogEntity::getId).columnSum(BlogEntity::getScore));
        Queryable<BlogEntity> blogEntityQueryable = sql.cloneQueryable();
        String countSql = sql.cloneQueryable().select("COUNT(1)").toSQL();
        Assert.assertEquals("SELECT COUNT(1) FROM (SELECT IFNULL(t1.`id`,'') AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY IFNULL(t1.`id`,'')) t2", countSql);
        String limitSql = sql.limit(2, 2).toSQL();
        Assert.assertEquals("SELECT IFNULL(t1.`id`,'') AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY IFNULL(t1.`id`,'') LIMIT 2 OFFSET 2", limitSql);
        String sql1 = blogEntityQueryable.select(Long.class, o -> o.columnCount(BlogEntity::getId)).toSQL();
        Assert.assertEquals("SELECT COUNT(t2.`id`) AS `id` FROM (SELECT IFNULL(t1.`id`,'') AS `id`,SUM(t1.`score`) AS `score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL GROUP BY IFNULL(t1.`id`,'')) t2", sql1);
    }

    @Test
    public void query16_2() {
        Queryable<BlogEntity> sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .groupBy((t, t1) -> t1.columnFunc(SQLTestFunc.ifNULL(BlogEntity::getId)))
                .select(BlogEntity.class, (t, t1) -> t1.columnFuncAs(SQLTestFunc.ifNULL(BlogEntity::getId), BlogEntity::getId).columnSum(BlogEntity::getScore));
        Queryable<BlogEntity> blogEntityQueryable = sql.cloneQueryable();
        List<BlogEntity> list = sql.cloneQueryable().select("COUNT(1)").toList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void query17() {
        Queryable<BlogEntity> sql = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle).then(t).eq(Topic::getId, "3"))
                .select(BlogEntity.class, (t, t1) -> t1.columnAll().columnIgnore(BlogEntity::getId));
        String countSql = sql.cloneQueryable().select("COUNT(1)").toSQL();
        Assert.assertEquals("SELECT COUNT(1) FROM (SELECT t1.`create_time`,t1.`update_time`,t1.`create_by`,t1.`update_by`,t1.`deleted`,t1.`title`,t1.`content`,t1.`url`,t1.`star`,t1.`publish_time`,t1.`score`,t1.`status`,t1.`order`,t1.`is_top`,t1.`top` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL AND t.`id` = ?) t2", countSql);
        String limitSql = sql.limit(2, 2).toSQL();
        Assert.assertEquals("SELECT t1.`create_time`,t1.`update_time`,t1.`create_by`,t1.`update_by`,t1.`deleted`,t1.`title`,t1.`content`,t1.`url`,t1.`star`,t1.`publish_time`,t1.`score`,t1.`status`,t1.`order`,t1.`is_top`,t1.`top` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL AND t.`id` = ? LIMIT 2 OFFSET 2", limitSql);
    }

    @Test
    public void query18() {

        TopicRequest topicRequest = new TopicRequest();
        topicRequest.setCreateTimeBegin(LocalDateTime.now());
        topicRequest.getOrders().add("id");
        try {
            Topic topic = easyQuery
                    .queryable(Topic.class).whereObject(topicRequest).orderByObject(topicRequest).firstOrNull();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof EasyQueryOrderByInvalidOperationException);
        }
        topicRequest.getOrders().clear();
        topicRequest.getOrders().add("createTime");
        String orderSql = easyQuery
                .queryable(Topic.class).whereObject(topicRequest).orderByObject(topicRequest).limit(1).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `create_time` > ? ORDER BY `create_time` ASC LIMIT 1", orderSql);
        String sql = easyQuery
                .queryable(Topic.class).whereObject(topicRequest).orderByObject(topicRequest).toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `create_time` > ? ORDER BY `create_time` ASC", sql);
    }

    @Test
    public void query19() {

        Queryable<TopicGroupTestDTO> sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "3"))
                .groupBy(o -> o.column(Topic::getId))
                .select(TopicGroupTestDTO.class, o -> o.columnAs(Topic::getId, TopicGroupTestDTO::getId).columnCountAs(Topic::getId, TopicGroupTestDTO::getIdCount));
        List<BlogEntity> topics = easyQuery
                .queryable(BlogEntity.class)
                .leftJoin(sql, (a, b) -> a.eq(b, BlogEntity::getId, TopicGroupTestDTO::getId))
                .where(o -> o.isNotNull(BlogEntity::getId).eq(BlogEntity::getId, "3"))
                .toList();
        Assert.assertNotNull(topics);
        Assert.assertEquals(1, topics.size());
    }

    @Test
    public void query19_1() {

        Queryable<TopicGroupTestDTO> sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "3"))
                .groupBy(o -> o.column(Topic::getId))
                .select(TopicGroupTestDTO.class, o -> o.columnAs(Topic::getId, TopicGroupTestDTO::getId).columnCountAs(Topic::getId, TopicGroupTestDTO::getIdCount));
        String sqlPrint = easyQuery
                .queryable(BlogEntity.class)
                .leftJoin(sql, (a, b) -> a.eq(b, BlogEntity::getId, TopicGroupTestDTO::getId))
                .where(o -> o.isNotNull(BlogEntity::getId).eq(BlogEntity::getId, "3"))
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN (SELECT t1.`id` AS `id`,COUNT(t1.`id`) AS `id_count` FROM `t_topic` t1 WHERE t1.`id` = ? GROUP BY t1.`id`) t3 ON t.`id` = t3.`id` WHERE t.`deleted` = ? AND t.`id` IS NOT NULL AND t.`id` = ?", sqlPrint);
    }

    @Test
    public void query19_2() {

        Queryable<TopicGroupTestDTO> sql = easyQuery
                .queryable(Topic.class)
                .queryLargeColumn(false)
                .where(o -> o.eq(Topic::getId, "3"))
                .groupBy(o -> o.column(Topic::getId))
                .select(TopicGroupTestDTO.class, o -> o.columnAs(Topic::getId, TopicGroupTestDTO::getId).columnCountAs(Topic::getId, TopicGroupTestDTO::getIdCount));
        String sqlPrint = easyQuery
                .queryable(BlogEntity.class)
                .leftJoin(sql, (a, b) -> a.eq(b, BlogEntity::getId, TopicGroupTestDTO::getId))
                .where(o -> o.isNotNull(BlogEntity::getId).eq(BlogEntity::getId, "3"))
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN (SELECT t1.`id` AS `id`,COUNT(t1.`id`) AS `id_count` FROM `t_topic` t1 WHERE t1.`id` = ? GROUP BY t1.`id`) t3 ON t.`id` = t3.`id` WHERE t.`deleted` = ? AND t.`id` IS NOT NULL AND t.`id` = ?", sqlPrint);
    }

    @Test
    public void query19_3() {

        Queryable<TopicGroupTestDTO> sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "3"))
                .groupBy(o -> o.column(Topic::getId))
                .select(TopicGroupTestDTO.class, o -> o.columnAs(Topic::getId, TopicGroupTestDTO::getId).columnCountAs(Topic::getId, TopicGroupTestDTO::getIdCount));
        String sqlPrint = easyQuery
                .queryable(BlogEntity.class)
                .queryLargeColumn(false)
                .leftJoin(sql, (a, b) -> a.eq(b, BlogEntity::getId, TopicGroupTestDTO::getId))
                .where(o -> o.isNotNull(BlogEntity::getId).eq(BlogEntity::getId, "3"))
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t LEFT JOIN (SELECT t1.`id` AS `id`,COUNT(t1.`id`) AS `id_count` FROM `t_topic` t1 WHERE t1.`id` = ? GROUP BY t1.`id`) t3 ON t.`id` = t3.`id` WHERE t.`deleted` = ? AND t.`id` IS NOT NULL AND t.`id` = ?", sqlPrint);
    }

    @Test
    public void query20() {

        Queryable<TopicGroupTestDTO> sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "3"))
                .groupBy(o -> o.column(Topic::getId))
                .select(TopicGroupTestDTO.class, o -> o.columnAs(Topic::getId, TopicGroupTestDTO::getId)
                        .columnCountAs(Topic::getId, TopicGroupTestDTO::getIdCount));
        String s = sql.toSQL();
        Assert.assertEquals("SELECT t.`id` AS `id`,COUNT(t.`id`) AS `id_count` FROM `t_topic` t WHERE t.`id` = ? GROUP BY t.`id`", s);
        List<TopicGroupTestDTO> topicGroupTestDTOS = sql.toList();
        Assert.assertNotNull(topicGroupTestDTOS);
        Assert.assertEquals(1, topicGroupTestDTOS.size());
        TopicGroupTestDTO topicGroupTestDTO = topicGroupTestDTOS.get(0);
        Assert.assertEquals(1, (int) topicGroupTestDTO.getIdCount());
        Assert.assertEquals("3", topicGroupTestDTO.getId());
    }

    @Test
    public void query21() {

        Queryable<TopicGroupTestDTO> sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "3"))
                .groupBy(o -> o.column(Topic::getId))
                .select(TopicGroupTestDTO.class, o -> o.column(Topic::getId).columnCountAs(Topic::getId, TopicGroupTestDTO::getIdCount));
        String s = sql.toSQL();
        Assert.assertEquals("SELECT t.`id`,COUNT(t.`id`) AS `id_count` FROM `t_topic` t WHERE t.`id` = ? GROUP BY t.`id`", s);
        List<TopicGroupTestDTO> topicGroupTestDTOS = sql.toList();
        Assert.assertNotNull(topicGroupTestDTOS);
        Assert.assertEquals(1, topicGroupTestDTOS.size());
        TopicGroupTestDTO topicGroupTestDTO = topicGroupTestDTOS.get(0);
        Assert.assertEquals(1, (int) topicGroupTestDTO.getIdCount());
        Assert.assertEquals("3", topicGroupTestDTO.getId());
    }

    @Test
    public void query22() {
        EntityQueryable<BlogEntityProxy, BlogEntity> queryable = easyEntityQuery.queryable(BlogEntity.class).where(o -> o.star().le(100));
        List<Map<String, Object>> maps = queryable.toMaps();
        Assert.assertNotNull(maps);
        Assert.assertEquals(100, maps.size());
    }

    @Test
    public void query23() {
        Map<String, Object> map = easyEntityQuery.queryable(BlogEntity.class).toMap();
        Assert.assertNotNull(map);
        Assert.assertTrue(map.containsKey("create_Time"));
    }

    @Test
    public void query24() {

        EntityQueryable<BlogEntityProxy, BlogEntity> queryable = easyEntityQuery.queryable(BlogEntity.class)
                .asTable(a -> "aa_bb_cc")
                .where(o -> o.id().eq("123"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `aa_bb_cc` WHERE `deleted` = ? AND `id` = ?", sql);
    }

    @Test
    public void query25() {

        EntityQueryable<BlogEntityProxy, BlogEntity> queryable = easyEntityQuery.queryable(BlogEntity.class)
                .asTable(a -> {
                    if ("t_blog".equals(a)) {
                        return "aa_bb_cc1";
                    }
                    return "xxx";
                })
                .where(o -> o.id().eq("123"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `aa_bb_cc1` WHERE `deleted` = ? AND `id` = ?", sql);
    }

    @Test
    public void query26() {
        String toSql1 = easyQuery
                .queryable(Topic.class)
                .asTable(o -> "t_topic_123")
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .asTable("x_t_blog")
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle).then(t).eq(Topic::getId, "3"))
                .select(BlogEntity.class, (t, t1) -> t1.columnAll()).toSQL();
        Assert.assertEquals("SELECT t1.`id`,t1.`create_time`,t1.`update_time`,t1.`create_by`,t1.`update_by`,t1.`deleted`,t1.`title`,t1.`content`,t1.`url`,t1.`star`,t1.`publish_time`,t1.`score`,t1.`status`,t1.`order`,t1.`is_top`,t1.`top` FROM `t_topic_123` t INNER JOIN `x_t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL AND t.`id` = ?",
                toSql1);
    }

    @Test
    public void query27() {
        Queryable<BlogEntityTest> queryable = easyQuery.queryable(BlogEntity.class)
                .select(BlogEntityTest.class);
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
    }

    @Test
    public void query28() {
        {

            Queryable<BlogEntityTest> queryable = easyQuery.queryable(BlogEntity.class)
                    .select(BlogEntityTest.class, o -> o.columnIgnore(BlogEntity::getUrl)
                            .columnFuncAs(SQLTestFunc.ifNULL(BlogEntity::getUrl), BlogEntityTest::getUrl));
            String sql = queryable.toSQL();
            Assert.assertEquals("SELECT IFNULL(t.`url`,'') AS `url` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
        }
        {

            Queryable<BlogEntityTest> queryable = easyQuery.queryable(BlogEntity.class)
                    .select(BlogEntityTest.class, o -> o.columnAll().columnIgnore(BlogEntity::getUrl).columnFuncAs(SQLTestFunc.ifNULL(BlogEntity::getUrl), BlogEntityTest::getUrl));
            String sql = queryable.toSQL();
            Assert.assertEquals("SELECT t.`title`,t.`content`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,IFNULL(t.`url`,'') AS `url` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
        }
        {

            Queryable<BlogEntityTest> queryable = easyQuery.queryable(BlogEntity.class)
                    .select(BlogEntityTest.class, o -> o.columnAll().columnFuncAs(SQLTestFunc.ifNULL(BlogEntity::getUrl), BlogEntityTest::getUrl));
            String sql = queryable.toSQL();
            Assert.assertEquals("SELECT t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,IFNULL(t.`url`,'') AS `url` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
        }
    }

    @Test
    public void query29() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.columnFunc(new ColumnPropertyFunction() {
                    @Override
                    public ColumnFunction getColumnFunction() {
                        return DefaultColumnFunction.LEN;
                    }

                    @Override
                    public String getPropertyName() {
                        return EasyLambdaUtil.getPropertyName(BlogEntity::getId);
                    }
                }, SQLPredicateCompareEnum.EQ, "123"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND LENGTH(`id`) = ?", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNull(blogEntity);
    }

    @Test
    public void query30() {
        Queryable<BlogEntity> queryable = easyQuery.queryable("SELECT * FROM `t_blog` t", BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT * FROM (SELECT * FROM `t_blog` t) t WHERE t.`id` = ?", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNull(blogEntity);
    }

    @Test
    public void query31() {
        Queryable<BlogEntity> queryable = easyQuery.queryable("SELECT * FROM `t_blog` t", BlogEntity.class)
                .noInterceptor()
                .where(o -> o.eq(BlogEntity::getId, "123"))
                .select(BlogEntity.class, o -> o.column(BlogEntity::getId).column(BlogEntity::getContent));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`content` FROM (SELECT * FROM `t_blog` t) t WHERE t.`id` = ?", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNull(blogEntity);
    }

    @Test
    public void query32() {
        List<BlogEntity> blogEntities = easyQuery.sqlQuery("SELECT * FROM `t_blog` t where t.id=?", BlogEntity.class, Collections.singletonList("1"));
        Assert.assertNotNull(blogEntities);
        Assert.assertEquals(1, blogEntities.size());
    }

    @Test
    public void query33() {
        List<BlogEntity> blogEntities = easyQuery.sqlQuery("SELECT * FROM `t_blog` t", BlogEntity.class);
        Assert.assertNotNull(blogEntities);
        Assert.assertTrue(blogEntities.size() >= 100);
    }

    @Test
    public void query34() {
        List<BlogEntity> blogEntities = easyQuery.sqlQuery("SELECT * FROM `t_blog` t where t.id=?", BlogEntity.class, Collections.singletonList("1xx"));
        Assert.assertNotNull(blogEntities);
        Assert.assertEquals(0, blogEntities.size());
    }

    @Test
    public void query35() {
        List<Map<String, Object>> blogs = easyQuery.sqlQueryMap("SELECT * FROM `t_blog` t");
        Assert.assertNotNull(blogs);
        Assert.assertTrue(blogs.size() >= 100);
    }

    @Test
    public void query36() {
        List<Map<String, Object>> blogs = easyQuery.sqlQueryMap("SELECT * FROM `t_blog` t  where t.id=?", Collections.singletonList("1"));
        Assert.assertNotNull(blogs);
        Assert.assertEquals(1, blogs.size());
    }

    @Test
    public void query37() {
        String newContent = UUID.randomUUID().toString();
        long l = easyQuery.sqlExecute("update t_blog set content='" + newContent + "' where id='1'");
        Assert.assertEquals(1, l);
        BlogEntity blog = easyQuery.queryable(BlogEntity.class)
                .whereById("1").firstOrNull();
        Assert.assertNotNull(blog);
        Assert.assertEquals(newContent, blog.getContent());
    }

    @Test
    public void query38() {
        String newContent = UUID.randomUUID().toString();
        long l = easyQuery.sqlExecute("update t_blog set content=? where id=?", Arrays.asList(newContent, "1"));
        Assert.assertEquals(1, l);
        BlogEntity blog = easyQuery.queryable(BlogEntity.class)
                .whereById("1").firstOrNull();
        Assert.assertNotNull(blog);
        Assert.assertEquals(newContent, blog.getContent());
    }


    @Test
    public void query39() {
        {

            Queryable<BlogEntityTest2> queryable = easyQuery.queryable(BlogEntity.class)
                    .select(BlogEntityTest2.class, o -> o.columnAs(BlogEntity::getUrl, BlogEntityTest2::getUrl));
            String sql = queryable.toSQL();
            Assert.assertEquals("SELECT t.`url` AS `my_url` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
            List<BlogEntityTest2> blogEntityTest2s = easyQuery.queryable(BlogEntity.class)
                    .select(BlogEntityTest2.class, o -> o.columnAs(BlogEntity::getUrl, BlogEntityTest2::getUrl)).toList();
            Assert.assertTrue(blogEntityTest2s.size() >= 100);
        }
        {

            Queryable<BlogEntityTest2> queryable = easyQuery.queryable(BlogEntity.class)
                    .select(BlogEntityTest2.class);
            String sql = queryable.toSQL();
//            Assert.assertEquals("SELECT t.`title`,t.`content`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
//            Assert.assertEquals("SELECT t.`title`,t.`content`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
            Assert.assertEquals("SELECT t.`title`,t.`content`,t.`url` AS `my_url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
            List<BlogEntityTest2> blogEntityTest2s = easyQuery.queryable(BlogEntity.class)
                    .select(BlogEntityTest2.class).toList();
            Assert.assertTrue(blogEntityTest2s.size() >= 100);
        }
        {

            Queryable<BlogEntityTest2> queryable = easyQuery.queryable(BlogEntity.class)
                    .select(BlogEntityTest2.class, o -> o.columnAll());
            String sql = queryable.toSQL();
            Assert.assertEquals("SELECT t.`title`,t.`content`,t.`url` AS `my_url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
            List<BlogEntityTest2> blogEntityTest2s = easyQuery.queryable(BlogEntity.class)
                    .select(BlogEntityTest2.class, o -> o.columnAll()).toList();
            Assert.assertTrue(blogEntityTest2s.size() >= 100);
        }
        {

            Queryable<BlogEntityTest2> queryable = easyQuery.queryable(BlogEntity.class)
                    .select(BlogEntityTest2.class, o -> o.columnAll().columnAs(BlogEntity::getUrl, BlogEntityTest2::getUrl));
            String sql = queryable.toSQL();
            Assert.assertEquals("SELECT t.`title`,t.`content`,t.`url` AS `my_url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,t.`url` AS `my_url` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
            List<BlogEntityTest2> blogEntityTest2s = easyQuery.queryable(BlogEntity.class)
                    .select(BlogEntityTest2.class, o -> o.columnAll().columnAs(BlogEntity::getUrl, BlogEntityTest2::getUrl)).toList();
            Assert.assertTrue(blogEntityTest2s.size() >= 100);
        }
        {

            Queryable<BlogEntityTest2> queryable = easyQuery.queryable(BlogEntity.class)
                    .select(BlogEntityTest2.class, o -> o.columnAll().columnIgnore(BlogEntity::getTitle).columnAs(BlogEntity::getUrl, BlogEntityTest2::getUrl));
            String sql = queryable.toSQL();
            Assert.assertEquals("SELECT t.`content`,t.`url` AS `my_url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top`,t.`url` AS `my_url` FROM `t_blog` t WHERE t.`deleted` = ?", sql);
            List<BlogEntityTest2> blogEntityTest2s = easyQuery.queryable(BlogEntity.class)
                    .select(BlogEntityTest2.class, o -> o.columnAll().columnIgnore(BlogEntity::getTitle).columnAs(BlogEntity::getUrl, BlogEntityTest2::getUrl)).toList();
            Assert.assertTrue(blogEntityTest2s.size() >= 100);
        }
    }

    @Test
    public void query40() {
        Queryable<BlogEntity> select = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .orderByDesc((t, t1) -> t1.column(BlogEntity::getPublishTime))
                .select(BlogEntity.class, (t, t1) -> t1.column(BlogEntity::getPublishTime).column(BlogEntity::getId).column(BlogEntity::getScore));
        EntityQueryExpressionBuilder countEntityQueryExpression = EasySQLExpressionUtil.getCountEntityQueryExpression(select.getSQLEntityExpressionBuilder().cloneEntityExpressionBuilder(), false);
        Assert.assertNotNull(countEntityQueryExpression);
        String s = countEntityQueryExpression.toExpression().toSQL(DefaultToSQLContext.defaultToSQLContext(select.getSQLEntityExpressionBuilder().getExpressionContext().getTableContext()));
        Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL", s);
        String s1 = select.limit(0, 20).toSQL();
        Assert.assertEquals("SELECT t1.`publish_time`,t1.`id`,t1.`score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL ORDER BY t1.`publish_time` DESC LIMIT 20", s1);
    }

    @Test
    public void query41() {
        Queryable<BlogEntity> blogEntityQueryable = easyQuery
                .queryable(Topic.class)
                .innerJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getTitle))
                .select(BlogEntity.class, (t, t1) -> t1.column(BlogEntity::getPublishTime).column(BlogEntity::getId).column(BlogEntity::getScore))
                .orderByDesc(t1 -> t1.column(BlogEntity::getPublishTime));
        EntityQueryExpressionBuilder countEntityQueryExpression = EasySQLExpressionUtil.getCountEntityQueryExpression(blogEntityQueryable.getSQLEntityExpressionBuilder().cloneEntityExpressionBuilder(), false);
        Assert.assertNotNull(countEntityQueryExpression);
        String s = countEntityQueryExpression.toExpression().toSQL(DefaultToSQLContext.defaultToSQLContext(blogEntityQueryable.getSQLEntityExpressionBuilder().getExpressionContext().getTableContext()));
        Assert.assertEquals("SELECT COUNT(*) FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL", s);
        String s1 = blogEntityQueryable.limit(0, 20).toSQL();
        Assert.assertEquals("SELECT t2.`publish_time`,t2.`id`,t2.`score` FROM (SELECT t1.`publish_time`,t1.`id`,t1.`score` FROM `t_topic` t INNER JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t1.`title` IS NOT NULL) t2 ORDER BY t2.`publish_time` DESC LIMIT 20", s1);

    }

    @Test
    public void query42() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123").eq(o, BlogEntity::getTitle, BlogEntity::getUrl));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ? AND `title` = `url`", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        Assert.assertNull(blogEntity);
    }

    @Test
    public void query43() {
        TopicGroupTestDTO topicGroupTestDTO = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123"))
                .groupBy(o -> o.column(BlogEntity::getId))
                .select(TopicGroupTestDTO.class, o -> o.columnAs(BlogEntity::getId, TopicGroupTestDTO::getId).columnCountAs(BlogEntity::getId, TopicGroupTestDTO::getIdCount))
                .orderByAsc(o -> o.column(TopicGroupTestDTO::getId)).firstOrNull();
        Assert.assertNull(topicGroupTestDTO);
        TopicGroupTestDTO topicGroupTestDTO1 = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123"))
                .groupBy(o -> o.column(BlogEntity::getId))
                .orderByAsc(o -> o.column(BlogEntity::getId))
                .select(TopicGroupTestDTO.class, o -> o.columnAs(BlogEntity::getId, TopicGroupTestDTO::getId).columnCountAs(BlogEntity::getId, TopicGroupTestDTO::getIdCount))
                .firstOrNull();
        Assert.assertNull(topicGroupTestDTO1);
    }

    @Test
    public void query44() {
        Queryable<TopicGroupTestDTO> topicGroupTestDTOQueryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123"))
                .groupBy(o -> o.column(BlogEntity::getId))
                .select(TopicGroupTestDTO.class, o -> o.columnAs(BlogEntity::getId, TopicGroupTestDTO::getId).columnCountAs(BlogEntity::getId, TopicGroupTestDTO::getIdCount))
                .orderByAsc(o -> o.column(TopicGroupTestDTO::getId));
        String sql = topicGroupTestDTOQueryable.toSQL();
        Assert.assertEquals("SELECT t1.`id` AS `id`,t1.`id_count` AS `id_count` FROM (SELECT t.`id` AS `id`,COUNT(t.`id`) AS `id_count` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? GROUP BY t.`id`) t1 ORDER BY t1.`id` ASC", sql);
        Queryable<TopicGroupTestDTO> select = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123"))
                .groupBy(o -> o.column(BlogEntity::getId))
                .orderByAsc(o -> o.column(BlogEntity::getId))
                .select(TopicGroupTestDTO.class, o -> o.columnAs(BlogEntity::getId, TopicGroupTestDTO::getId).columnCountAs(BlogEntity::getId, TopicGroupTestDTO::getIdCount));
        String sql1 = select.toSQL();
        Assert.assertEquals("SELECT t.`id` AS `id`,COUNT(t.`id`) AS `id_count` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? GROUP BY t.`id` ORDER BY t.`id` ASC", sql1);
    }

    @Test
    public void query45() {
        Queryable<BlogEntity> where = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123"));
        String sql = easyQuery
                .queryable(Topic.class).where(o -> o.exists(where.where(q -> q.eq(o, BlogEntity::getId, Topic::getId)))).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ? AND t1.`id` = t.`id`)", sql);

        Queryable<BlogEntity> subQueryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "1"));
        List<Topic> x = easyQuery
                .queryable(Topic.class).where(o -> o.exists(subQueryable.where(q -> q.eq(o, BlogEntity::getId, Topic::getId)))).toList();
        Assert.assertEquals(1, x.size());
        Assert.assertEquals("1", x.get(0).getId());
    }

    @Test
    public void query46() {
        {

            Queryable<BlogEntity> subQueryable = easyQuery.queryable(BlogEntity.class)
                    .where(o -> o.eq(BlogEntity::getId, "123"));
            String sql = easyQuery
                    .queryable(Topic.class).where(o -> o.notExists(subQueryable.where(q -> q.eq(o, BlogEntity::getId, Topic::getId)))).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE NOT EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ? AND t1.`id` = t.`id`)", sql);

        }
        {

            Queryable<BlogEntity> subQueryable = easyQuery.queryable(BlogEntity.class)
                    .where(o -> o.eq(BlogEntity::getId, "1"));
            List<Topic> x = easyQuery
                    .queryable(Topic.class).where(o -> o.notExists(subQueryable.where(q -> q.eq(o, BlogEntity::getId, Topic::getId)))).toList();
            Assert.assertEquals(100, x.size());
            Assert.assertEquals("0", x.get(0).getId());
            Assert.assertEquals("10", x.get(1).getId());
        }
    }

    @Test
    public void query47() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "98"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `id` = ?", sql);
        BlogEntity blogEntity = queryable.firstOrNull();
        System.out.println(blogEntity);
        Assert.assertNotNull(blogEntity);
        Assert.assertEquals("98", blogEntity.getId());
        Assert.assertEquals("title98", blogEntity.getTitle());
        Assert.assertEquals(true, blogEntity.getTop());
        Assert.assertEquals(true, blogEntity.getIsTop());
        Assert.assertEquals("content98", blogEntity.getContent());
        Assert.assertEquals("http://blog.easy-query.com/98", blogEntity.getUrl());
        Assert.assertEquals(98, (int) blogEntity.getStar());
        Assert.assertNull(blogEntity.getPublishTime());
        Assert.assertEquals(new BigDecimal("1.20"), blogEntity.getScore());
        Assert.assertEquals(1, (int) blogEntity.getStatus());
        Assert.assertEquals(new BigDecimal("117.60"), blogEntity.getOrder());
    }

    @Test
    public void query48() {
        Integer integer1 = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, UUID.randomUUID().toString()))
                .maxOrNull(BlogEntity::getStar);
        Assert.assertNull(integer1);
        Integer integer2 = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, UUID.randomUUID().toString()))
                .maxOrDefault(BlogEntity::getStar, 12345);
        Assert.assertNotNull(integer2);
        Assert.assertEquals(12345, (int) integer2);
    }

    @Test
    public void query49() {
        Integer integer1 = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, UUID.randomUUID().toString()))
                .minOrNull(BlogEntity::getStar);
        Assert.assertNull(integer1);
        Integer integer2 = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, UUID.randomUUID().toString()))
                .minOrDefault(BlogEntity::getStar, 4567);
        Assert.assertNotNull(integer2);
        Assert.assertEquals(4567, (int) integer2);
    }

    @Test
    public void query50() {
        {

            EasyPageResult<Topic> topicPageResult = easyQuery
                    .queryable(Topic.class)
                    .where(o -> o.isNotNull(Topic::getId))
                    .orderByDesc(o -> o.column(Topic::getCreateTime))
                    .toPageResult(3, 10);
            List<Topic> data = topicPageResult.getData();
            Assert.assertEquals(10, data.size());
        }

        {
            EasyPageResult<Topic> topicPageResult = easyQuery
                    .queryable(Topic.class)
                    .where(o -> o.isNotNull(Topic::getId))
                    .orderByDesc(o -> o.column(Topic::getCreateTime))
                    .toPageResult(8, 10);
            List<Topic> data = topicPageResult.getData();
            Assert.assertEquals(10, data.size());
            List<Topic> list = easyQuery
                    .queryable(Topic.class)
                    .where(o -> o.isNotNull(Topic::getId))
                    .orderByDesc(o -> o.column(Topic::getCreateTime))
                    .limit(70, 10).toList();
            Assert.assertEquals(10, list.size());
            for (int i = 0; i < 10; i++) {
                Assert.assertEquals(data.get(i).getId(), list.get(i).getId());
            }
        }
        {
            EasyPageResult<Topic> topicPageResult = easyQuery
                    .queryable(Topic.class)
                    .where(o -> o.isNotNull(Topic::getId))
                    .orderByDesc(o -> o.column(Topic::getCreateTime))
                    .toPageResult(6, 11);
            List<Topic> data = topicPageResult.getData();
            Assert.assertEquals(11, data.size());
            List<Topic> list = easyQuery
                    .queryable(Topic.class)
                    .where(o -> o.isNotNull(Topic::getId))
                    .orderByDesc(o -> o.column(Topic::getCreateTime))
                    .limit(55, 11).toList();
            Assert.assertEquals(11, list.size());
            for (int i = 0; i < 11; i++) {
                Assert.assertEquals(data.get(i).getId(), list.get(i).getId());
            }
        }
    }

    @Test
    public void query51() {

        String sql = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isNotNull(Topic::getId))
                .select(String.class, o -> o.column(Topic::getId)).distinct().toSQL();
        Assert.assertEquals("SELECT DISTINCT t.`id` FROM `t_topic` t WHERE t.`id` IS NOT NULL", sql);
        List<String> list = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isNotNull(Topic::getId))
                .select(String.class, o -> o.column(Topic::getId)).distinct().toList();
        Assert.assertEquals(101, list.size());
    }

    @Test
    public void query52() {
        ClientQueryable<Topic> q1 = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.isNotNull("id"));
        ClientQueryable<Topic> q2 = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.isNull("id"));
        ClientQueryable<Topic> q3 = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.eq("id", "123"));

        String sql = q1.union(q2, q3).toSQL();
        Assert.assertEquals("SELECT t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM ( (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` IS NOT NULL)  UNION  (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NULL)  UNION  (SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM `t_topic` t2 WHERE t2.`id` = ?) ) t3", sql);
    }

    @Test
    public void query52_1_1() {
        Topic topic = easyQueryClient
                .queryable(Topic.class)
                .leftJoin(Topic.class, (t, t1) -> t.eq(t1, "id", "id"))
                .whereById(true, "1")
                .where((a, b) -> a.eq("id", "id"))
                .firstOrNull();
        ClientQueryable<Topic> q1 = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.isNotNull("id"));
        ClientQueryable<Topic> q2 = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.isNull("id"));
        ClientQueryable<Topic> q3 = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.eq("id", "123"));

        String sql = q1.unionAll(q2, q3).toSQL();
        Assert.assertEquals("SELECT t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM ( (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` IS NOT NULL)  UNION ALL  (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NULL)  UNION ALL  (SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM `t_topic` t2 WHERE t2.`id` = ?) ) t3", sql);
    }

    @Test
    public void query52_1() {
        Queryable<Topic> q1 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isNotNull(Topic::getId));
        Queryable<Topic> q2 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isNull(Topic::getId));
        Queryable<Topic> q3 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "123"));

        String sql = q1.union(q2, q3).toSQL();
        Assert.assertEquals("SELECT t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM ( (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` IS NOT NULL)  UNION  (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NULL)  UNION  (SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM `t_topic` t2 WHERE t2.`id` = ?) ) t3", sql);
    }

    @Test
    public void query52_1_2() {
        Queryable<Topic> q1 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isNotNull(Topic::getId));
        Queryable<Topic> q2 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isNull(Topic::getId));
        Queryable<Topic> q3 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "123"));

        String sql = q1.unionAll(q2, q3).toSQL();
        Assert.assertEquals("SELECT t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM ( (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` IS NOT NULL)  UNION ALL  (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NULL)  UNION ALL  (SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM `t_topic` t2 WHERE t2.`id` = ?) ) t3", sql);
    }

    @Test
    public void query52_2() {
        ClientQueryable<Topic> q1 = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.isNotNull("id"));
        ClientQueryable<Topic> q3 = easyQueryClient
                .queryable(Topic.class)
                .where(o -> o.eq("id", "123"));

        String sql = q1.union(q3).toSQL();
        Assert.assertEquals("SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM ( (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` IS NOT NULL)  UNION  (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` = ?) ) t2", sql);
    }

    @Test
    public void query52_3() {
        Queryable<Topic> q1 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isNotNull(Topic::getId));
        Queryable<Topic> q3 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "123"));

        String sql = q1.union(q3).toSQL();
        Assert.assertEquals("SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM ( (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` IS NOT NULL)  UNION  (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` = ?) ) t2", sql);
    }

    @Test
    public void query52_3_1() {
        Queryable<Topic> q1 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isNotNull(Topic::getId));
        Queryable<Topic> q3 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "123"));

        String sql = q1.unionAll(q3).toSQL();
        Assert.assertEquals("SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM ( (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` IS NOT NULL)  UNION ALL  (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` = ?) ) t2", sql);
    }

    @Test
    public void query53() {
        Queryable<Topic> q1 = easyQuery
                .queryable(Topic.class);
        Queryable<Topic> q2 = easyQuery
                .queryable(Topic.class);
        Queryable<Topic> q3 = easyQuery
                .queryable(Topic.class);

        String sql = q1.union(q2, q3).toSQL();
        Assert.assertEquals("SELECT t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM ( (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t)  UNION  (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1)  UNION  (SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM `t_topic` t2) ) t3", sql);
    }

    @Test
    public void query54() {
        Queryable<Topic> q1 = easyQuery
                .queryable(Topic.class);
        Queryable<Topic> q2 = easyQuery
                .queryable(Topic.class);
        Queryable<Topic> q3 = easyQuery
                .queryable(Topic.class);

        String sql = q1.union(q2, q3).where(o -> o.eq(Topic::getId, "123321")).toSQL();
        Assert.assertEquals("SELECT t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM ( (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t)  UNION  (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1)  UNION  (SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM `t_topic` t2) ) t3 WHERE t3.`id` = ?", sql);
    }

    @Test
    public void query55() {
        Queryable<Topic> q1 = easyQuery
                .queryable(Topic.class);
        Queryable<Topic> q2 = easyQuery
                .queryable(Topic.class);
        Queryable<Topic> q3 = easyQuery
                .queryable(Topic.class);

        String sql = q1.union(q2, q3).select(o -> o.column(Topic::getCreateTime).column(Topic::getStars)).toSQL();
        Assert.assertEquals("SELECT t3.`create_time`,t3.`stars` FROM ( (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t)  UNION  (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1)  UNION  (SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM `t_topic` t2) ) t3", sql);
    }

    @Test
    public void query56() {
        Queryable<Topic> q1 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isNotNull(Topic::getId));
        Queryable<Topic> q2 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.isNull(Topic::getId));
        Queryable<Topic> q3 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getId, "123"));
        Queryable<Topic> q4 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.eq(Topic::getCreateTime, "123"));

        String sql = q1.union(q2, q3).unionAll(q4).toSQL();
        Assert.assertEquals("SELECT t5.`id`,t5.`stars`,t5.`title`,t5.`create_time` FROM ( (SELECT t3.`id`,t3.`stars`,t3.`title`,t3.`create_time` FROM ( (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` IS NOT NULL)  UNION  (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`id` IS NULL)  UNION  (SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM `t_topic` t2 WHERE t2.`id` = ?) ) t3)  UNION ALL  (SELECT t4.`id`,t4.`stars`,t4.`title`,t4.`create_time` FROM `t_topic` t4 WHERE t4.`create_time` = ?) ) t5", sql);
    }

    @Test
    public void query57() {
        Queryable<Topic> q1 = easyQuery
                .queryable(Topic.class);
        Queryable<Topic> q2 = easyQuery
                .queryable(Topic.class);
        Queryable<Topic> q3 = easyQuery
                .queryable(Topic.class);
        List<Topic> list = q1.union(q2, q3).where(o -> o.eq(Topic::getId, "123321")).toList();
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void query58() {
        Queryable<String> idQueryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123"))
                .select(String.class, o -> o.column(BlogEntity::getId));
        String sql = easyQuery
                .queryable(Topic.class).where(o -> o.in(Topic::getId, idQueryable)).toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` IN (SELECT t1.`id` FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = ?)", sql);
    }

    @Test
    public void query59() {
        Queryable<String> idQueryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "123"))
                .select(String.class, o -> o.column(BlogEntity::getId));
        List<Topic> list = easyQuery
                .queryable(Topic.class).where(o -> o.in(Topic::getId, idQueryable)).toList();
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void query60() {
        Queryable<String> idQueryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "1"))
                .select(String.class, o -> o.column(BlogEntity::getId));
        List<Topic> list = easyQuery
                .queryable(Topic.class).where(o -> o.in(Topic::getId, idQueryable)).toList();
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void query61() {
        long count = easyQuery
                .queryable(Topic.class).count();
        Queryable<String> idQueryable = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "1"))
                .select(String.class, o -> o.column(BlogEntity::getId));
        List<Topic> list = easyQuery
                .queryable(Topic.class).where(o -> o.notIn(Topic::getId, idQueryable)).toList();
        Assert.assertEquals(count - 1, list.size());
    }

    @Test
    public void query62() {
        Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                .queryLargeColumn(false)
                .where(o -> o.eq(SysUser::getId, "123xxx"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ?", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }


    @Test
    public void query63() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class);
        Queryable<TopicSubQueryBlog> select = easyQuery
                .queryable(Topic.class)
                .where(t -> t.isNotNull(Topic::getTitle))
                .select(TopicSubQueryBlog.class, o -> o.columnAll().columnSubQueryAs(() -> {
                    return queryable.where(x -> x.eq(o, BlogEntity::getId, Topic::getId)).select(Long.class, x -> x.columnCount(BlogEntity::getId));
                }, TopicSubQueryBlog::getBlogCount));
        String sql = select.toSQL();

        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,(SELECT COUNT(t1.`id`) AS `id` FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AS `blog_count` FROM `t_topic` t WHERE t.`title` IS NOT NULL", sql);
        List<TopicSubQueryBlog> list = select.toList();
        Assert.assertEquals(99, list.size());
        for (TopicSubQueryBlog topicSubQueryBlog : list) {
            if (topicSubQueryBlog.getStars() < 200) {
                Assert.assertEquals(1, (long) topicSubQueryBlog.getBlogCount());
            } else {
                Assert.assertEquals(0, (long) topicSubQueryBlog.getBlogCount());
            }
        }
    }

    @Test
    public void query63_1() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class);
        Queryable<TopicSubQueryBlog> select = easyQuery
                .queryable(Topic.class)
                .where(t -> t.isNotNull(Topic::getTitle))
                .select(TopicSubQueryBlog.class, o -> o.columnAll().columnSubQueryAs(() -> {
                    return queryable.where(x -> x.eq(o, BlogEntity::getId, Topic::getId)).select(Long.class, x -> x.columnSum(BlogEntity::getStar));
                }, TopicSubQueryBlog::getBlogCount));
        String sql = select.toSQL();

        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,(SELECT SUM(t1.`star`) AS `star` FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AS `blog_count` FROM `t_topic` t WHERE t.`title` IS NOT NULL", sql);
        List<TopicSubQueryBlog> list = select.toList();
        Assert.assertEquals(99, list.size());
        for (TopicSubQueryBlog topicSubQueryBlog : list) {
            if (topicSubQueryBlog.getStars() < 200) {
                Assert.assertEquals(Long.parseLong(topicSubQueryBlog.getId()), (long) topicSubQueryBlog.getBlogCount());
            } else {
                Assert.assertNull(topicSubQueryBlog.getBlogCount());
            }
        }
    }

    @Test
    public void query64() {
        Queryable<BlogEntity> queryable = easyQuery.queryable(BlogEntity.class);
        Queryable<TopicSubQueryBlog> select = easyQuery
                .queryable(Topic.class)
                .where(t -> t.isNotNull(Topic::getTitle))
                .select(TopicSubQueryBlog.class, o -> o.columnAll().columnSubQueryAs(() -> {
                    return queryable.where(x -> x.eq(o, BlogEntity::getId, Topic::getId)).select(Long.class, x -> x.columnCount(BlogEntity::getId));
                }, TopicSubQueryBlog::getBlogCount).columnIgnore(Topic::getCreateTime));
        String sql = select.toSQL();

        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,(SELECT COUNT(t1.`id`) AS `id` FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`id` = t.`id`) AS `blog_count` FROM `t_topic` t WHERE t.`title` IS NOT NULL", sql);
        List<TopicSubQueryBlog> list = select.toList();
        Assert.assertEquals(99, list.size());
        for (TopicSubQueryBlog topicSubQueryBlog : list) {
            if (topicSubQueryBlog.getStars() < 200) {
                Assert.assertEquals(1, (long) topicSubQueryBlog.getBlogCount());
            } else {
                Assert.assertEquals(0, (long) topicSubQueryBlog.getBlogCount());
            }
        }
    }

    @Test
    public void query65() {
        Queryable<Topic> q1 = easyQuery
                .queryable(Topic.class).where(o -> o.eq(Topic::getId, "123"));
        Queryable<Topic> q2 = easyQuery
                .queryable(Topic.class).where(o -> o.ge(Topic::getCreateTime, LocalDateTime.of(2020, 1, 1, 1, 1)));
        Queryable<Topic> q3 = easyQuery
                .queryable(Topic.class).leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getContent).then(t).isNotNull(Topic::getStars));
        List<Topic> list = q1.union(q2, q3).where(o -> o.eq(Topic::getId, "123321")).toList();
        Assert.assertEquals(0, list.size());


        String sql = q1.union(q2, q3).where(o -> o.eq(Topic::getId, "123321")).toSQL();
        System.out.println(sql);
        Assert.assertEquals("SELECT t7.`id`,t7.`stars`,t7.`title`,t7.`create_time` FROM ( (SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t WHERE t.`id` = ?)  UNION  (SELECT t1.`id`,t1.`stars`,t1.`title`,t1.`create_time` FROM `t_topic` t1 WHERE t1.`create_time` >= ?)  UNION  (SELECT t2.`id`,t2.`stars`,t2.`title`,t2.`create_time` FROM `t_topic` t2 LEFT JOIN `t_blog` t3 ON t3.`deleted` = ? AND t2.`id` = t3.`id` WHERE t3.`content` IS NOT NULL AND t2.`stars` IS NOT NULL) ) t7 WHERE t7.`id` = ?", sql);
    }

    @Test
    public void query66() {
        String id = "123";
        Queryable<TopicUnion> q1 = easyQuery
                .queryable(Topic.class).where(o -> o.eq(EasyStringUtil.isNotBlank(id), Topic::getId, id)).select(TopicUnion.class);
        Queryable<TopicUnion> q2 = easyQuery
                .queryable(Topic.class)
                .where(o -> o.ge(Topic::getCreateTime, LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(TopicUnion.class);
        Queryable<TopicUnion> q3 = easyQuery
                .queryable(Topic.class).leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where((t, t1) -> t1.isNotNull(BlogEntity::getContent).then(t).isNotNull(Topic::getStars))
                .select(TopicUnion.class);
        List<TopicUnion> list = q1.union(q2, q3).where(o -> o.eq(TopicUnion::getId, "123321")).toList();
        Assert.assertEquals(0, list.size());


        String sql = q1.union(q2, q3).where(o -> o.eq(TopicUnion::getId, "123321")).toSQL();
        System.out.println(sql);
//        Assert.assertEquals("SELECT t8.`id`,t8.`stars`,t8.`title` FROM ( (SELECT t.`id`,t.`stars`,t.`title` FROM `t_topic` t WHERE t.`id` = ?)  UNION  (SELECT t8.`id`,t8.`stars`,t8.`title` FROM `t_topic` t8 WHERE t8.`create_time` >= ?)  UNION  (SELECT t8.`id`,t8.`stars`,t8.`title` FROM `t_topic` t8 LEFT JOIN `t_blog` t8 ON t8.`deleted` = ? AND t8.`id` = t8.`id` WHERE t8.`content` IS NOT NULL AND t8.`stars` IS NOT NULL) ) t8 WHERE t8.`id` = ?", sql);
//        Assert.assertEquals("SELECT t8.`id`,t8.`stars`,t8.`title` FROM ( (SELECT t.`id`,t.`stars`,t.`title` FROM `t_topic` t WHERE t.`id` = ?)  UNION  (SELECT t2.`id`,t2.`stars`,t2.`title` FROM `t_topic` t2 WHERE t2.`create_time` >= ?)  UNION  (SELECT t4.`id`,t4.`stars`,t4.`title` FROM `t_topic` t4 LEFT JOIN `t_blog` t5 ON t5.`deleted` = ? AND t4.`id` = t5.`id` WHERE t5.`content` IS NOT NULL AND t4.`stars` IS NOT NULL) ) t8 WHERE t8.`id` = ?", sql);
        Assert.assertEquals("SELECT t8.`id`,t8.`stars`,t8.`abc` AS `abc` FROM ( (SELECT t.`id`,t.`stars`,t.`title` AS `abc` FROM `t_topic` t WHERE t.`id` = ?)  UNION  (SELECT t2.`id`,t2.`stars`,t2.`title` AS `abc` FROM `t_topic` t2 WHERE t2.`create_time` >= ?)  UNION  (SELECT t4.`id`,t4.`stars`,t4.`title` AS `abc` FROM `t_topic` t4 LEFT JOIN `t_blog` t5 ON t5.`deleted` = ? AND t4.`id` = t5.`id` WHERE t5.`content` IS NOT NULL AND t4.`stars` IS NOT NULL) ) t8 WHERE t8.`id` = ?", sql);
    }

    @Test
    public void query67() {
        Queryable<TopicUnion> q1 = easyQuery
                .queryable(Topic.class).where(o -> o.eq(Topic::getId, "123")).select(TopicUnion.class);
        Queryable<TopicUnion> q2 = easyQuery
                .queryable(BlogEntity.class)
                .where(o -> o.ge(BlogEntity::getCreateTime, LocalDateTime.of(2020, 1, 1, 1, 1)))
                .select(TopicUnion.class, o -> o.columnAs(BlogEntity::getId, TopicUnion::getId)
                        .columnAs(BlogEntity::getStar, TopicUnion::getStars)
                        .columnAs(BlogEntity::getContent, TopicUnion::getTitle)
                );
        List<TopicUnion> list = q1.unionAll(q2).where(o -> o.eq(TopicUnion::getId, "123321")).toList();
        Assert.assertEquals(0, list.size());


        String sql = q1.union(q2).where(o -> o.eq(TopicUnion::getId, "123321")).toSQL();
        System.out.println(sql);
//        Assert.assertEquals("SELECT t5.`id`,t5.`stars`,t5.`title` FROM ( (SELECT t.`id`,t.`stars`,t.`title` FROM `t_topic` t WHERE t.`id` = ?)  UNION  (SELECT t2.`id` AS `id`,t2.`star` AS `stars`,t2.`content` AS `title` FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`create_time` >= ?) ) t5 WHERE t5.`id` = ?", sql);
//        Assert.assertEquals("SELECT t4.`id`,t4.`stars`,t4.`abc` AS `abc` FROM ( (SELECT t.`id`,t.`stars`,t.`title` AS `abc` FROM `t_topic` t WHERE t.`id` = ?)  UNION ALL  (SELECT t2.`id` AS `id`,t2.`star` AS `stars`,t2.`content` AS `abc` FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`create_time` >= ?) ) t4 WHERE t4.`id` = ?", sql);
        Assert.assertEquals("SELECT t5.`id`,t5.`stars`,t5.`abc` AS `abc` FROM ( (SELECT t.`id`,t.`stars`,t.`title` AS `abc` FROM `t_topic` t WHERE t.`id` = ?)  UNION  (SELECT t2.`id` AS `id`,t2.`star` AS `stars`,t2.`content` AS `abc` FROM `t_blog` t2 WHERE t2.`deleted` = ? AND t2.`create_time` >= ?) ) t5 WHERE t5.`id` = ?", sql);
    }

    @Test
    public void query68() {
        Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                .where(o -> o.eq(SysUser::getId, "123xxx"))
                .select(o -> o.columnAll().columnIgnore(SysUser::getCreateTime)
                        .columnFunc(SQLTestFunc.ifNULL(SysUser::getCreateTime))
                );
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`username`,`phone`,`id_card`,`address`,IFNULL(`create_time`,'') AS `create_time` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ?", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }

    @Test
    public void query69() {
        TopicType topicType = easyQuery.queryable(TopicType.class)
                .whereById("123").firstOrNull();
        if (topicType != null) {
            long l = easyQuery.deletable(topicType).executeRows();
            Assert.assertEquals(1, l);
        }
        topicType = easyQuery.queryable(TopicType.class)
                .whereById("123").firstOrNull();
        Assert.assertNull(topicType);
        TopicType topicType1 = new TopicType();
        topicType1.setId("123");
        topicType1.setStars(123);
        topicType1.setTitle("title123");
        topicType1.setTopicType(TopicTypeEnum.TEACHER.getCode());
        topicType1.setCreateTime(LocalDateTime.now());
        long l = easyQuery.insertable(topicType1).executeRows();
        Assert.assertEquals(1, l);

        TopicTypeVO topicTypeVO = easyQuery.queryable(TopicType.class)
                .whereById("123")
                .select(TopicTypeVO.class)
                .firstOrNull();
        System.out.println(topicTypeVO);
        Assert.assertNotNull(topicTypeVO);

        Assert.assertEquals(TopicTypeEnum.TEACHER, topicTypeVO.getTopicType());

    }

    @Test
    public void query70() {
        TopicTypeTest1 topicType = easyQuery.queryable(TopicTypeTest1.class)
                .whereById("123").firstOrNull();
        if (topicType != null) {
            long l = easyQuery.deletable(topicType).executeRows();
            Assert.assertEquals(1, l);
        }
        topicType = easyQuery.queryable(TopicTypeTest1.class)
                .whereById("123").firstOrNull();
        Assert.assertNull(topicType);
        TopicTypeTest1 topicType1 = new TopicTypeTest1();
        topicType1.setId("123");
        topicType1.setStars(123);
        topicType1.setTitle("title123");
        topicType1.setTopicType(TopicTypeEnum.CLASSER);
        topicType1.setCreateTime(LocalDateTime.now());
        long l = easyQuery.insertable(topicType1).executeRows();
        Assert.assertEquals(1, l);

        TopicTypeTest1 topicTypeVO = easyQuery.queryable(TopicTypeTest1.class)
                .whereById("123")
                .firstOrNull();
        Assert.assertNotNull(topicTypeVO);
        System.out.println(topicTypeVO);

        Assert.assertEquals(TopicTypeEnum.CLASSER, topicTypeVO.getTopicType());

    }

    @Test
    public void query70_1() {
        TopicTypeTest2 topicType = easyQuery.queryable(TopicTypeTest2.class)
                .whereById("123").firstOrNull();
        if (topicType != null) {
            long l = easyQuery.deletable(topicType).executeRows();
            Assert.assertEquals(1, l);
        }
        topicType = easyQuery.queryable(TopicTypeTest2.class)
                .whereById("123").firstOrNull();
        Assert.assertNull(topicType);
        TopicTypeTest2 topicType1 = new TopicTypeTest2();
        topicType1.setId("123");
        topicType1.setStars(123);
        topicType1.setTitle("title123");
        topicType1.setTopicType(TopicTypeEnum.CLASSER);
        topicType1.setCreateTime(LocalDateTime.now());
        long l = easyQuery.insertable(topicType1).executeRows();
        Assert.assertEquals(1, l);

        TopicTypeTest2 topicTypeVO = easyQuery.queryable(TopicTypeTest2.class)
                .whereById("123")
                .firstOrNull();
        Assert.assertNotNull(topicTypeVO);
        System.out.println(topicTypeVO);

        Assert.assertEquals(TopicTypeEnum.CLASSER, topicTypeVO.getTopicType());

    }

    @Test
    public void query71() {
        TopicTypeJson topicType = easyQuery.queryable(TopicTypeJson.class)
                .whereById("1231").firstOrNull();
        if (topicType != null) {
            long l = easyQuery.deletable(topicType).executeRows();
            Assert.assertEquals(1, l);
        }
        topicType = easyQuery.queryable(TopicTypeJson.class)
                .whereById("1231").firstOrNull();
        Assert.assertNull(topicType);
        TopicTypeJson topicType1 = new TopicTypeJson();
        topicType1.setId("1231");
        topicType1.setStars(123);
        TopicTypeJsonValue topicTypeJsonValue = new TopicTypeJsonValue();
        topicTypeJsonValue.setName("123");
        topicTypeJsonValue.setAge(456);
        topicType1.setTitle(topicTypeJsonValue);
        topicType1.setTopicType(TopicTypeEnum.CLASSER.getCode());
        topicType1.setCreateTime(LocalDateTime.now());
        long l = easyQuery.insertable(topicType1).executeRows();
        Assert.assertEquals(1, l);

        TopicTypeJson topicTypeVO = easyQuery.queryable(TopicTypeJson.class)
                .whereById("1231")
                .firstOrNull();
        Assert.assertNotNull(topicTypeVO);
        System.out.println(topicTypeVO);
        Assert.assertNotNull(topicTypeVO.getTitle());
        Assert.assertEquals("123", topicTypeVO.getTitle().getName());
        Assert.assertEquals(456, (int) topicTypeVO.getTitle().getAge());


        Assert.assertEquals(TopicTypeEnum.CLASSER.getCode(), topicTypeVO.getTopicType());

    }

    @Test
    public void query72() {
        BlogEntityVO1 blogEntityVO1 = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "2"))
                .select(BlogEntityVO1.class).firstOrNull();
        Assert.assertNotNull(blogEntityVO1);
        System.out.println(blogEntityVO1);
        String sql = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "2"))
                .select(BlogEntityVO1.class).limit(1).toSQL();
        Assert.assertEquals("SELECT t.`score`,t.`status` AS `status1`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", sql);
    }

    @Test
    public void query73() {
        BlogEntityVO1 blogEntityVO1 = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "2"))
                .select(BlogEntityVO1.class, o -> o.columnAll()).firstOrNull();
        Assert.assertNotNull(blogEntityVO1);
        System.out.println(blogEntityVO1);
        String sql = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "2"))
                .select(BlogEntityVO1.class, o -> o.columnAll()).limit(1).toSQL();
        Assert.assertEquals("SELECT t.`score`,t.`status` AS `status1`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", sql);
    }

    @Test
    public void query74() {
        BlogEntityVO1 blogEntityVO1 = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "2"))
                .select(BlogEntityVO1.class, o -> o.columnIgnore(BlogEntity::getId)).firstOrNull();
        Assert.assertNotNull(blogEntityVO1);
        System.out.println(blogEntityVO1);
        String sql = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "2"))
                .select(BlogEntityVO1.class, o -> o.columnIgnore(BlogEntity::getId)).limit(1).toSQL();
        Assert.assertEquals("SELECT t.`score`,t.`status` AS `status1`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", sql);
    }

    @Test
    public void query75() {
        BlogEntityVO1 blogEntityVO1 = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "2"))
                .select(BlogEntityVO1.class, o -> o.columnIgnore(BlogEntity::getId).columnAs(BlogEntity::getOrder, BlogEntityVO1::getScore)).firstOrNull();
        Assert.assertNotNull(blogEntityVO1);
        System.out.println(blogEntityVO1);
        String sql = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "2"))
                .select(BlogEntityVO1.class, o -> o.columnIgnore(BlogEntity::getId)
                        .columnAs(BlogEntity::getOrder, BlogEntityVO1::getScore)).limit(1).toSQL();
        Assert.assertEquals("SELECT t.`order` AS `score` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", sql);
    }

    @Test
    public void query76() {
        BlogEntityVO1 blogEntityVO1 = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "2"))
                .select(BlogEntityVO1.class, o -> o.columnAll().columnIgnore(BlogEntity::getScore)).firstOrNull();
        Assert.assertNotNull(blogEntityVO1);
        System.out.println(blogEntityVO1);
        String sql = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.eq(BlogEntity::getId, "2"))
                .select(BlogEntityVO1.class, o -> o.columnAll().columnIgnore(BlogEntity::getScore)).limit(1).toSQL();
        Assert.assertEquals("SELECT t.`status` AS `status1`,t.`order`,t.`is_top`,t.`top` FROM `t_blog` t WHERE t.`deleted` = ? AND t.`id` = ? LIMIT 1", sql);
    }

    @Test
    public void query78() {
        BlogEntityVO2 blogEntityVO1 = easyQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "2"))
                .select(BlogEntityVO2.class, (t, t1) -> t1.columnAll().columnIgnore(BlogEntity::getId).then(t).column(Topic::getId)
                        //.columnAs(Topic::getId,BlogEntityVO2::getId)//如果属性对应的columnName不一致需要as处理
                ).firstOrNull();
        Assert.assertNotNull(blogEntityVO1);
        System.out.println(blogEntityVO1);
        String sql = easyQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "2"))
                .select(BlogEntityVO2.class, (t, t1) -> t1.columnAll().columnIgnore(BlogEntity::getId).then(t).column(Topic::getId)).limit(1).toSQL();
        Assert.assertEquals("SELECT t1.`title`,t1.`content`,t1.`url`,t1.`star`,t1.`publish_time`,t1.`score`,t1.`status`,t1.`order`,t1.`is_top`,t1.`top`,t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ? LIMIT 1", sql);
    }

    @Test
    public void query79() {
        BlogEntityVO2 blogEntityVO1 = easyQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "2"))
                .select(BlogEntityVO2.class, (t, t1) -> t1.columnAll().then(t).column(Topic::getId)//如果不进行忽略两个id都查询,但是默认会把后面的覆盖掉前面的
                ).firstOrNull();
        Assert.assertNotNull(blogEntityVO1);
        System.out.println(blogEntityVO1);
        String sql = easyQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "2"))
                .select(BlogEntityVO2.class, (t, t1) -> t1.columnAll().then(t).column(Topic::getId)).limit(1).toSQL();
        Assert.assertEquals("SELECT t1.`id`,t1.`title`,t1.`content`,t1.`url`,t1.`star`,t1.`publish_time`,t1.`score`,t1.`status`,t1.`order`,t1.`is_top`,t1.`top`,t.`id` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ? LIMIT 1", sql);
    }

    @Test
    public void query80() {
        try {

            Map<String, Object> map = easyQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.eq(Topic::getId, "2"))
                    .select(BlogEntityVO2.class, (t, t1) -> t1.columnAll().then(t).column(Topic::getId)//如果不进行忽略两个id都查询,但是默认会把后面的覆盖掉前面的
                    ).toMap();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof IllegalStateException);
            Assert.assertEquals("Duplicate key found: id", ex.getMessage());
        }
    }

    @Test
    public void query81() {
        try {
            long i = easyQuery.queryable(BlogEntity.class)
                    .asTable("t_12345")
                    .where(o -> o.eq(BlogEntity::getId, "2"))
                    .select(BlogEntity.class)
                    .distinct()
                    .select(BlogEntity.class)
                    .count();
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
            Assert.assertEquals("SELECT COUNT(*) FROM (SELECT t1.`id`,t1.`create_time`,t1.`update_time`,t1.`create_by`,t1.`update_by`,t1.`deleted`,t1.`title`,t1.`content`,t1.`url`,t1.`star`,t1.`publish_time`,t1.`score`,t1.`status`,t1.`order`,t1.`is_top`,t1.`top` FROM (SELECT DISTINCT t.`id`,t.`create_time`,t.`update_time`,t.`create_by`,t.`update_by`,t.`deleted`,t.`title`,t.`content`,t.`url`,t.`star`,t.`publish_time`,t.`score`,t.`status`,t.`order`,t.`is_top`,t.`top` FROM `t_12345` t WHERE t.`deleted` = ? AND t.`id` = ?) t1) t2", sql);
        }
    }

    @Test
    public void query82() {
        Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                .where(o -> o.eq(SysUser::getId, "123xxx"))
                .orderByDesc(o -> o.column(SysUser::getId).column(SysUser::getIdCard));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ? ORDER BY `id` DESC,`id_card` DESC", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }

    @Test
    public void query83() {
        Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                .where(o -> o.eq(SysUser::getId, "123xxx"))
                .orderByAsc(o -> o.column(SysUser::getId).column(SysUser::getIdCard));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ? ORDER BY `id` ASC,`id_card` ASC", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }

    @Test
    public void query83_1() {
        Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                .where(o -> o.eq(SysUser::getId, "123xxx"))
                .orderBy(o -> o.column(SysUser::getId).column(SysUser::getIdCard), true);
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ? ORDER BY `id` ASC,`id_card` ASC", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }

    @Test
    public void query84() {
        Queryable<SysUser> queryable = easyQuery.queryable(SysUser.class)
                .where(o -> o.eq(SysUser::getId, "123xxx"))
                .orderByDesc(false, o -> o.column(SysUser::getId).column(SysUser::getIdCard))
                .orderByDesc(true, o -> o.column(SysUser::getIdCard).column(SysUser::getId));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ? ORDER BY `id_card` DESC,`id` DESC", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }

    @Test
    public void query85() {
        ClientQueryable<SysUser> queryable = easyQueryClient.queryable(SysUser.class)
                .where(o -> o.eq("id", "123xxx"))
                .orderByDesc(false, o -> o.column("id").column("idCard"))
                .orderByDesc(true, o -> o.column("idCard").column("id"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ? ORDER BY `id_card` DESC,`id` DESC", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }

    @Test
    public void query86() {
        ClientQueryable<SysUser> queryable = easyQueryClient.queryable(SysUser.class)
                .where(o -> o.eq("id", "123xxx"))
                .orderByAsc(false, o -> o.column("id").column("idCard"))
                .orderByAsc(true, o -> o.column("idCard").column("id"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ? ORDER BY `id_card` ASC,`id` ASC", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }

    @Test
    public void query87() {
        ClientQueryable<SysUser> queryable = easyQueryClient.queryable(SysUser.class)
                .where(o -> o.eq("id", "123xxx"))
                .orderByAsc(false, o -> o.column("id").column("idCard"))
                .orderByAsc(o -> o.column("idCard").column("id"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ? ORDER BY `id_card` ASC,`id` ASC", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }

    @Test
    public void query88() {
        ClientQueryable<SysUser> queryable = easyQueryClient.queryable(SysUser.class)
                .where(o -> o.eq("id", "123xxx"))
                .orderByDesc(false, o -> o.column("id").column("idCard"))
                .orderByDesc(o -> o.column("idCard").column("id"));
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ? ORDER BY `id_card` DESC,`id` DESC", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }

    @Test
    public void query88_1() {
        ClientQueryable<SysUser> queryable = easyQueryClient.queryable(SysUser.class)
                .where(o -> o.eq("id", "123xxx"))
                .orderBy(o -> o.column("idCard").column("id"), false);
        String sql = queryable.toSQL();
        Assert.assertEquals("SELECT `id`,`create_time`,`username`,`phone`,`id_card`,`address` FROM `easy-query-test`.`t_sys_user` WHERE `id` = ? ORDER BY `id_card` DESC,`id` DESC", sql);
        SysUser sysUser = queryable.firstOrNull();
        Assert.assertNull(sysUser);
    }

    @Test
    public void query89() {

        BigDecimal bigDecimal = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1", "2")))
                .sumOrDefault(BlogEntity::getScore, BigDecimal.ZERO);

        Assert.assertTrue(new BigDecimal("2.40").compareTo(bigDecimal) == 0);
        Assert.assertTrue(new BigDecimal("2.4").compareTo(bigDecimal) == 0);
    }

    @Test
    public void query89_1() {
        BigDecimal bigDecimal = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1x", "2x")))
                .sumOrDefault(BlogEntity::getScore, BigDecimal.ZERO);

        Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal) == 0);
    }

    @Test
    public void query90() {
        BigDecimal bigDecimal = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1", "2")))
                .sumBigDecimalOrNull(BlogEntity::getScore);

        Assert.assertTrue(new BigDecimal("2.40").compareTo(bigDecimal) == 0);
        Assert.assertTrue(new BigDecimal("2.4").compareTo(bigDecimal) == 0);
    }

    @Test
    public void query90_1() {
        BigDecimal bigDecimal = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1x", "2x")))
                .sumBigDecimalOrNull(BlogEntity::getScore);

        Assert.assertTrue(bigDecimal == null);
    }

    @Test
    public void query91() {
        BigDecimal bigDecimal = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1", "2")))
                .sumBigDecimalOrDefault(BlogEntity::getScore, BigDecimal.ZERO);

        Assert.assertTrue(new BigDecimal("2.40").compareTo(bigDecimal) == 0);
        Assert.assertTrue(new BigDecimal("2.4").compareTo(bigDecimal) == 0);
    }

    @Test
    public void query92() {
        Integer integer = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1", "2")))
                .maxOrDefault(BlogEntity::getStar, 0);

        Assert.assertTrue(integer == 2);
    }

    @Test
    public void query93() {
        Integer integer = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1x", "2x")))
                .maxOrDefault(BlogEntity::getStar, -1);

        Assert.assertTrue(integer == -1);
    }

    @Test
    public void query94() {
        Integer integer = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1x", "2x")))
                .maxOrNull(BlogEntity::getStar);

        Assert.assertTrue(integer == null);
    }

    @Test
    public void query95() {
        Integer integer = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1x", "9")))
                .maxOrNull(BlogEntity::getStar);

        Assert.assertTrue(integer == 9);
    }

    @Test
    public void query96() {
        Double number = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1", "2")))
                .avgOrDefault(BlogEntity::getStar, 0d);

        Assert.assertTrue(number == 1.5d);
    }

    @Test
    public void query97() {
        Double number = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1", "2")))
                .avgOrNull(BlogEntity::getStar);

        Assert.assertTrue(number == 1.5d);
    }

    @Test
    public void query98() {
        Double number = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1x", "2x")))
                .avgOrNull(BlogEntity::getStar);

        Assert.assertTrue(number == null);
    }

    @Test
    public void query99() {
        BigDecimal number = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1", "2")))
                .avgBigDecimalOrNull(BlogEntity::getStar);

        Assert.assertTrue(new BigDecimal("1.5").compareTo(number) == 0);
        Assert.assertTrue(new BigDecimal("1.5000").equals(number));
    }

    @Test
    public void query100() {
        Float number = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1", "2")))
                .avgFloatOrNull(BlogEntity::getStar);

        Assert.assertTrue(number == 1.5f);
    }

    @Test
    public void query101() {
        Double number = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1", "2")))
                .avgOrDefault(BlogEntity::getStar, 1d);

        Assert.assertTrue(number == 1.5d);
    }

    @Test
    public void query101_1() {
        Double number = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1", "2")))
                .avgOrNull(BlogEntity::getStar);

        Assert.assertTrue(number == 1.5d);
    }

    @Test
    public void query102() {
        Double number = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1x", "2x")))
                .avgOrDefault(BlogEntity::getStar, -1d);

        Assert.assertTrue(number == -1d);
    }

    @Test
    public void query102_1() {
        BigDecimal number = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1", "2")))
                .avgOrDefault(BlogEntity::getScore, BigDecimal.ZERO);

        Assert.assertTrue(new BigDecimal("1.2").compareTo(number) == 0);
    }

    @Test
    public void query102_2() {
        BigDecimal number = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1x", "2x")))
                .avgOrDefault(BlogEntity::getScore, (BigDecimal) null);

        Assert.assertTrue(number == null);
    }

    @Test
    public void query103() {
        BigDecimal number = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1x", "2x")))
                .avgOrDefault(BlogEntity::getStar, BigDecimal.ZERO, BigDecimal.class);

        Assert.assertTrue(number.compareTo(BigDecimal.ZERO) == 0);
    }

    @Test
    public void query104() {
        BigDecimal number = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("1", "2")))
                .avgOrDefault(BlogEntity::getStar, BigDecimal.ZERO, BigDecimal.class);

        Assert.assertTrue(number.compareTo(new BigDecimal("1.5")) == 0);
    }

    @Test
    public void query108() {
        Integer integer = easyQuery.queryable(BlogEntity.class)
                .where(o -> o.in(BlogEntity::getId, Arrays.asList("11", "22")))
                .sumOrNull(BlogEntity::getStar);

        Assert.assertTrue(integer == 33);
        Integer integer1 = easyQuery.queryable(BlogEntity.class)
                .whereByIds(Arrays.asList("11", "22"))
                .sumOrNull(BlogEntity::getStar);

        Assert.assertTrue(integer1 == 33);
        Integer integer2 = easyQuery.queryable(BlogEntity.class)
                .whereByIds(Arrays.asList("13", "22"))
                .sumOrNull(BlogEntity::getStar);

        Assert.assertTrue(integer2 == 35);
    }

    @Test
    public void query111() {
        String toSql = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3"))
                .limit(1, 2)
                .toSQL();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` = ? LIMIT 2 OFFSET 1", toSql);
    }

    @Test
    public void query112() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3"))
                .sumOrDefault((t, t1) -> {
                    Assert.assertNotNull(t.getTable());
                    t1.column(BlogEntity::getScore);
                }, BigDecimal.ZERO);
        Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
    }

    @Test
    public void query113() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3x"))
                .sumOrNull((t, t1) -> t1.column(BlogEntity::getScore));
        Assert.assertTrue(bigDecimal == null);
    }

    @Test
    public void query114() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3"))
                .sumBigDecimalOrNull((t, t1) -> t1.column(BlogEntity::getScore));
        Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
    }

    @Test
    public void query115() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3x"))
                .sumBigDecimalOrDefault((t, t1) -> t1.column(BlogEntity::getScore), null);
        Assert.assertTrue(bigDecimal == null);
        BigDecimal bigDecimal1 = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3x"))
                .sumBigDecimalOrDefault((t, t1) -> t1.column(BlogEntity::getScore), BigDecimal.ZERO);
        Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
    }

    @Test
    public void query116() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3x"))
                .maxOrNull((t, t1) -> t1.column(BlogEntity::getScore));
        Assert.assertTrue(bigDecimal == null);
        BigDecimal bigDecimal1 = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3x"))
                .maxOrDefault((t, t1) -> t1.column(BlogEntity::getScore), BigDecimal.ZERO);
        Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
    }

    @Test
    public void query117() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.in(Topic::getId, Arrays.asList("3x", "3")))
                .maxOrNull((t, t1) -> t1.column(BlogEntity::getScore));
        Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
    }

    @Test
    public void query118() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3x"))
                .minOrNull((t, t1) -> t1.column(BlogEntity::getScore));
        Assert.assertTrue(bigDecimal == null);
        BigDecimal bigDecimal1 = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.eq(Topic::getId, "3x"))
                .minOrDefault((t, t1) -> t1.column(BlogEntity::getScore), BigDecimal.ZERO);
        Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal1) == 0);
    }

    @Test
    public void query119() {
        BigDecimal bigDecimal = easyQuery
                .queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                .where(o -> o.in(Topic::getId, Arrays.asList("3x", "3")))
                .minOrNull((t, t1) -> t1.column(BlogEntity::getScore));
        Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
    }

    @Test
    public void query120() {
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .avgBigDecimalOrNull((t, t1) -> t1.column(BlogEntity::getScore));
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5x")))
                    .avgBigDecimalOrNull((t, t1) -> t1.column(BlogEntity::getScore));
            Assert.assertTrue(bigDecimal == null);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5")))
                    .avgBigDecimalOrDefault((t, t1) -> t1.column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(new BigDecimal("1.2").compareTo(bigDecimal) == 0);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5x")))
                    .avgBigDecimalOrDefault((t, t1) -> t1.column(BlogEntity::getScore), BigDecimal.ZERO);
            Assert.assertTrue(BigDecimal.ZERO.compareTo(bigDecimal) == 0);
        }
        {
            Double bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .avgOrNull((t, t1) -> t1.column(BlogEntity::getStar));
            Assert.assertTrue(3.3333d == bigDecimal);
        }
        {
            Double bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5x")))
                    .avgOrDefault((t, t1) -> t1.column(BlogEntity::getStar), null);
            Assert.assertTrue(null == bigDecimal);
        }
        {
            Double bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5")))
                    .avgOrDefault((t, t1) -> t1.column(BlogEntity::getStar), null);
            Assert.assertTrue(5d == bigDecimal);
        }
        {
            BigDecimal bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5")))
                    .avgOrDefault((t, t1) -> t1.column(BlogEntity::getStar), null, BigDecimal.class);
            Assert.assertTrue(new BigDecimal("5").compareTo(bigDecimal) == 0);
        }
        {
            Float bigDecimal = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3x", "2x", "5")))
                    .avgOrDefault((t, t1) -> t1.column(BlogEntity::getStar), null, Float.class);
            Assert.assertTrue(5f == bigDecimal);
        }
    }

    @Test
    public void query121() {
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .orderByAsc((t, t1) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.sqlNativeSegment("{0} IN ({1})", c -> {
                        c.expression(Topic::getId).collection(Arrays.asList("3", "2", "5"));
                    }))
                    .orderByAsc((t, t1) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) ORDER BY t1.`order` ASC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .orderByAsc(false, (t, t1) -> t1.column(BlogEntity::getOrder))
                    .orderByAsc(true, (t, t1) -> t1.column(BlogEntity::getId)).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) ORDER BY t1.`id` ASC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .orderByDesc((t, t1) -> t1.column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) ORDER BY t1.`order` DESC", sql);
        }
        {
            String sql = easyQuery
                    .queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, t1) -> t.eq(t1, Topic::getId, BlogEntity::getId))
                    .where(o -> o.in(Topic::getId, Arrays.asList("3", "2", "5")))
                    .orderByDesc(false, (t, t1) -> t1.column(BlogEntity::getOrder))
                    .orderByDesc(true, (t, t1) -> t1.column(BlogEntity::getId).column(BlogEntity::getOrder)).toSQL();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` WHERE t.`id` IN (?,?,?) ORDER BY t1.`id` DESC,t1.`order` DESC", sql);
        }
    }


}
