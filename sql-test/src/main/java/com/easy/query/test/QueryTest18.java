package com.easy.query.test;

import com.easy.query.api.proxy.base.MapTypeProxy;
import com.easy.query.api.proxy.key.MapKey;
import com.easy.query.api.proxy.key.MapKeys;
import com.easy.query.api4j.select.Queryable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.expression.builder.core.NotNullOrEmptyValueFilter;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.core.proxy.extension.functions.executor.ColumnFunctionComparableAnyChainExpression;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUser;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.entity.school.proxy.SchoolStudentProxy;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.vo.TestUserAAA;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * create time 2024/6/13 12:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest18 extends BaseTest {
    @Test
    public void test1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Topic topic = easyEntityQuery.queryable(Topic.class)
                .where(t -> t.id().isNull())
                .singleOrDefault(new Topic());
        Assert.assertNull(topic.getId());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` IS NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void test2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        Topic topic = easyEntityQuery.queryable(Topic.class)
                .where(t -> t.id().isNull())
                .singleOrDefault(new Topic(), result -> result == null);
        Assert.assertNull(topic.getId());
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE `id` IS NULL", jdbcExecuteAfterArg.getBeforeArg().getSql());
//        Assert.assertEquals("0(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
        Character x = 'a';

        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .where(s -> {
                    s.id().in(Arrays.asList("1", "2"));
                }).toList();
    }

    @Test
    public void testxxx() {

//        String sql="";
//        easyEntityQuery.queryable(Topic.class)
//                .where(t -> {
//                    if(EasyStringUtil.isNotBlank(sql)){
//                        t.expression().sql(sql);
//                    }
//                })
//                .where(EasyStringUtil.isNotBlank(sql),t -> {
//                    t.expression().sql(sql);
//                })

        Queryable<TestUserAAA> queryable = easyQuery.queryable(TestUserAAA.class);
    }

    @Test
    public void test8() {
        MappingPath address = SchoolStudentProxy.TABLE.schoolStudentAddress().address();
        String s = address.__getMappingPath();
        System.out.println(s);
        Assert.assertEquals("schoolStudentAddress.address", s);
    }

    @Test
    public void test9() {
        List<Topic> list4 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.createTime().format("yyyy/MM/dd").eq("2023/01/01"))
                .select(o -> {
                    TopicProxy r = new TopicProxy();
                    r.title().set(o.stars().nullOrDefault(0).toStr());
                    ColumnFunctionComparableAnyChainExpression<String> nullProperty = o.expression().sqlSegment("IFNULL({0},'')", c -> {
                        c.expression(o.id());
                    }, String.class);
                    r.alias().set(nullProperty);
                    return r;
                })
                .toList();
    }

    @Test
    public void test10() {
        List<Topic> list2 = easyEntityQuery.queryable(Topic.class)
                .where(o -> o.createTime().format("yyyy/MM/dd").eq("2023/01/01"))
                .select(o -> {
                    TopicProxy r = new TopicProxy();
                    r.title().set(o.stars().nullOrDefault(0).toStr());
                    r.alias().setSQL("IFNULL({0},'')", c -> {
                        c.keepStyle();
                        c.expression(o.id());
                    });
                    return r;
                })
                .toList();
    }
    @Test
    public void test11() {
        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(s -> {
                    s.blogs().any(x -> {
                        x.content().eq("");
                    });
                }).toList();
    }
    @Test
    public void test12() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(s -> {
                    s.expression().ifEquals(s.id(),"123").then(s.id()).elseEnd(s.id().nullOrDefault("1234")).eq("123xx");
                    s.blogs().any(x -> {
                        x.content().eq("");
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t WHERE (CASE WHEN t.`id` = ? THEN t.`id` ELSE IFNULL(t.`id`,?) END) = ? AND EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`title` = t.`id` AND t1.`content` = ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1234(String),123xx(String),false(Boolean),(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void test13() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<SysUser> list = easyEntityQuery.queryable(SysUser.class)
                .filterConfigure(NotNullOrEmptyValueFilter.DEFAULT)
                .where(s -> {
                    s.expression().caseWhen(()->s.id().eq("123")).then(s.id()).elseEnd(s.id().nullOrDefault("1234")).eq("123xx");
                    s.blogs().any(x -> {
                        x.content().eq("");
                    });
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t.`id`,t.`create_time`,t.`username`,t.`phone`,t.`id_card`,t.`address` FROM `easy-query-test`.`t_sys_user` t WHERE (CASE WHEN t.`id` = ? THEN t.`id` ELSE IFNULL(t.`id`,?) END) = ? AND EXISTS (SELECT 1 FROM `t_blog` t1 WHERE t1.`deleted` = ? AND t1.`title` = t.`id` AND t1.`content` = ? LIMIT 1)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1234(String),123xx(String),false(Boolean),(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

}
