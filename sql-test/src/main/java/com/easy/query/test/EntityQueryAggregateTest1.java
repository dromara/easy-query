package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable10;
import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.api.proxy.entity.select.EntityQueryable4;
import com.easy.query.api.proxy.entity.select.EntityQueryable5;
import com.easy.query.api.proxy.entity.select.EntityQueryable6;
import com.easy.query.api.proxy.entity.select.EntityQueryable7;
import com.easy.query.api.proxy.entity.select.EntityQueryable8;
import com.easy.query.api.proxy.entity.select.EntityQueryable9;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * create time 2023/10/31 08:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityQueryAggregateTest1 extends BaseTest {

    public EntityQueryable10<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join1() {
        EntityQueryable10<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t9.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t10.id()));
        return query;
    }

    public EntityQueryable9<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join2() {
        EntityQueryable9<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t9.id()));
        return query;
    }

    public EntityQueryable8<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join3() {
        EntityQueryable8<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()));
        return query;
    }

    public EntityQueryable7<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join4() {
        EntityQueryable7<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()));
        return query;
    }

    public EntityQueryable6<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join5() {
        EntityQueryable6<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()));
        return query;
    }

    public EntityQueryable5<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join6() {
        EntityQueryable5<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()));
        return query;
    }

    public EntityQueryable4<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join7() {
        EntityQueryable4<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()));
        return query;
    }

    public EntityQueryable3<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join8() {
        EntityQueryable3<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .leftJoin(Topic.class, (t1, t2) -> t1.id().eq(t2.id()))
                .leftJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()));
        return query;
    }

    @Test
    public void test1() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Integer integer = join1().sumOrDefault((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                return t.stars();
            }, 1);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            Integer integer = join1().sumOrNull((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                return t.stars();
            });
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            BigDecimal bigDecimal = join1().sumBigDecimalOrDefault((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                return t.stars();
            }, BigDecimal.ZERO);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            BigDecimal bigDecimal = join1().sumBigDecimalOrNull((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                return t.stars();
            });
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            Integer integer = join1().maxOrNull((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                return t.stars();
            });
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            Integer integer = join1().maxOrDefault((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                return t.stars();
            }, 1);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            Integer integer = join1().minOrNull((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                return t.stars();
            });
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            Integer integer = join1().minOrDefault((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                return t.stars();
            }, 1);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            Double aDouble = join1().avgOrNull((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                return t.stars();
            });
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            BigDecimal bigDecimal = join1().avgBigDecimalOrNull((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                return t.stars();
            });
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            Float abcv = join1().avgFloatOrNull((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                return t.stars();
            });
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            Double aDouble = join1().avgOrDefault((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                return t.stars();
            }, 1d);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            BigDecimal bigDecimal = join1().avgBigDecimalOrDefault((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                return t.stars();
            }, BigDecimal.ZERO);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            Float abcv = join1().avgFloatOrDefault((t, t1, t2, t3, t4, t5, t6, t7, t8, t9) -> {
                return t.stars();
            }, 1f);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t LEFT JOIN `t_topic` t1 ON t.`id` = t1.`id` LEFT JOIN `t_topic` t2 ON t.`id` = t2.`id` LEFT JOIN `t_topic` t3 ON t.`id` = t3.`id` LEFT JOIN `t_topic` t4 ON t.`id` = t4.`id` LEFT JOIN `t_topic` t5 ON t.`id` = t5.`id` LEFT JOIN `t_topic` t6 ON t.`id` = t6.`id` LEFT JOIN `t_topic` t7 ON t.`id` = t7.`id` LEFT JOIN `t_topic` t8 ON t.`id` = t8.`id` LEFT JOIN `t_topic` t9 ON t.`id` = t9.`id`", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("%1%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

    }
