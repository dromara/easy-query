package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.api.proxy.util.EasyProxyUtil;
import com.easy.query.api4j.func.LambdaSQLFunc;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.nameconversion.NameConversion;
import com.easy.query.core.context.EmptyQueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryInvalidFieldCheckException;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.parser.core.available.EmptyTableAvailable;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.core.expression.sql.builder.EmptyEntityExpressionBuilder;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.SQLConstantExpression;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.core.draft.Draft1;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.core.draft.Draft3;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasyFieldCheckUtil;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.kingbase.es.config.KingbaseESDatabaseConfiguration;
import com.easy.query.test.dto.autodto.SchoolClassAOProp3;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MyTopic;
import com.easy.query.test.entity.MyTopic4;
import com.easy.query.test.entity.MyTopic5;
import com.easy.query.test.entity.MyTopicx;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicTypeTest1;
import com.easy.query.test.entity.base.Province;
import com.easy.query.test.entity.base.ProvinceVO;
import com.easy.query.test.entity.base.proxy.ProvinceVOProxy;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.entity.proxy.SysUserProxy;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.entity.school.proxy.SchoolClassProxy;
import com.easy.query.test.entity.school.proxy.SchoolStudentAddressProxy;
import com.easy.query.test.entity.school.proxy.SchoolStudentProxy;
import com.easy.query.test.entity.school.proxy.SchoolTeacherProxy;
import com.easy.query.test.enums.TopicTypeEnum;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.nop.MyObject;
import com.easy.query.test.nop.OtherTable;
import com.zaxxer.hikari.HikariDataSource;
import lombok.val;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    /// /        String sql1 = easyQueryClient.queryable(Topic.class)
    /// /                .select(Topic.class, t -> t.column("id").column("title"))
    /// /                .where(t -> t.eq("title", "123"))
    /// /                .toSQL();
    /// /
    /// /        System.out.println(sql1);
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
//                .groupBy((a, b) -> GroupKeys.of(a.value1()))
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
                        .groupBy((a, b) -> GroupKeys.of(a.value1()))
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
                        .groupBy((a, b) -> GroupKeys.of(a.value1()))
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
        Assert.assertEquals("SELECT \"id\",\"stars\",\"title\",\"create_time\" FROM \"t_topic\" WHERE TO_CHAR((\"create_time\")::TIMESTAMP,'YYYY年MM月DD日') = ? AND CONCAT(\"id\",':') = ?", sql);
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
                        }).asAnyType(LocalDateTime.class)
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
                        topicProxy.selectColumn(t, "id");
                        topicProxy.selectColumn(t, "title");
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
    public void testxxx9() {
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
    public void textLikeFunc() {


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

//    @Test
//    public void testxxxxa(){
////        LocalDateTime begin = LocalDateTime.now();
////        LocalDateTime end = LocalDateTime.now();
////        BlogEntity blogEntity = null;
////
////        List<BlogEntity> list = easyEntityQuery.queryable(BlogEntity.class)
////                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
////                .where(blogEntity!=null,b -> {
////                    b.content().like(blogEntity.getContent());
////                }).toList();
////
////        List<BlogEntityTitleOnly> list1 = easyEntityQuery.queryable(BlogEntity.class)
////                .where(b -> {
////                    b.content().like("123");
////                })
////                .select(b -> {
////                    BlogEntityTitleOnlyProxy r = new BlogEntityTitleOnlyProxy();
////                    r.title().set(b.title());
////                    return r;
////                })
////                .where(b -> {
////                    b.title().like("4556");
////                }).toList();
//
//

    /// /        List<Draft1<String>> list = easyEntityQuery.queryable(BlogEntity.class)
    /// /                .where(b -> {
    /// /                    b.content().like("123");
    /// /                })
    /// /                .select(b -> Select.DRAFT.of(
    /// /                        b.title()
    /// /                ))
    /// /                .where(s -> {
    /// /                    s.value1().like("4556");
    /// /                }).toList();
    /// /
    /// /        List<Draft2<String, String>> list1 = easyEntityQuery.queryable(BlogEntity.class)
    /// /                .where(b -> {
    /// /                    b.content().like("123");
    /// /                })
    /// /                .select(b -> Select.DRAFT.of(
    /// /                        b.title(),
    /// /                        b.content()
    /// /                ))
    /// /                .where(s -> {
    /// /                    s.value1().like("4556");
    /// /                }).toList();
//
//        Draft3<String, Integer, BigDecimal> result = easyEntityQuery.queryable(BlogEntity.class)
//                .where(b -> {
//                    b.content().like("123");
//                })
//                .groupBy(b -> GroupKeys.of(
//                        b.content(),
//                        b.star()
//                ))
//                .select(group -> Select.DRAFT.of(
//                        group.key1(),
//                        group.key2(),
//                        group.groupTable().score().sum(BigDecimal.class)
//                )).firstOrNull();
//        String groupKey1 = result.getValue1();
//        Integer groupKey2 = result.getValue2();
//        BigDecimal scoreSum = result.getValue3();
//    }
    @Test
    public void xxx() {
        List<MyTopic> list = easyEntityQuery.queryable(MyTopic.class)
                .where(m -> m.id().eq("123"))
                .toList();


        List<MyTopicx> list1 = easyEntityQuery.queryable(MyTopicx.class)
                .where(m -> m.id().eq("123"))
                .toList();
        List<MyTopic4> list2 = easyEntityQuery.queryable(MyTopic4.class)
                .where(m -> m.id().eq("123"))
                .toList();
        List<MyTopic5> list3 = easyEntityQuery.queryable(MyTopic5.class)
                .where(m -> m.id().eq("123"))
                .toList();
    }


    //    @Test
//    public void multiFrom1(){
//
//        {
//
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//
//            List<Topic> list = easyEntityQuery.queryable(Topic.class)
//                    .from(BlogEntity.class, SysUser.class)
//                    .where((t1, b2, s3) -> {
//                        t1.id().eq(b2.id());
//                        b2.content().eq(s3.address());
//                    }).toList();
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("SELECT t.`id`,t.`name`,t.`create_time` FROM `t_company` t WHERE IFNULL((SELECT AVG(t1.`age`) FROM `t_user` t1 WHERE t1.`company_id` = t.`id`),0) > ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("18(BigDecimal)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//        }
//    }
    @Test
    public void testNativeSQL() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .where(b -> {
                        b.id().eq("123");
                    }).orderBy(t -> {
                        t.expression().sql("RAND()");
                    }).toList();

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ? ORDER BY RAND()", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyQuery.queryable(Topic.class)
                    .where(b -> {
                        b.eq(Topic::getId, "123");
                    }).orderByAsc(t -> {
                        t.sqlNativeSegment("RAND()");
                    }).toList();

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ? ORDER BY RAND()", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyQueryClient.queryable(Topic.class)
                    .where(b -> {
                        b.eq("id", "123");
                    }).orderByAsc(t -> {
                        t.sqlNativeSegment("RAND()");
                    }).toList();

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ? ORDER BY RAND()", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
    }

    @Test
    public void testNativeSQL1() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .where(b -> {
                        b.id().eq("123");
                    }).orderBy(t -> {
                        t.expression().sql("IFNULL({0},{1}) DESC", c -> {
                            c.expression(t.stars()).value(1);
                        });
                        t.expression().sql("RAND()");
                    }).toList();

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ? ORDER BY IFNULL(`stars`,?) DESC,RAND()", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyQuery.queryable(Topic.class)
                    .where(b -> {
                        b.eq(Topic::getId, "123");
                    }).orderByAsc(t -> {
                        t.sqlNativeSegment("IFNULL({0},{1}) DESC", c -> {
                            c.expression(Topic::getStars).value(1);
                        });
                        t.sqlNativeSegment("RAND()");
                    }).toList();

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ? ORDER BY IFNULL(`stars`,?) DESC,RAND()", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyQueryClient.queryable(Topic.class)
                    .where(b -> {
                        b.eq("id", "123");
                    }).orderByAsc(t -> {
                        t.sqlNativeSegment("IFNULL({0},{1}) DESC", c -> {
                            c.expression("stars").value(1);
                        });
                        t.sqlNativeSegment("RAND()");
                    }).toList();

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ? ORDER BY IFNULL(`stars`,?) DESC,RAND()", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
    }

    @Test
    public void testNativeSQL2() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .where(b -> {
                        b.id().eq("123");
                        b.expression().sql("{0}!={1}", c -> {
                            c.expression(b.stars()).expression(b.createTime());
                        });
                    }).orderBy(t -> {
                        t.expression().sql("IFNULL({0},{1}) DESC", c -> {
                            c.expression(t.stars()).value(1);
                        });
                        t.expression().sql("RAND()");
                    }).toList();

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ? AND `stars`!=`create_time` ORDER BY IFNULL(`stars`,?) DESC,RAND()", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyQuery.queryable(Topic.class)
                    .where(b -> {
                        b.eq(Topic::getId, "123");
                        b.sqlNativeSegment("{0}!={1}", c -> {
                            c.expression(Topic::getStars).expression(Topic::getCreateTime);
                        });
                    }).orderByAsc(t -> {
                        t.sqlNativeSegment("IFNULL({0},{1}) DESC", c -> {
                            c.expression(Topic::getStars).value(1);
                        });
                        t.sqlNativeSegment("RAND()");
                    }).toList();

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ? AND `stars`!=`create_time` ORDER BY IFNULL(`stars`,?) DESC,RAND()", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyQueryClient.queryable(Topic.class)
                    .where(b -> {
                        b.eq("id", "123");
                        b.sqlNativeSegment("{0}!={1}", c -> {
                            c.expression("stars").expression("createTime");
                        });
                    }).orderByAsc(t -> {
                        t.sqlNativeSegment("IFNULL({0},{1}) DESC", c -> {
                            c.expression("stars").value(1);
                        });
                        t.sqlNativeSegment("RAND()");
                    }).toList();

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` = ? AND `stars`!=`create_time` ORDER BY IFNULL(`stars`,?) DESC,RAND()", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
    }

    @Test
    public void testNativeSQL3() {

//        easyEntityQuery.queryable(BlogEntity.class)
//                .where(b -> b.content().like("123"))
//                .groupBy(b -> GroupKeys.of())


        List<Topic> topics = new ArrayList<>();
        Map<String, List<Topic>> collect = topics.stream().collect(Collectors.groupingBy(x -> x.getId()));

        List<Draft1<String>> list1 = easyEntityQuery.queryable(BlogEntity.class)
                .where(b -> b.content().like("123"))
                .groupBy(b -> GroupKeys.of(
                        b.createTime().nullOrDefault(LocalDateTime.now()).format("yyyy-MM-dd")
                )).select(group -> Select.DRAFT.of(
                        group.key1()
                )).toList();
        System.out.println("1");

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Draft2<Double, Integer>> list = easyEntityQuery.queryable(Topic.class)
                    .where(b -> {
                        b.id().eq("123");
                    }).select(t -> Select.DRAFT.of(
                            t.expression().sqlType("RAND()").asAnyType(Double.class),
                            t.expression().sqlType("IFNULL({0},{1})", c -> {
                                c.expression(t.stars()).value(1);
                            }).asAnyType(Integer.class)
                    )).toList();

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT RAND() AS `value1`,IFNULL(t.`stars`,?) AS `value2` FROM `t_topic` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(Integer),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }

        {
            NameConversion service = easyEntityQuery.getRuntimeContext().getService(NameConversion.class);
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            List<Topic> list = easyEntityQuery.queryable(Topic.class)
                    .where(b -> {
                        b.id().eq("123");
                    }).select(Topic.class, t -> Select.of(
                            t.expression().sqlType("RAND()", c -> {
                                c.setAlias(t.stars());
                            }).asAnyType(Double.class),
                            t.expression().sqlType("IFNULL({0},{1})", c -> {
                                c.expression(t.stars());
                                c.value(1);
                                c.setAlias(t.createTime());
                            }).asAnyType(Integer.class)
                    )).toList();

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT RAND() AS `stars`,IFNULL(t.`stars`,?) AS `createTime` FROM `t_topic` t WHERE t.`id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(Integer),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }

    }

    @Test
    public void testSubFrom() {
        List<Province> list = easyEntityQuery.queryable(Province.class)
                .where(p -> {
                    p.code().eq("123");
                }).select(p -> p.FETCHER.code().name().fetchProxy())
                .toList();
    }

    @Test
    public void testSubFrom1() {
        List<ProvinceVO> list = easyEntityQuery.queryable(Province.class)
                .where(p -> {
                    p.code().eq("123");
                }).select(p -> {
                    ProvinceVOProxy r = new ProvinceVOProxy();
                    r.myName().set(p.code());
                    r.myCode().set(p.name());
                    return r;
                }).where(p -> {
                    p.myCode().like("123");
                }).toList();
        List<ProvinceVO> list1 = easyEntityQuery.queryable(Province.class)
                .where(p -> {
                    p.code().eq("123");
                }).select(p -> new ProvinceVOProxy()
                        .myName().set(p.code())
                        .myCode().set(p.name())
                ).where(p -> {
                    p.myCode().like("123");
                }).toList();


        List<ProvinceVO> list2 = easyEntityQuery.queryable(Province.class)
                .where(p -> {
                    p.code().eq("123");
                }).select(ProvinceVO.class, p -> Select.of(
                        p.code().as(ProvinceVO.Fields.myName),
                        p.name().as(ProvinceVO.Fields.myCode)
                )).toList();
    }

    @Test
    public void testOptional() {


        {


            boolean ex1 = false;
            try {
                String checkField = EasyFieldCheckUtil.toCheckField("1=1 or name");
            } catch (Exception ex) {
                ex1 = true;
                Assert.assertTrue(ex instanceof EasyQueryInvalidFieldCheckException);
                Assert.assertEquals("column name has unsafe char: [=].", ex.getMessage());
            }
            Assert.assertTrue(ex1);
        }
        {

            boolean ex1 = false;
            try {
                String checkField = EasyFieldCheckUtil.toCheckField(" name");
            } catch (Exception ex) {
                ex1 = true;
                Assert.assertTrue(ex instanceof EasyQueryInvalidFieldCheckException);
                Assert.assertEquals("column name must not has space char.", ex.getMessage());
            }
            Assert.assertTrue(ex1);
        }

        Optional<Topic> topic = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    t.id().eq("1");
                }).streamBy(s -> s.findFirst());
        boolean present = topic.isPresent();
        System.out.println(present);
        Topic topic1 = topic.orElse(null);

        List<Topic> topics = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    t.id().eq("1");
                }).streamBy(s -> s.collect(Collectors.toList()));

        Map<String, Topic> mapWithMap = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    t.id().eq("1");
                }).streamBy(s -> s.collect(Collectors.toMap(o -> o.getId(), o -> o, (d1, d2) -> d2)));
        System.out.println("1");
        boolean error = false;
        try {
            easyQueryClient.queryable(Topic.class)
                    .configure(b -> {
                        b.getBehavior().removeBehavior(EasyBehaviorEnum.JDBC_LISTEN);
                    })
                    .where(t -> {
                        t.eq(t, "id", "name");
                    }).getSQLEntityExpressionBuilder().getExpressionContext().getBehavior();
        } catch (Exception ex) {
            error = true;
        }
        Assert.assertTrue(error);
    }

    @Test
    public void testOptional1() {
        TopicProxy table = TopicProxy.createTable();
        System.out.println(table);
        Optional<Topic> topic = easyEntityQuery.queryable(Topic.class)
                .where(t -> {
                    t.id().eq("1");
                    t.id().nullOrDefault("1").like("123");
                    t.id().nullOrDefault("2").in(Arrays.asList("1", "2", "3"));
                }).streamBy(s -> s.findFirst());
    }

    @Test
    public void testaaa() {
        SQLStringTypeColumn<SchoolStudentAddressProxy> address = SchoolStudentProxy.createTable().createEmpty().schoolStudentAddress().address();
        String fullNavValue = EasyProxyUtil.getFullNavValue(address);
        System.out.println(fullNavValue);
        SchoolClassProxy schoolClassProxy = SchoolClassProxy.createTable().create(EmptyTableAvailable.DEFAULT, EmptyEntityExpressionBuilder.DEFAULT, EmptyQueryRuntimeContext.DEFAULT);
        SQLStringTypeColumn<SchoolTeacherProxy> id = schoolClassProxy.schoolStudents()
                .flatElement().schoolClass().schoolTeachers().flatElement().id();
        MappingPath schoolStudentsIdsFlat = SchoolClassAOProp3.schoolStudentsIdsFlat;
        String navValue = EasyProxyUtil.getFullNavValue(id);
        Assert.assertEquals("schoolStudents.schoolClass.schoolTeachers.id", navValue);
        Assert.assertEquals("schoolStudents.id", schoolStudentsIdsFlat.__getMappingPath());


//
        List<ProvinceVO> list = easyEntityQuery.queryable(Topic.class)
                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
                .select(ProvinceVO.class, (t1, b2) -> Select.of(
                        t1.FETCHER.allFields(),
                        b2.createTime().as(ProvinceVO::getMyName)
                )).toList();


//        List<ProvinceVO> list2 = easyEntityQuery.queryable(Topic.class)
//                .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
//                .select((t1, b2) -> {
//                    ProvinceVOProxy provinceVOProxy = new ProvinceVOProxy();
//                    provinceVOProxy.selectAll(t1);
//                    provinceVOProxy.myName().set(
//                            t1.expression().sqlSegment("RANDOM()", c -> {
//                            }, String.class)
//                    );
//                    return provinceVOProxy;
//                }).toList();

    }

    @Test
    public void xxx123() {


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<ProvinceVO> list1 = easyEntityQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
                    .groupBy((t1, b2) -> GroupKeys.of(
                            t1.id()
                    )).select(group -> {

                        ProvinceVOProxy provinceVOProxy = new ProvinceVOProxy();
                        provinceVOProxy.myCode().set(group.key1());
                        provinceVOProxy.myName().set(
                                group.groupTable().t2.title().join(",")
                        );
                        return provinceVOProxy;
                    }).toList();

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `my_code`,GROUP_CONCAT(t1.`title` SEPARATOR ?) AS `my_name` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals(",(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            List<ProvinceVO> list1 = easyEntityQuery.queryable(Topic.class)
                    .leftJoin(BlogEntity.class, (t, b2) -> t.id().eq(b2.id()))
                    .groupBy((t1, b2) -> GroupKeys.of(
                            t1.id()
                    )).select(group -> {

                        ProvinceVOProxy provinceVOProxy = new ProvinceVOProxy();
                        provinceVOProxy.myCode().set(group.key1());
                        provinceVOProxy.myName().set(
                                group.groupTable().t2.title().join(",", true)
                        );
                        return provinceVOProxy;
                    }).toList();

            listenerContextManager.clear();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.`id` AS `my_code`,GROUP_CONCAT(DISTINCT t1.`title` SEPARATOR ?) AS `my_name` FROM `t_topic` t LEFT JOIN `t_blog` t1 ON t1.`deleted` = ? AND t.`id` = t1.`id` GROUP BY t.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals(",(String),false(Boolean)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

        }
    }


}

