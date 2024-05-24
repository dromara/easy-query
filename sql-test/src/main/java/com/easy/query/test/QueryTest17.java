package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api4j.func.LambdaSQLFunc;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLConstantExpression;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.kingbase.es.config.KingbaseESDatabaseConfiguration;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicTypeTest1;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.enums.TopicTypeEnum;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.nop.MyObject;
import com.easy.query.test.nop.OtherTable;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create time 2024/5/6 17:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest17 extends BaseTest {
    @Test
    public void test1() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> {
                    b.id().nullOrDefault("123").eq("123");
                    b.id().subString(1, 20).eq("456");
                    b.expression().concat(x -> x.expression(b.content()).value("123").expression(b.id())).eq("789");
                    b.content().toUpper().eq("abc");
                    b.content().toLower().eq("def");
                    b.content().trim().eq("a");
                    b.content().trimStart().eq("b");
                    b.content().trimEnd().eq("c");
                    b.content().replace("123", "456").eq("aaa");
                    b.content().leftPad(2, 'a').eq("aa");
                    b.content().rightPad(2, 'a').eq("aa");
                    b.content().length().eq(1);
                    b.content().compareTo("aaaa").ge(0);
                }).toList();

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND IFNULL(`id`,?) = ? AND SUBSTR(`id`,2,20) = ? AND CONCAT(`content`,?,`id`) = ? AND UPPER(`content`) = ? AND LOWER(`content`) = ? AND TRIM(`content`) = ? AND LTRIM(`content`) = ? AND RTRIM(`content`) = ? AND REPLACE(`content`,?,?) = ? AND LPAD(`content`, 2, ?) = ? AND RPAD(`content`, 2, ?) = ? AND CHAR_LENGTH(`content`) = ? AND STRCMP(`content`,?) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),123(String),456(String),123(String),789(String),abc(String),def(String),a(String),b(String),c(String),123(String),456(String),aaa(String),a(String),aa(String),a(String),aa(String),1(Integer),aaaa(String),0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);


        List<BlogEntity> list = easyQuery.queryable(BlogEntity.class)
                .where(b -> {
                    LambdaSQLFunc<BlogEntity> fx = b.fx();
                    SQLFunction nullOrDefault = fx.nullOrDefault(BlogEntity::getId, "123");
                    b.eq(nullOrDefault, "123");

                    SQLFunction subString = fx.subString(BlogEntity::getId, 1, 20);
                    b.eq(subString, "456");

                    SQLFunction concat = fx.concat(x -> x.column(BlogEntity::getContent).value("123").column(BlogEntity::getId));
                    b.eq(concat, "789");

                    SQLFunction upper = fx.toUpper(BlogEntity::getContent);
                    b.eq(upper, "abc");
                    SQLFunction lower = fx.toLower(BlogEntity::getContent);
                    b.eq(lower, "def");

                    SQLFunction trim = fx.trim(BlogEntity::getContent);
                    b.eq(trim, "a");

                    SQLFunction trimStart = fx.trimStart(BlogEntity::getContent);
                    b.eq(trimStart, "b");
                    SQLFunction trimEnd = fx.trimEnd(BlogEntity::getContent);
                    b.eq(trimEnd, "c");

                    SQLFunction replace = fx.replace(BlogEntity::getContent, "123", "456");
                    b.eq(replace, "aaa");

                    SQLFunction leftPad = fx.leftPad(BlogEntity::getContent, 2, 'a');
                    b.eq(leftPad, "aa");

                    SQLFunction rightPad = fx.rightPad(BlogEntity::getContent, 2, 'a');
                    b.eq(rightPad, "aa");

                    SQLFunction length = fx.length(BlogEntity::getContent);
                    b.eq(length, 1);

                    SQLFunction stringCompareTo = fx.stringCompareTo(BlogEntity::getContent, "aaaa");
                    b.ge(stringCompareTo, 0);
                }).toList();

        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND IFNULL(`id`,?) = ? AND SUBSTR(`id`,2,20) = ? AND CONCAT(`content`,?,`id`) = ? AND UPPER(`content`) = ? AND LOWER(`content`) = ? AND TRIM(`content`) = ? AND LTRIM(`content`) = ? AND RTRIM(`content`) = ? AND REPLACE(`content`,?,?) = ? AND LPAD(`content`, 2, ?) = ? AND RPAD(`content`, 2, ?) = ? AND CHAR_LENGTH(`content`) = ? AND STRCMP(`content`,?) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),123(String),456(String),123(String),789(String),abc(String),def(String),a(String),b(String),c(String),123(String),456(String),aaa(String),a(String),aa(String),a(String),aa(String),1(Integer),aaaa(String),0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }

    @Test
    public void test3() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<BlogEntity> list = easyQueryClient.queryable(BlogEntity.class)
                .where(b -> {
                    SQLFunc fx = b.fx();
                    SQLFunction nullOrDefault = fx.nullOrDefault("id", "123");
                    b.eq(nullOrDefault, "123");

                    SQLFunction subString = fx.subString("id", 1, 20);
                    b.eq(subString, "456");

                    SQLFunction concat = fx.concat(x -> x.column("content").value("123").column("id"));
                    b.eq(concat, "789");

                    SQLFunction upper = fx.toUpper("content");
                    b.eq(upper, "abc");
                    SQLFunction lower = fx.toLower("content");
                    b.eq(lower, "def");

                    SQLFunction trim = fx.trim("content");
                    b.eq(trim, "a");

                    SQLFunction trimStart = fx.trimStart("content");
                    b.eq(trimStart, "b");
                    SQLFunction trimEnd = fx.trimEnd("content");
                    b.eq(trimEnd, "c");

                    SQLFunction replace = fx.replace("content", "123", "456");
                    b.eq(replace, "aaa");

                    SQLFunction leftPad = fx.leftPad("content", 2, 'a');
                    b.eq(leftPad, "aa");

                    SQLFunction rightPad = fx.rightPad("content", 2, 'a');
                    b.eq(rightPad, "aa");

                    SQLFunction length = fx.length("content");
                    b.eq(length, 1);

                    SQLFunction stringCompareTo = fx.stringCompareTo("content", "aaaa");
                    b.ge(stringCompareTo, 0);
                }).toList();
        listenerContextManager.clear();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND IFNULL(`id`,?) = ? AND SUBSTR(`id`,2,20) = ? AND CONCAT(`content`,?,`id`) = ? AND UPPER(`content`) = ? AND LOWER(`content`) = ? AND TRIM(`content`) = ? AND LTRIM(`content`) = ? AND RTRIM(`content`) = ? AND REPLACE(`content`,?,?) = ? AND LPAD(`content`, 2, ?) = ? AND RPAD(`content`, 2, ?) = ? AND CHAR_LENGTH(`content`) = ? AND STRCMP(`content`,?) >= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),123(String),123(String),456(String),123(String),789(String),abc(String),def(String),a(String),b(String),c(String),123(String),456(String),aaa(String),a(String),aa(String),a(String),aa(String),1(Integer),aaaa(String),0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
    }