//
//    @Test
//    public void test2() {
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join2().asTable("abcv").sumOrDefault((t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join2().asTable("abcv").sumOrNull((t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join2().asTable("abcv").sumBigDecimalOrDefault((t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
//                        t.column(Topic::getStars);
//                    }, BigDecimal.ZERO);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join2().asTable("abcv").sumBigDecimalOrNull((t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join2().asTable("abcv").maxOrNull((t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join2().asTable("abcv").maxOrDefault((t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join2().asTable("abcv").minOrNull((t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join2().asTable("abcv").minOrDefault((t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join2().asTable("abcv").avgOrNull((t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join2().asTable("abcv").avgBigDecimalOrNull((t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join2().asTable("abcv").avgFloatOrNull((t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`", sql);
//        }
//
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join2().asTable("abcv").avgOrDefault((t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
//                        t.column(Topic::getStars);
//                    }, 1d);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join2().asTable("abcv").avgBigDecimalOrDefault((t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
//                        t.column(Topic::getStars);
//                    }, BigDecimal.ZERO);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join2().asTable("abcv").avgFloatOrDefault((t, t1, t2, t3, t4, t5, t6, t7, t8) -> {
//                        t.column(Topic::getStars);
//                    }, 1f);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`", sql);
//        }
//
//    }
//
//    @Test
//    public void test3() {
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join3().asTable("abcv").sumOrDefault((t, t1, t2, t3, t4, t5, t6, t7) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join3().asTable("abcv").sumOrNull((t, t1, t2, t3, t4, t5, t6, t7) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join3().asTable("abcv").sumBigDecimalOrDefault((t, t1, t2, t3, t4, t5, t6, t7) -> {
//                        t.column(Topic::getStars);
//                    }, BigDecimal.ZERO);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join3().asTable("abcv").sumBigDecimalOrNull((t, t1, t2, t3, t4, t5, t6, t7) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join3().asTable("abcv").maxOrNull((t, t1, t2, t3, t4, t5, t6, t7) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join3().asTable("abcv").maxOrDefault((t, t1, t2, t3, t4, t5, t6, t7) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join3().asTable("abcv").minOrNull((t, t1, t2, t3, t4, t5, t6, t7) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join3().asTable("abcv").minOrDefault((t, t1, t2, t3, t4, t5, t6, t7) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join3().asTable("abcv").avgOrNull((t, t1, t2, t3, t4, t5, t6, t7) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join3().asTable("abcv").avgBigDecimalOrNull((t, t1, t2, t3, t4, t5, t6, t7) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join3().asTable("abcv").avgFloatOrNull((t, t1, t2, t3, t4, t5, t6, t7) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`", sql);
//        }
//
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join3().asTable("abcv").avgOrDefault((t, t1, t2, t3, t4, t5, t6, t7) -> {
//                        t.column(Topic::getStars);
//                    }, 1d);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join3().asTable("abcv").avgBigDecimalOrDefault((t, t1, t2, t3, t4, t5, t6, t7) -> {
//                        t.column(Topic::getStars);
//                    }, BigDecimal.ZERO);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join3().asTable("abcv").avgFloatOrDefault((t, t1, t2, t3, t4, t5, t6, t7) -> {
//                        t.column(Topic::getStars);
//                    }, 1f);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`", sql);
//        }
//
//    }
//
//    @Test
//    public void test4() {
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join4().asTable("abcv").sumOrDefault((t, t1, t2, t3, t4, t5, t6) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join4().asTable("abcv").sumOrNull((t, t1, t2, t3, t4, t5, t6) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join4().asTable("abcv").sumBigDecimalOrDefault((t, t1, t2, t3, t4, t5, t6) -> {
//                        t.column(Topic::getStars);
//                    }, BigDecimal.ZERO);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join4().asTable("abcv").sumBigDecimalOrNull((t, t1, t2, t3, t4, t5, t6) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join4().asTable("abcv").maxOrNull((t, t1, t2, t3, t4, t5, t6) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join4().asTable("abcv").maxOrDefault((t, t1, t2, t3, t4, t5, t6) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join4().asTable("abcv").minOrNull((t, t1, t2, t3, t4, t5, t6) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join4().asTable("abcv").minOrDefault((t, t1, t2, t3, t4, t5, t6) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join4().asTable("abcv").avgOrNull((t, t1, t2, t3, t4, t5, t6) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join4().asTable("abcv").avgBigDecimalOrNull((t, t1, t2, t3, t4, t5, t6) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join4().asTable("abcv").avgFloatOrNull((t, t1, t2, t3, t4, t5, t6) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`", sql);
//        }
//
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join4().asTable("abcv").avgOrDefault((t, t1, t2, t3, t4, t5, t6) -> {
//                        t.column(Topic::getStars);
//                    }, 1d);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join4().asTable("abcv").avgBigDecimalOrDefault((t, t1, t2, t3, t4, t5, t6) -> {
//                        t.column(Topic::getStars);
//                    }, BigDecimal.ZERO);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join4().asTable("abcv").avgFloatOrDefault((t, t1, t2, t3, t4, t5, t6) -> {
//                        t.column(Topic::getStars);
//                    }, 1f);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`", sql);
//        }
//
//    }
//
//    @Test
//    public void test5() {
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join5().asTable("abcv").sumOrDefault((t, t1, t2, t3, t4, t5) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join5().asTable("abcv").sumOrNull((t, t1, t2, t3, t4, t5) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join5().asTable("abcv").sumBigDecimalOrDefault((t, t1, t2, t3, t4, t5) -> {
//                        t.column(Topic::getStars);
//                    }, BigDecimal.ZERO);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join5().asTable("abcv").sumBigDecimalOrNull((t, t1, t2, t3, t4, t5) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join5().asTable("abcv").maxOrNull((t, t1, t2, t3, t4, t5) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join5().asTable("abcv").maxOrDefault((t, t1, t2, t3, t4, t5) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join5().asTable("abcv").minOrNull((t, t1, t2, t3, t4, t5) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join5().asTable("abcv").minOrDefault((t, t1, t2, t3, t4, t5) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join5().asTable("abcv").avgOrNull((t, t1, t2, t3, t4, t5) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join5().asTable("abcv").avgBigDecimalOrNull((t, t1, t2, t3, t4, t5) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join5().asTable("abcv").avgFloatOrNull((t, t1, t2, t3, t4, t5) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`", sql);
//        }
//
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join5().asTable("abcv").avgOrDefault((t, t1, t2, t3, t4, t5) -> {
//                        t.column(Topic::getStars);
//                    }, 1d);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join5().asTable("abcv").avgBigDecimalOrDefault((t, t1, t2, t3, t4, t5) -> {
//                        t.column(Topic::getStars);
//                    }, BigDecimal.ZERO);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join5().asTable("abcv").avgFloatOrDefault((t, t1, t2, t3, t4, t5) -> {
//                        t.column(Topic::getStars);
//                    }, 1f);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`", sql);
//        }
//
//    }
//
//    @Test
//    public void test6() {
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join6().asTable("abcv").sumOrDefault((t, t1, t2, t3, t4) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join6().asTable("abcv").sumOrNull((t, t1, t2, t3, t4) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join6().asTable("abcv").sumBigDecimalOrDefault((t, t1, t2, t3, t4) -> {
//                        t.column(Topic::getStars);
//                    }, BigDecimal.ZERO);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join6().asTable("abcv").sumBigDecimalOrNull((t, t1, t2, t3, t4) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join6().asTable("abcv").maxOrNull((t, t1, t2, t3, t4) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join6().asTable("abcv").maxOrDefault((t, t1, t2, t3, t4) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join6().asTable("abcv").minOrNull((t, t1, t2, t3, t4) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join6().asTable("abcv").minOrDefault((t, t1, t2, t3, t4) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join6().asTable("abcv").avgOrNull((t, t1, t2, t3, t4) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join6().asTable("abcv").avgBigDecimalOrNull((t, t1, t2, t3, t4) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join6().asTable("abcv").avgFloatOrNull((t, t1, t2, t3, t4) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`", sql);
//        }
//
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join6().asTable("abcv").avgOrDefault((t, t1, t2, t3, t4) -> {
//                        t.column(Topic::getStars);
//                    }, 1d);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join6().asTable("abcv").avgBigDecimalOrDefault((t, t1, t2, t3, t4) -> {
//                        t.column(Topic::getStars);
//                    }, BigDecimal.ZERO);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join6().asTable("abcv").avgFloatOrDefault((t, t1, t2, t3, t4) -> {
//                        t.column(Topic::getStars);
//                    }, 1f);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`", sql);
//        }
//
//    }
//
//    @Test
//    public void test7() {
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join7().asTable("abcv").sumOrDefault((t, t1, t2, t3) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join7().asTable("abcv").sumOrNull((t, t1, t2, t3) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join7().asTable("abcv").sumBigDecimalOrDefault((t, t1, t2, t3) -> {
//                        t.column(Topic::getStars);
//                    }, BigDecimal.ZERO);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join7().asTable("abcv").sumBigDecimalOrNull((t, t1, t2, t3) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join7().asTable("abcv").maxOrNull((t, t1, t2, t3) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join7().asTable("abcv").maxOrDefault((t, t1, t2, t3) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join7().asTable("abcv").minOrNull((t, t1, t2, t3) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join7().asTable("abcv").minOrDefault((t, t1, t2, t3) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join7().asTable("abcv").avgOrNull((t, t1, t2, t3) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join7().asTable("abcv").avgBigDecimalOrNull((t, t1, t2, t3) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join7().asTable("abcv").avgFloatOrNull((t, t1, t2, t3) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`", sql);
//        }
//
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join7().asTable("abcv").avgOrDefault((t, t1, t2, t3) -> {
//                        t.column(Topic::getStars);
//                    }, 1d);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join7().asTable("abcv").avgBigDecimalOrDefault((t, t1, t2, t3) -> {
//                        t.column(Topic::getStars);
//                    }, BigDecimal.ZERO);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join7().asTable("abcv").avgFloatOrDefault((t, t1, t2, t3) -> {
//                        t.column(Topic::getStars);
//                    }, 1f);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`", sql);
//        }
//
//    }
//
//    @Test
//    public void test8() {
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join8().asTable("abcv").sumOrDefault((t, t1, t2) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join8().asTable("abcv").sumOrNull((t, t1, t2) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join8().asTable("abcv").sumBigDecimalOrDefault((t, t1, t2) -> {
//                        t.column(Topic::getStars);
//                    }, BigDecimal.ZERO);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join8().asTable("abcv").sumBigDecimalOrNull((t, t1, t2) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join8().asTable("abcv").maxOrNull((t, t1, t2) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join8().asTable("abcv").maxOrDefault((t, t1, t2) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join8().asTable("abcv").minOrNull((t, t1, t2) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join8().asTable("abcv").minOrDefault((t, t1, t2) -> {
//                        t.column(Topic::getStars);
//                    }, 1);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join8().asTable("abcv").avgOrNull((t, t1, t2) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join8().asTable("abcv").avgBigDecimalOrNull((t, t1, t2) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join8().asTable("abcv").avgFloatOrNull((t, t1, t2) -> {
//                        t.column(Topic::getStars);
//                    });
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`", sql);
//        }
//
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join8().asTable("abcv").avgOrDefault((t, t1, t2) -> {
//                        t.column(Topic::getStars);
//                    }, 1d);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join8().asTable("abcv").avgBigDecimalOrDefault((t, t1, t2) -> {
//                        t.column(Topic::getStars);
//                    }, BigDecimal.ZERO);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`", sql);
//        }
//        {
//            Supplier<Exception> f = () -> {
//                try {
//                    join8().asTable("abcv").avgFloatOrDefault((t, t1, t2) -> {
//                        t.column(Topic::getStars);
//                    }, 1f);
//                } catch (Exception ex) {
//                    return ex;
//                }
//                return null;
//            };
//            Exception exception = f.get();
//            Assert.assertNotNull(exception);
//            Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//            Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//            EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//            String sql = easyQuerySQLStatementException.getSQL();
//            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`", sql);
//        }
//
//    }
}
