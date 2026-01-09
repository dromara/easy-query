package com.easy.query.test;

import com.easy.query.api.proxy.base.ClassProxy;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.listener.ListenerContext;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * create time 2025/11/28 22:19
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest28 extends BaseTest {
    @Test
    public void testQueryMap() {
//        Topic[] objects = easyEntityQuery.queryable(Topic.class)
//                .where(t_topic -> {
//                    t_topic.title().isNotNull();
//                }).streamBy(s -> s.toArray(Topic[]::new));

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<Map<String, Object>> list = easyEntityQuery.mapQueryable("t_topic")
                .where(t_topic -> {
                    t_topic.get("id").eq("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT * FROM `t_topic` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testQueryMap2() {
//        Topic[] objects = easyEntityQuery.queryable(Topic.class)
//                .where(t_topic -> {
//                    t_topic.title().isNotNull();
//                }).streamBy(s -> s.toArray(Topic[]::new));

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        List<TypeA> list = easyEntityQuery.queryable(ClassProxy.of(TypeA.class))
                .asTable("t_topic")
                .where(t_topic -> {
                    t_topic.field("id").eq("123");
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id` FROM `t_topic` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Data
    public static class TypeA{
        private String id;
    }

    @Test
    public void testQueryNullOrDefault(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.stars().nullOrDefaultZero().eq(1);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE IFNULL(`stars`,0) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testQueryNullOrDefault2(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        List<Topic> list1 = easyEntityQuery.queryable(Topic.class)
                .where(t_topic -> {
                    t_topic.stars().nullOrDefaultFormat(1).eq(1);
                }).toList();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time` FROM `t_topic` WHERE IFNULL(`stars`,1) = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
}