//
//    @Test
//    public  void testxxxx(){
////        String sql = easyQueryClient.queryable(Topic.class).from(BlogEntity.class)
////                .where(t -> {
////                    t.eq("id", 123);
////                    t.ne("title", (Object) null);
////                }).toSQL();
////        System.out.println(sql);
//
////        String sql1 = easyQueryClient.queryable(Topic.class)
////                .select(Topic.class, t -> t.column("id").column("title"))
////                .where(t -> t.eq("title", "123"))
////                .toSQL();
////
////        System.out.println(sql1);
//
//        ClientQueryable<Topic> select = easyQueryClient.queryable(Topic.class)
//                .select(Topic.class, t -> t.column("id").column("title"));
//
//        String sql = select.from(BlogEntity.class)
//                .toSQL();
//
//        System.out.println(sql);
//        List<Map<String, Object>> list = easyQueryClient.mapQueryable("select * from t_topic")
//                .toList();
//        List<Map<String, Object>> list1 = easyQueryClient.mapQueryable()
//                .asTable("table1")
//                .join(MultiTableTypeEnum.LEFT_JOIN, easyQueryClient.mapQueryable("select * from t_topic"), on -> {
//                    WherePredicate<?> wherePredicate0 = on.getWherePredicate(0);
//                    WherePredicate<?> wherePredicate1 = on.getWherePredicate(1);
//
//                    wherePredicate0.eq(wherePredicate1, "id", "id");
//                })
//                .toList();
//    }

    //
    @Test
    public void testNopQuery() {
////         select v.f1,sum(v.f2), count(u.f3)
////         from (select o.f1, o.f2 from MyObject o left join o.parent
////                 where o.parent.children.myChild.name > 3 ) v,
////                 OtherTable u
////         where v.f1 = u.type group by u.type
//
//        //myObject oneToOne parent
//        //parent oneToMany children
//        //children onToMany myChild
//
//        //查询object的父级下的children中存在myChild里面name是大于3的
//
//
//
//        List<MyObject> list = easyEntityQuery.queryable(MyObject.class)
//                .where(m -> {
//                    m.parent().children().flatElement().name().gt("3");
//                }).toList();
//
//        List<MyObject> list1 = easyEntityQuery.queryable(MyObject.class)
//                .where(m -> {
//                    m.parent().children().where(o->{
//                        o.name().gt("3");
//                    }).any();
//                }).toList();
//
//
//
//
//        List<Draft3<String, Number, Long>> list2 = easyEntityQuery.queryable(MyObject.class)
//                .where(m -> {
//                    m.parent().children()
//                            .where(children -> {
//                                children.myChildren().where(myChild -> {
//                                    myChild.name().gt("3");
//                                }).any();
//                            }).any();
//                }).select(m -> Select.DRAFT.of(m.f1(), m.f1()))
//                .innerJoin(OtherTable.class, (a, b) -> a.value1().eq(b.type()))
//                .groupBy((a, b) -> GroupKeys.TABLE2.of(a.value1()))
//                .select(group -> Select.DRAFT.of(
//                        group.key1(),
//                        group.groupTable().t1.value2().sum(),
//                        group.groupTable().t2.type().count()
//                )).toList();
    }


    @Test
    public void testFlatElement1() {
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                List<MyObject> list1 = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {
                            m.parent().children().where(o -> {
                                o.name().gt("3");
                            }).any();
                        }).toList();
            } catch (Exception ignored) {

            }

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`f1`,t.`f2`,t.`parent_id` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND t2.`name` > ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            try {

                List<MyObject> list = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {
                            m.parent().children().flatElement().name().gt("3");
                        }).toList();
            } catch (Exception ignored) {

            }
            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`f1`,t.`f2`,t.`parent_id` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND t2.`name` > ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
    }

    @Test
    public void testFlatElement2() {
//
//
//
//        List<MyObject> list2 = easyEntityQuery.queryable(MyObject.class)
//                .where(m -> {
//                    m.parent().children()
//                            .where(children -> {
//                                children.myChildren().where(myChild -> {
//                                    myChild.name().gt("3");
//                                }).any();
//                            }).any();
//                }).toList();
//
//
//        List<MyObject> list1 = easyEntityQuery.queryable(MyObject.class)
//                .where(m -> {
//                    m.parent().children().flatElement().myChildren().flatElement().name().gt("3");
//                }).toList();


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                List<MyObject> list1 = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {
                            m.parent().children()
                                    .where(children -> {
                                        children.myChildren().where(myChild -> {
                                            myChild.name().gt("3");
                                        }).any();
                                    }).any();
                        }).toList();
            } catch (Exception ignored) {

            }
            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`f1`,t.`f2`,t.`parent_id` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND EXISTS (SELECT 1 FROM `MyChild` t3 WHERE t3.`children_parent_id` = t2.`id` AND t3.`name` > ? LIMIT 1) LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            try {

                List<MyObject> list = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {
                            m.parent().children().flatElement().myChildren().flatElement().name().gt("3");
                        }).toList();
            } catch (Exception ignored) {

            }
            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`f1`,t.`f2`,t.`parent_id` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND EXISTS (SELECT 1 FROM `MyChild` t3 WHERE t3.`children_parent_id` = t2.`id` AND t3.`name` > ? LIMIT 1) LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            try {

                List<MyObject> list = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {
                            m.parent().children().where(children -> {
                                children.myChildren().flatElement().name().gt("3");
                            }).any();
                        }).toList();
            } catch (Exception ignored) {

            }
            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`f1`,t.`f2`,t.`parent_id` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND EXISTS (SELECT 1 FROM `MyChild` t3 WHERE t3.`children_parent_id` = t2.`id` AND t3.`name` > ? LIMIT 1) LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
    }

    @Test
    public void testNop2() {
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            try {
                List<Draft3<String, Number, Long>> list2 = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {
                            m.parent().children()
                                    .where(children -> {
                                        children.myChildren().where(myChild -> {
                                            myChild.name().gt("3");
                                        }).any();
                                    }).any();
                        }).select(m -> Select.DRAFT.of(m.f1(), m.f1()))
                        .innerJoin(OtherTable.class, (a, b) -> a.value1().eq(b.type()))
                        .groupBy((a, b) -> GroupKeys.TABLE2.of(a.value1()))
                        .select(group -> Select.DRAFT.of(
                                group.key1(),
                                group.groupTable().t1.value2().sum(),
                                group.groupTable().t2.type().count()
                        )).toList();
            } catch (Exception ignored) {

            }
            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t4.`value1` AS `value1`,SUM(t4.`value2`) AS `value2`,COUNT(t5.`type`) AS `value3` FROM (SELECT t.`f1` AS `value1`,t.`f1` AS `value2` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND EXISTS (SELECT 1 FROM `MyChild` t3 WHERE t3.`children_parent_id` = t2.`id` AND t3.`name` > ? LIMIT 1) LIMIT 1)) t4 INNER JOIN `OtherTable` t5 ON t4.`value1` = t5.`type` GROUP BY t4.`value1`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            try {
                List<Draft3<String, Number, Long>> list2 = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {

                            m.parent().children().flatElement().myChildren().flatElement().name().gt("3");

                        }).select(m -> Select.DRAFT.of(m.f1(), m.f1()))
                        .innerJoin(OtherTable.class, (a, b) -> a.value1().eq(b.type()))
                        .groupBy((a, b) -> GroupKeys.TABLE2.of(a.value1()))
                        .select(group -> Select.DRAFT.of(
                                group.key1(),
                                group.groupTable().t1.value2().sum(),
                                group.groupTable().t2.type().count()
                        )).toList();
            } catch (Exception ignored) {

            }
            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t4.`value1` AS `value1`,SUM(t4.`value2`) AS `value2`,COUNT(t5.`type`) AS `value3` FROM (SELECT t.`f1` AS `value1`,t.`f1` AS `value2` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND EXISTS (SELECT 1 FROM `MyChild` t3 WHERE t3.`children_parent_id` = t2.`id` AND t3.`name` > ? LIMIT 1) LIMIT 1)) t4 INNER JOIN `OtherTable` t5 ON t4.`value1` = t5.`type` GROUP BY t4.`value1`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
    }

    @Test
    public void testFlatElement5() {
//
//
//        List<MyObject> list1 = easyEntityQuery.queryable(MyObject.class)
//                .where(m -> {
//                    m.parent().children().where(o->{
//                        o.name().gt("3");
//                        o.id().eq("123");
//                    }).any();
//                }).toList();
//
//
//
//        List<MyObject> list = easyEntityQuery.queryable(MyObject.class)
//                .where(m -> {
//                    m.parent().children().flatElement(c->{
//                        c.name().gt("3");
//                        c.id().eq("123");
//                    });
//                }).toList();
//
//
//        List<MyObject> list2 = easyEntityQuery.queryable(MyObject.class)
//                .where(m -> {
//                    m.parent().children().flatElement().myChildren().flatElement().name().gt("3");
//                }).toList();
//        {
//
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//
//
//            try {
//
//                List<MyObject> list = easyEntityQuery.queryable(MyObject.class)
//                        .where(m -> {
//                            m.parent().children().flatElement(c->{
//                                c.name().gt("3");
//                                c.id().eq("123");
//                            });
//                        }).toList();
//            }catch (Exception ignored){
//
//            }
//            listenerContextManager.clear();
//
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`id`,t.`f1`,t.`f2`,t.`parent_id` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND t2.`name` > ? AND t2.`id` = ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("3(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//
//        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                List<MyObject> list1 = easyEntityQuery.queryable(MyObject.class)
                        .where(m -> {
                            m.parent().children().where(o -> {
                                o.name().gt("3");
                                o.id().eq("123");
                            }).any();
                        }).toList();
            } catch (Exception ignored) {

            }

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`f1`,t.`f2`,t.`parent_id` FROM `MyObject` t LEFT JOIN `MyObjectParent` t1 ON t1.`id` = t.`parent_id` WHERE EXISTS (SELECT 1 FROM `MyObjectParentChildren` t2 WHERE t2.`parent_id` = t1.`id` AND t2.`name` > ? AND t2.`id` = ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
    }

    @Test
    public void test12234567() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/easy-query-test?serverTimezone=GMT%2B8&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&rewriteBatchedStatements=true");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setMaximumPoolSize(20);

        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setExecutorCorePoolSize(1);
                    op.setExecutorMaximumPoolSize(0);
                    op.setMaxShardingQueryLimit(10);
                    op.setShardingOption(easyQueryShardingOption);
                    op.setDefaultDataSourceName("ds2020");
                    op.setThrowIfRouteNotMatch(false);
                    op.setMaxShardingRouteCount(512);
                    op.setDefaultDataSourceMergePoolSize(20);
                    op.setStartTimeJob(true);
                    op.setReverseOffsetThreshold(10);
                })
                .useDatabaseConfigure(new KingbaseESDatabaseConfiguration())
//                .replaceService(BeanValueCaller.class, ReflectBeanValueCaller.class)
                .build();
        DefaultEasyEntityQuery defaultEasyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);


        String sql = defaultEasyEntityQuery.queryable(Topic.class)
                .where(m -> {
                    m.createTime().format("yyyy年MM月dd日").eq("2022年01月01日");
                    Expression expression = m.expression();
                    expression.concat(x -> x.expression(m.id()).format("':'")).eq("123");
                }).toSQL();
        Assert.assertEquals("SELECT \"id\",\"stars\",\"title\",\"create_time\" FROM \"t_topic\" WHERE to_char((\"create_time\")::TIMESTAMP,'YYYY年MM月DD日') = ? AND CONCAT(\"id\",':') = ?", sql);
    }

    @Test
    public void aaaa() {
        List<Draft1<LocalDateTime>> list = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    t.createTime().plus(1, TimeUnit.DAYS).lt(LocalDateTime.now());
                }).select(t -> Select.DRAFT.of(
                        t.createTime().plus(1, TimeUnit.DAYS)
                )).toList();

        List<Draft1<LocalDateTime>> list1 = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    Expression expression = t.expression();
                    expression.sql("({0} + interval 1 day) < {1}", c -> {
                        c.expression(t.createTime()).value(LocalDateTime.now());
                    });

                }).select(t -> Select.DRAFT.of(
                        t.expression().sqlType("({0} + interval 1 day)", c -> {
                            c.expression(t.createTime());
                        }).setPropertyType(LocalDateTime.class)
                )).toList();

    }


    @Test
    public void test11() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .where(t -> {
                        Filter filter = t.getEntitySQLContext().getFilter();
                        filter.eq(t.getTable(), "title", "123");
                    })
                    .select(t -> {
                        TopicProxy topicProxy = new TopicProxy();
                        topicProxy.selectColumn(t,"id");
                        topicProxy.selectColumn(t,"title");
                        return topicProxy;
                    }).toList();


            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`title` FROM `t_topic` t WHERE t.`title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
                    .where(t -> {
                        t.title().like("456");
                    })
                    .select(Topic.class, x -> x.FETCHER.allFields()._concat(x.stars())).toList();


            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,t.`stars` FROM `t_topic` t WHERE t.`title` LIKE ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("%456%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
    }

    @Test
     public void testxxx9(){
        Class<Topic> topicClass = Topic.class;
        TopicProxy topicProxy = EntityQueryProxyManager.create(topicClass);
        Assert.assertNotNull(topicProxy);
        System.out.println(topicProxy);

        List<Draft1<TopicTypeEnum>> list = easyEntityQuery.queryable(TopicTypeTest1.class)
                .select(t -> Select.DRAFT.of(t.topicType())).toList();
        for (Draft1<TopicTypeEnum> topicTypeEnumDraft1 : list) {
            TopicTypeEnum value1 = topicTypeEnumDraft1.getValue1();
            System.out.println(value1);
        }
    }


    @Test
    public void textLikeFunc(){


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
                    .where(b -> {
                        SQLConstantExpression constant = b.expression().constant();
                        b.title().like(constant.valueOf("ABc").toLower());
                    }).toList();

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`create_time`,`update_time`,`create_by`,`update_by`,`deleted`,`title`,`content`,`url`,`star`,`publish_time`,`score`,`status`,`order`,`is_top`,`top` FROM `t_blog` WHERE `deleted` = ? AND `title` LIKE CONCAT('%',LOWER(?),'%')", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("false(Boolean),ABc(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
    }
}
