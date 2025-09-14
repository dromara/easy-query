package com.easy.query.test;

import com.easy.query.api.proxy.entity.update.ExpressionUpdatable;
import com.easy.query.api.proxy.entity.update.impl.EasyEntityUpdatable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.MapValue;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUserSQLEncryption;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicAuto;
import com.easy.query.test.entity.TopicLarge;
import com.easy.query.test.entity.TopicTypeTest1;
import com.easy.query.test.entity.TopicTypeTest2;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.entity.proxy.TopicTypeTest1Proxy;
import com.easy.query.test.entity.proxy.TopicTypeTest2Proxy;
import com.easy.query.test.enums.TopicTypeEnum;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.vo.BlogEntityVO1;
import com.easy.query.test.vo.BlogEntityVO2;
import com.easy.query.test.vo.SysMenuDto;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author xuejiaming
 * @FileName: UpdateTest.java
 * @Description: 文件说明
 * create time 2023/3/20 21:58
 */
public class UpdateTest extends BaseTest {

    @Test
    public void updateTest1() {
        {

            long rows = easyEntityQuery.updatable(Topic.class)
                    .setColumns(t_topic -> t_topic.stars().set(12))
//                    .setSQLSegment(Topic::getStars,"NOT {0}",c->c.expression(Topic::getStars))
                    .where(o -> o.id().eq( "2"))
                    .executeRows();
            Assert.assertEquals(1, rows);
        }
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            long rows = easyEntityQuery.updatable(Topic.class)
                    .setColumns(o -> {
                        o.stars().set(12);
                    })
                    .where(o -> o.id().eq("2"))
                    .where(o -> o.title().eq("2c"))
                    .executeRows();
            Assert.assertEquals(0, rows);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("UPDATE `t_topic` SET `stars` = ? WHERE `id` = ? AND `title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("12(Integer),2(String),2c(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            long rows = easyEntityQuery.updatable(Topic.class)
                    .setColumns(o -> o.stars().set(12))
                    .where(o -> {
                        o.id().eq("2");
                        o.title().eq("2c");
                    })
                    .executeRows();
            Assert.assertEquals(0, rows);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("UPDATE `t_topic` SET `stars` = ? WHERE `id` = ? AND `title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("12(Integer),2(String),2c(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);


            long rows = easyEntityQuery.updatable(Topic.class)
                    .setColumns(o -> {
                        o.stars().set(12);
                    })
                    .where(o -> {
                        o.id().eq("2");
                        o.title().eq("21");
                        o.createTime().eq(LocalDateTime.of(2021, 3, 4, 5, 1));
                        o.title().eq("2c");
                    })
                    .executeRows();
            Assert.assertEquals(0, rows);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("UPDATE `t_topic` SET `stars` = ? WHERE `id` = ? AND `title` = ? AND `create_time` = ? AND `title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("12(Integer),2(String),21(String),2021-03-04T05:01(LocalDateTime),2c(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void updateTest2() {
        long rows = easyEntityQuery.updatable(Topic.class)
                .setColumns(s->s.stars().set(12))
                .where(o -> o.id().eq( "2x"))
                .executeRows();
        Assert.assertEquals(0, rows);
    }

    @Test
    public void updateTest3() {
        Topic topic = easyEntityQuery.queryable(Topic.class).whereById("3").firstOrNull();
        Assert.assertNotNull(topic);
        Assert.assertEquals("3", topic.getId());
        long rows = easyEntityQuery.updatable(Topic.class)
                .setColumns(t_topic -> t_topic.title().set(t_topic.stars().asStr()))
                .where(o -> o.id().eq( topic.getId()))
                .executeRows();
        Assert.assertEquals(1, rows);
        Topic topic2 = easyEntityQuery.queryable(Topic.class).whereById("3").firstOrNull();
        Assert.assertNotNull(topic2);
        Assert.assertEquals(String.valueOf(topic.getStars()), topic2.getTitle());
    }

    @Test
    public void updateTest4() {
        long rows = easyEntityQuery.updatable(Topic.class)
                .setColumns(t_topic -> t_topic.stars().set(2))
                .whereById("5").executeRows();
        Assert.assertEquals(1, rows);


        long rows1 = easyEntityQuery.updatable(Topic.class)
                .setColumns(t_topic -> t_topic.stars().increment())
                .whereById("5").executeRows();
        Assert.assertEquals(1, rows1);
        Topic topic1 = easyEntityQuery.queryable(Topic.class)
                .whereById("5").firstOrNull();
        Assert.assertNotNull(topic1);
        Assert.assertEquals(3, (int) topic1.getStars());

        long rows2 = easyEntityQuery.updatable(Topic.class)
                .setColumns(t_topic -> t_topic.stars().increment(2))
                .where(o -> o.id().eq( "5")).executeRows();
        Assert.assertEquals(1, rows2);
        Topic topic2 = easyEntityQuery.queryable(Topic.class)
                .whereById("5").firstOrNull();
        Assert.assertNotNull(topic2);
        Assert.assertEquals(5, (int) topic2.getStars());

        long rows3 = easyEntityQuery.updatable(Topic.class)
                .setColumns(t_topic -> t_topic.stars().decrement())
                .where(o -> o.id().eq( "5")).executeRows();
        Assert.assertEquals(1, rows3);
        Topic topic3 = easyEntityQuery.queryable(Topic.class)
                .whereById("5").firstOrNull();
        Assert.assertNotNull(topic3);
        Assert.assertEquals(4, (int) topic3.getStars());

        long rows4 = easyEntityQuery.updatable(Topic.class)
                .setColumns(t_topic -> t_topic.stars().decrement(2))
                .where(o -> o.id().eq( "5")).executeRows();
        Assert.assertEquals(1, rows4);
        Topic topic4 = easyEntityQuery.queryable(Topic.class)
                .whereById("5").firstOrNull();
        Assert.assertNotNull(topic4);
        Assert.assertEquals(2, (int) topic4.getStars());
    }

    @Test
    public void updateTest5() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq( "7")).firstNotNull("未找到对应的数据");
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            String sql = (easyEntityQuery.updatable(topic))
                    .toSQL(topic);
            //默认没开启track所以应该是更新所有
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ?", sql);
            long l = easyEntityQuery.updatable(topic).executeRows();
            Assert.assertEquals(1, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest6() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq( "7")).asTracking().firstNotNull("未找到对应的数据");
            String newTitle = "test123" + new Random().nextInt(100000);
            topic.setTitle(newTitle);
            String sql = (easyEntityQuery.updatable(topic))
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ?", sql);
            long l = easyEntityQuery.updatable(topic).executeRows();
            Assert.assertEquals(1, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest7() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq( "7")).firstNotNull("未找到对应的数据");
            boolean b = easyEntityQuery.addTracking(topic);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            String sql = (easyEntityQuery.updatable(topic))
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ?", sql);
            long l = easyEntityQuery.updatable(topic).executeRows();
            Assert.assertEquals(1, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest8() {
        long l = easyEntityQuery.updatable(Topic.class)
                .setColumns(t_topic -> t_topic.title().setNull())
                .whereById("9").executeRows();
        Assert.assertEquals(1, l);
        Topic topic = easyEntityQuery.queryable(Topic.class)
                .whereById("9").firstOrNull();
        Assert.assertNotNull(topic);
        String updateSql = (easyEntityQuery.updatable(topic).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS))
                .toSQL(topic);
        Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ?", updateSql);
        long l1 = easyEntityQuery.updatable(topic)
                .setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS)
                .executeRows();
        Assert.assertEquals(1, l1);
    }

    @Test
    public void updateTest9() {
        long l = easyEntityQuery.updatable(Topic.class)
                .setColumns(t_topic -> t_topic.title().setNull())
                .whereById("10").executeRows();
        Assert.assertEquals(1, l);
        Topic topic = easyEntityQuery.queryable(Topic.class)
                .whereById("10").firstOrNull();
        Assert.assertNotNull(topic);
        String updateSQL = easyEntityQuery.updatable(topic)
                .setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS)
                .toSQL(topic);
        Assert.assertEquals("UPDATE `t_topic` SET `stars` = ?,`create_time` = ? WHERE `id` = ?", updateSQL);
        long l1 = easyEntityQuery.updatable(topic)
                .setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS)
                .executeRows();
        Assert.assertEquals(1, l1);
    }

    @Test
    public void updateTest10() {
        try {

            easyEntityQuery.updatable(Topic.class)
                    .setColumns(s->s.stars().set(12))
                    .where(o -> o.id().eq( UUID.randomUUID().toString()))
                    .executeRows(1, "123");
        } catch (Exception e) {
            Assert.assertEquals(EasyQueryConcurrentException.class, e.getClass());
        }
    }

    @Test
    public void updateTest11() {
        TopicTypeTest1 topicType = easyEntityQuery.queryable(TopicTypeTest1.class)
                .whereById("123").firstOrNull();
        if (topicType != null) {
            long l = easyEntityQuery.deletable(topicType).executeRows();
            Assert.assertEquals(1, l);
        }
        ExpressionUpdatable<TopicTypeTest1Proxy, TopicTypeTest1> queryable = easyEntityQuery.updatable(TopicTypeTest1.class)
                .setColumns(t -> t.stars().set(234))
                .where(o -> o.topicType().eq(TopicTypeEnum.CLASSER));
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(queryable.getExpressionContext().getTableContext());
        String sql = queryable.toSQL(toSQLContext);
        Assert.assertEquals("UPDATE `t_topic_type` SET `stars` = ? WHERE `topic_type` = ?", sql);
        String parameterToString = EasySQLUtil.sqlParameterToString(toSQLContext.getParameters());
        Assert.assertEquals("234(Integer),CLASSER(TopicTypeEnum)", parameterToString);

        long l = easyEntityQuery.updatable(TopicTypeTest1.class)
                .setColumns(t -> t.stars().set(234))
                .where(o -> o.topicType().eq(TopicTypeEnum.CLASSER))
                .executeRows();
    }

    @Test
    public void updateTest11_1() {
        TopicTypeTest2 topicType = easyEntityQuery.queryable(TopicTypeTest2.class)
                .whereById("123").firstOrNull();
        if (topicType != null) {
            long l = easyEntityQuery.deletable(topicType).executeRows();
            Assert.assertEquals(1, l);
        }
        ExpressionUpdatable<TopicTypeTest2Proxy, TopicTypeTest2> queryable = easyEntityQuery.updatable(TopicTypeTest2.class)
                .setColumns(t -> t.stars().set(234))
                .where(o -> o.topicType().eq(TopicTypeEnum.CLASSER));
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(queryable.getExpressionContext().getTableContext());
        String sql = queryable.toSQL(toSQLContext);
        Assert.assertEquals("UPDATE `t_topic_type` SET `stars` = ? WHERE `topic_type` = ?", sql);
        String parameterToString = EasySQLUtil.sqlParameterToString(toSQLContext.getParameters());
        Assert.assertEquals("234(Integer),CLASSER(TopicTypeEnum)", parameterToString);

        long l = easyEntityQuery.updatable(TopicTypeTest2.class)
                .setColumns(t -> t.stars().set(234))
                .where(o -> o.topicType().eq( TopicTypeEnum.CLASSER))
                .executeRows();
    }

    @Test
    public void updateTest12() {
        Topic topic = easyEntityQuery.queryable(Topic.class).whereById("15").firstOrNull();
        Assert.assertNotNull(topic);
        long rows4 = easyEntityQuery.updatable(topic)
                .setColumns(o -> o.createTime())
                .whereColumns(o -> o.stars()).executeRows();
        Assert.assertEquals(1, rows4);
    }

    @Test
    public void updateTest12_1() {
        Topic topic = easyEntityQuery.queryable(Topic.class).whereById("15").firstOrNull();
        Assert.assertNotNull(topic);
        String sql = easyEntityQuery.updatable(topic)
                .whereColumns(t_topic -> t_topic.FETCHER.columnKeys().stars())
                .toSQL(topic);

        Assert.assertEquals("UPDATE `t_topic` SET `title` = ?,`create_time` = ? WHERE `id` = ? AND `stars` = ?", sql);
    }

    @Test
    public void updateTest12_2() {
        Topic topic = easyEntityQuery.queryable(Topic.class).whereById("15").firstOrNull();
        Assert.assertNotNull(topic);
        String sql = easyEntityQuery.updatable(topic)
                .whereColumns(o -> o.FETCHER.columnKeys().stars()).toSQL(topic);
        Assert.assertEquals("UPDATE `t_topic` SET `title` = ?,`create_time` = ? WHERE `id` = ? AND `stars` = ?", sql);
    }

    @Test
    public void updateTest16() {
        TopicAuto topicAuto = null;
        long l = easyEntityQuery.getEasyQueryClient().updatable(topicAuto)
                .noInterceptor().noInterceptor("11")
                .useInterceptor("11").useInterceptor()
                .disableLogicDelete()
                .enableLogicDelete()
                .useLogicDelete(true)
                .setSQLStrategy(true, SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS)
                .asAlias("a")
                .asSchema("b")
                .asTable("c")
                .asSchema(o -> o + "ab")
                .asTable(o -> o + "bb")
                .executeRows();
        Assert.assertEquals(0, l);
    }
//    @Test
//    public void updateTest17() {
//        TopicValueUpdateAtomicTrack topicValueUpdateAtomicTrack = new TopicValueUpdateAtomicTrack();
//        topicValueUpdateAtomicTrack.setId("123");
//        topicValueUpdateAtomicTrack.setStars(99);
//        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
//        try {
//            trackManager.begin();
//            easyEntityQuery.addTracking(topicValueUpdateAtomicTrack);
//            topicValueUpdateAtomicTrack.setStars(98);
//            long l = easyEntityQuery.updatable(topicValueUpdateAtomicTrack).executeRows();
//            System.out.println(l);
//        } catch (Exception ex) {
//            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
//            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
//            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
//            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
//            Assert.assertEquals("UPDATE `t_topic_value_atomic` SET `stars` = `stars`- ? WHERE `id` = ? AND `stars` >= ?", sql);
//        } finally {
//            trackManager.release();
//        }
//    }

    @Test
    public void updateTest17() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = new Topic();
            topic.setId("123xx");
            easyEntityQuery.addTracking(topic);
            String newTitle = "test123" + new Random().nextInt(100000);
            topic.setTitle(newTitle);
            String sql = (easyEntityQuery.updatable(topic))
                    .whereColumns(o -> o.FETCHER.id().title())
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ? AND `title` IS NULL", sql);
            long l = easyEntityQuery.updatable(topic).executeRows();
            Assert.assertEquals(0, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest17_1() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = new Topic();
            topic.setId("123xx");
            easyEntityQuery.addTracking(topic);
            String newTitle = "test123" + new Random().nextInt(100000);
            topic.setTitle(newTitle);
            String sql = (easyEntityQuery.updatable(topic))
                    .whereColumns(o -> {
                        Assert.assertNotNull(o.getTable());
                        return o.FETCHER.columnKeys().title();
                    })
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ? AND `title` IS NULL", sql);
            long l = easyEntityQuery.updatable(topic).executeRows();
            Assert.assertEquals(0, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest17_2() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = new Topic();
            topic.setId("123xx");
            easyEntityQuery.addTracking(topic);
            String newTitle = "test123" + new Random().nextInt(100000);
            topic.setTitle(newTitle);
            String sql = (easyEntityQuery.updatable(topic))
                    .whereColumns(o -> {
                        return o.FETCHER.allFieldsExclude(o.title());
                    })
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ? AND `stars` IS NULL AND `create_time` IS NULL AND `alias` IS NULL", sql);
            long l = easyEntityQuery.updatable(topic).executeRows();
            Assert.assertEquals(0, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }


    @Test
    public void updateTest18_1() {
        try {

            long rows = easyEntityQuery.updatable(Topic.class)
                    .asTable("xxxxx")
                    .setColumns(o -> {
                        o.stars().setSQL("ifnull({0},0)+{1}", (context) -> {
                            context.expression(o.stars())
                                    .value(1);
                        });
                    })
                    .where(o -> o.id().eq("2"))
                    .executeRows();
            Assert.assertEquals(1, rows);
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `xxxxx` SET `stars` = ifnull(`stars`,0)+? WHERE `id` = ?", sql);
        }
    }

    @Test
    public void updateTest19() {
        try {
            Topic topic = new Topic();
            long rows = easyEntityQuery.updatable(topic)
                    .asTable("xxxxx")
                    .columnConfigure((o,configurer)->{
                        configurer.column(o.stars(),  "ifnull({0},0)+{1}", (context, sqlParameter) -> {
                            context.expression(o.stars())
                                    .value(sqlParameter);
                        });
                    })
                    .executeRows();
            Assert.assertEquals(1, rows);
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `xxxxx` SET `stars` = ifnull(`stars`,0)+?,`title` = ?,`create_time` = ? WHERE `id` = ?", sql);
        }
    }

    @Test
    public void updateTest19_1() {
        try {
            Topic topic = new Topic();
            long rows = easyEntityQuery.updatable(topic)
                    .asTable("xxxxx")
                    .columnConfigure((t, o) -> o.column(t.stars(), "ifnull({0},0)+{1}", (context, sqlParameter) -> {
                        context.expression(t.stars())
                                .value(sqlParameter);
                    }))
                    .executeRows();
            Assert.assertEquals(1, rows);
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `xxxxx` SET `stars` = ifnull(`stars`,0)+?,`title` = ?,`create_time` = ? WHERE `id` = ?", sql);
        }
    }

    @Test
    public void updateTest20() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq( "7")).firstNotNull("未找到对应的数据");
            boolean b = easyEntityQuery.addTracking(topic);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            String sql = (easyEntityQuery.updatable(topic))
                    .whereColumns(o -> o.FETCHER.id().title())
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ? AND `title` = ?", sql);
            long l = easyEntityQuery.updatable(topic).executeRows();
            Assert.assertEquals(1, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest21() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq( "7")).firstNotNull("未找到对应的数据");
            boolean b = easyEntityQuery.addTracking(topic);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            (easyEntityQuery.updatable(topic))
                    .asTable("x1234")
                    .whereColumns(o -> o.FETCHER.id().title())
                    .executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `x1234` SET `title` = ? WHERE `id` = ? AND `title` = ?", sql);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest21_1() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq("7")).firstNotNull("未找到对应的数据");
            boolean b = easyEntityQuery.addTracking(topic);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            easyEntityQuery.updatable(topic)
                    .asTable("x1234")
                    .whereColumns(o -> o.FETCHER.id().title())
                    .executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `x1234` SET `title` = ? WHERE `id` = ? AND `title` = ?", sql);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest22() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq( "7")).firstNotNull("未找到对应的数据");
            boolean b = easyEntityQuery.addTracking(topic);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            (easyEntityQuery.updatable(topic))
                    .asTable("x123")
                    .whereColumns(o -> o.id())
                    .executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `x123` SET `title` = ? WHERE `id` = ?", sql);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest22_1() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq("7")).firstNotNull("未找到对应的数据");
            boolean b = easyEntityQuery.addTracking(topic);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            easyEntityQuery.updatable(topic)
                    .asTable("x123")
                    .whereColumns(o -> o.id())
                    .executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `x123` SET `title` = ? WHERE `id` = ?", sql);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest23() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq( "7")).firstNotNull("未找到对应的数据");
            boolean b = easyEntityQuery.addTracking(topic);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            (easyEntityQuery.updatable(topic))
                    .asTable("x123")
                    .whereColumns(o -> o.FETCHER.title())
                    .executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `x123` SET `title` = ? WHERE `title` = ?", sql);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest23_1() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq("7")).firstNotNull("未找到对应的数据");
            boolean b = easyEntityQuery.addTracking(topic);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            (easyEntityQuery.updatable(topic))
                    .asTable("x123")
                    .whereColumns(o -> o.title())
                    .executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `x123` SET `title` = ? WHERE `title` = ?", sql);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest24() {
        try {

            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq( "7")).firstNotNull("未找到对应的数据");
            boolean b = easyEntityQuery.addTracking(topic);
            Assert.assertFalse(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            (easyEntityQuery.updatable(topic))
                    .asTable("x123")
                    .whereColumns(o -> o.FETCHER.title())
                    .executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `x123` SET `stars` = ?,`create_time` = ? WHERE `title` = ?", sql);
        }
    }

    @Test
    public void updateTest24_1() {
        try {

            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .where(o -> o.id().eq("7")).firstNotNull("未找到对应的数据");
            boolean b = easyEntityQuery.addTracking(topic);
            Assert.assertFalse(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            (easyEntityQuery.updatable(topic))
                    .asTable("x123")
                    .whereColumns(o -> o.title())
                    .executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `x123` SET `stars` = ?,`create_time` = ? WHERE `title` = ?", sql);
        }
    }

    @Test
    public void updateTest25() {
        try {
            SysUserSQLEncryption user = new SysUserSQLEncryption();
            user.setId("123");
            user.setUsername("username");
            user.setPhone("13232456789");
            user.setIdCard("12345678");
            user.setAddress("xxxxxxx");
            user.setCreateTime(LocalDateTime.now());
            long rows = easyEntityQuery.updatable(user)
                    .asTable("xxxxx")
                    .executeRows();
            Assert.assertEquals(1, rows);
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `xxxxx` SET `username` = ?,`phone` = to_base64(AES_ENCRYPT(?,?)),`id_card` = ?,`address` = ?,`create_time` = ? WHERE `id` = ?", sql);
        }
    }

    @Test
    public void updateTest25_1() {
        try {
            SysUserSQLEncryption user = new SysUserSQLEncryption();
            user.setId("123");
            user.setUsername("username");
            user.setPhone("13232456789");
            user.setIdCard("12345678");
            user.setAddress("xxxxxxx");
            user.setCreateTime(LocalDateTime.now());
            long rows = easyEntityQuery.updatable(user)
                    .asTable("xxxxx")
                    .executeRows();
            Assert.assertEquals(1, rows);
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `xxxxx` SET `username` = ?,`phone` = to_base64(AES_ENCRYPT(?,?)),`id_card` = ?,`address` = ?,`create_time` = ? WHERE `id` = ?", sql);
        }
    }

    @Test
    public void updateTest26() {
        try {

            SysUserSQLEncryption topic = easyEntityQuery.queryable(SysUserSQLEncryption.class)
                    .asTable("x123")
                    .where(o -> o.id().eq( "7")).firstNotNull("未找到对应的数据");
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("SELECT `id`,`username`,AES_DECRYPT(from_base64(`phone`),?) AS `phone`,`id_card`,`address`,`create_time` FROM `x123` WHERE `id` = ? LIMIT 1", sql);
        }
    }

    @Test
    public void updateTest26_1() {
        try {

            SysUserSQLEncryption topic = easyEntityQuery.queryable(SysUserSQLEncryption.class)
                    .asTable("x123")
                    .where(o -> o.id().eq("7")).firstNotNull("未找到对应的数据");
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("SELECT `id`,`username`,AES_DECRYPT(from_base64(`phone`),?) AS `phone`,`id_card`,`address`,`create_time` FROM `x123` WHERE `id` = ? LIMIT 1", sql);
        }
    }

    @Test
    public void updateTest27() {
        try {

            easyEntityQuery.updatable(SysUserSQLEncryption.class)
                    .asTable("x123")
                    .setColumns(s -> s.phone().set("123123"))
                    .where(o -> o.id().eq( "7")).executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `x123` SET `phone` = to_base64(AES_ENCRYPT(?,?)) WHERE `id` = ?", sql);
        }
    }

    @Test
    public void updateTest27_1() {
        try {

            easyEntityQuery.updatable(SysUserSQLEncryption.class)
                    .asTable("x123")
                    .setColumns(o -> {
                        o.phone().set("123123");
                    })
                    .where(o -> o.id().eq("7")).executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `x123` SET `phone` = to_base64(AES_ENCRYPT(?,?)) WHERE `id` = ?", sql);
        }
    }


    @Test
    public void updateTest28() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            TopicLarge topicLarge = new TopicLarge();
            topicLarge.setId("1");
            topicLarge.setStars(1);
            topicLarge.setTitle("1");
            topicLarge.setTitle1("2");
            topicLarge.setCreateTime(LocalDateTime.now());
            easyEntityQuery.addTracking(topicLarge);
            topicLarge.setTitle("xx");
//            String newTitle = "test123" + new Random().nextInt(100000);
//            topic.setTitle(newTitle);
            String sql = (easyEntityQuery.updatable(topicLarge))
                    .toSQL(topicLarge);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ?", sql);

        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest28_1() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            TopicLarge topicLarge = new TopicLarge();
            topicLarge.setId("1");
            topicLarge.setStars(1);
            topicLarge.setTitle("1");
            topicLarge.setTitle1("2");
            topicLarge.setCreateTime(LocalDateTime.now());
            easyEntityQuery.addTracking(topicLarge);
            topicLarge.setTitle("xx");
//            String newTitle = "test123" + new Random().nextInt(100000);
//            topic.setTitle(newTitle);
            String sql = (easyEntityQuery.updatable(topicLarge))
                    .toSQL(topicLarge);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ?", sql);

        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest29() {
        TopicLarge topicLarge = new TopicLarge();
        topicLarge.setId("1");
        topicLarge.setStars(1);
        topicLarge.setTitle("1");
        topicLarge.setTitle1("2");
        topicLarge.setCreateTime(LocalDateTime.now());
        topicLarge.setTitle("xx");
//            String newTitle = "test123" + new Random().nextInt(100000);
//            topic.setTitle(newTitle);
        String sql = (easyEntityQuery.updatable(topicLarge))
                .toSQL(topicLarge);
        Assert.assertEquals("UPDATE `t_topic` SET `stars` = ?,`create_time` = ? WHERE `id` = ?", sql);
    }

    @Test
    public void updateTest29_1() {
        TopicLarge topicLarge = new TopicLarge();
        topicLarge.setId("1");
        topicLarge.setStars(1);
        topicLarge.setTitle("1");
        topicLarge.setTitle1("2");
        topicLarge.setCreateTime(LocalDateTime.now());
        topicLarge.setTitle("xx");
//            String newTitle = "test123" + new Random().nextInt(100000);
//            topic.setTitle(newTitle);
        String sql = (easyEntityQuery.updatable(topicLarge))
                .toSQL(topicLarge);
        Assert.assertEquals("UPDATE `t_topic` SET `stars` = ?,`create_time` = ? WHERE `id` = ?", sql);
    }

    @Test
    public void updateTest30() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {
            BlogEntity blogEntity = new BlogEntity();
            blogEntity.setId("123123123x");
            blogEntity.setContent("123");
            blogEntity.setScore(new BigDecimal("1.0"));

            trackManager.begin();

            boolean b = easyEntityQuery.addTracking(blogEntity);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            blogEntity.setContent("111");
            blogEntity.setScore(new BigDecimal("1"));//1.0和1一样
            ( easyEntityQuery.updatable(blogEntity))
                    .asTable("x1234")
                    .executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `x1234` SET `content` = ? WHERE `deleted` = ? AND `id` = ?", sql);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest31() {
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {
            BlogEntity blogEntity = new BlogEntity();
            blogEntity.setId("123123123x");
            blogEntity.setContent("123");
            blogEntity.setScore(new BigDecimal("1.0"));

            trackManager.begin();

            boolean b = easyEntityQuery.addTracking(blogEntity);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            blogEntity.setContent("111");
            blogEntity.setScore(new BigDecimal("1"));//1.0和1一样
            easyEntityQuery.updatable(blogEntity)
                    .asTable("x1234")
                    .executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `x1234` SET `content` = ? WHERE `deleted` = ? AND `id` = ?", sql);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void mapUpdateTest1() {
        try {
            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("id", "123");
            stringObjectHashMap.put("name", "123");
            easyEntityQuery.mapUpdatable(stringObjectHashMap).asTable("aaa")
                    .whereColumns("id")
                    .executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `aaa` SET `name` = ? WHERE `id` = ?", sql);
        }
    }

    @Test
    public void mapUpdateTest2() {
        try {

            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("id", "123");
            stringObjectHashMap.put("name", "123");
            stringObjectHashMap.put("name1", null);
            easyEntityQuery.mapUpdatable(stringObjectHashMap).asTable("aaa")
                    .setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS)
                    .whereColumns("id")
                    .executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `aaa` SET `name` = ? WHERE `id` = ?", sql);
        }
    }

    @Test
    public void mapUpdateTest3() {
        try {

            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("id", "123");
            stringObjectHashMap.put("name", "123");
            stringObjectHashMap.put("name1", null);
            easyEntityQuery.mapUpdatable(stringObjectHashMap).asTable("aaa")
                    .setSQLStrategy(SQLExecuteStrategyEnum.ALL_COLUMNS)
                    .whereColumns("id")
                    .executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `aaa` SET `name` = ?,`name1` = ? WHERE `id` = ?", sql);
        }
    }

    @Test
    public void mapUpdateTest4() {

        long rows = easyEntityQuery.updatable(Topic.class)
                .setColumns(s->s.stars().set(12))
                .where(o -> o.id().eq( "2"))
                .executeRows();
        Assert.assertEquals(1, rows);
        List<Map<String, Object>> updates = new ArrayList<>();
        HashMap<String, Object> updateMap = new HashMap<>();
        updateMap.put("id", "2");
        updateMap.put("stars", 12);
        updates.add(updateMap);
        HashMap<String, Object> update1Map = new HashMap<>();
        update1Map.put("id", "2");
        update1Map.put("stars", 12);
        updates.add(update1Map);
        easyEntityQuery.mapUpdatable(updates)
                .asTable("t_topic")
                .whereColumns("id")
                .batch()
                .executeRows();
    }

    @Test
    public void mapUpdateTest5() {


        Supplier<Exception> f = () -> {
            try {
                List<Map<String, Object>> updates = new ArrayList<>();
                HashMap<String, Object> updateMap = new HashMap<>();
                updateMap.put("id", "2");
                updateMap.put("stars", null);
                updates.add(updateMap);
                easyEntityQuery.mapUpdatable(updates)
                        .asTable("t_topic")
                        .whereColumns("id")
                        .batch()
                        .executeRows();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        Assert.assertEquals("UPDATE `t_topic` SET `stars` = ? WHERE `id` = ?", easyQuerySQLStatementException.getSQL());


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Map<String, Object>> updates = new ArrayList<>();
            HashMap<String, Object> updateMap = new HashMap<>();
            updateMap.put("id", "2");
            updateMap.put("stars", 12);
            updates.add(updateMap);
            easyEntityQuery.mapUpdatable(updates)
                    .asTable("t_topic")
                    .whereColumns("id")
                    .batch()
                    .executeRows();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("UPDATE `t_topic` SET `stars` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals(1, jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().size());
            Assert.assertEquals("12(Integer),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }

    @Test
    public void updateTestBatch() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        ArrayList<Topic> topics = new ArrayList<>();
        for (int i = 0; i < 100; i++) {

            Topic topic = new Topic();
            topic.setId("1234567890x" + i);
            topic.setStars(1);
            topic.setTitle("1234567890x");
            topic.setCreateTime(null);
            topics.add(topic);
        }
        long l = easyEntityQuery.updatable(topics)
                .batch()
                .ignoreVersion()
                .executeRows();
        System.out.println(l);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        int i = 0;
        for (List<SQLParameter> sqlParameter : jdbcExecuteAfterArg.getBeforeArg().getSqlParameters()) {
            Assert.assertEquals("1(Integer),1234567890x(String),null(null),1234567890x" + i + "(String)", EasySQLUtil.sqlParameterToString(sqlParameter));
            i++;
        }
    }

    @Test
    public void updateTestBatch1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        ArrayList<Topic> topics = new ArrayList<>();
        for (int i = 0; i < 100; i++) {

            Topic topic = new Topic();
            topic.setId("1234567890x" + i);
            topic.setStars(1);
            topic.setTitle("1234567890x");
            topic.setCreateTime(null);
            topics.add(topic);
        }
        long l = easyEntityQuery.updatable(topics)
                .setColumns(o -> o.stars())
                .batch()
                .ignoreVersion()
                .executeRows();
        System.out.println(l);
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        int i = 0;
        for (List<SQLParameter> sqlParameter : jdbcExecuteAfterArg.getBeforeArg().getSqlParameters()) {
            Assert.assertEquals("1(Integer),1234567890x" + i + "(String)", EasySQLUtil.sqlParameterToString(sqlParameter));
            i++;
        }
    }

    @Test
    public void updateTestBatch2() {
        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            ArrayList<Topic> topics = new ArrayList<>();
            for (int i = 0; i < 100; i++) {

                Topic topic = new Topic();
                topic.setId("1234567890x" + i);
                topic.setStars(1);
                topic.setTitle("1234567890x" + i);
                topic.setCreateTime(null);
                topics.add(topic);
            }
            long l = easyEntityQuery.updatable(topics)
                    .setColumns(o -> o.title())
                    .batch()
                    .ignoreVersion()
                    .executeRows();
            System.out.println(l);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            int i = 0;
            for (List<SQLParameter> sqlParameter : jdbcExecuteAfterArg.getBeforeArg().getSqlParameters()) {
                Assert.assertEquals("1234567890x" + i + "(String),1234567890x" + i + "(String)", EasySQLUtil.sqlParameterToString(sqlParameter));
                i++;
            }
        }

        {
            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            ArrayList<Topic> topics = new ArrayList<>();
            for (int i = 0; i < 100; i++) {

                Topic topic = new Topic();
                topic.setId("1234567890x" + i);
                topic.setStars(1);
                topic.setTitle("1234567890x" + i);
                topic.setCreateTime(null);
                topics.add(topic);
            }
            long l = easyEntityQuery.updatable(topics)
                    .setColumns(o -> o.FETCHER.title())
                    .batch()
                    .ignoreVersion()
                    .executeRows();
            System.out.println(l);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            int i = 0;
            for (List<SQLParameter> sqlParameter : jdbcExecuteAfterArg.getBeforeArg().getSqlParameters()) {
                Assert.assertEquals("1234567890x" + i + "(String),1234567890x" + i + "(String)", EasySQLUtil.sqlParameterToString(sqlParameter));
                i++;
            }
        }
    }

    @Test
    public void updateSet1() {

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            long rows = easyEntityQuery.updatable(Topic.class)
                    .setColumns(o -> {
                        o.stars().set(1);
                        o.stars().set(o.title().toNumber(Integer.class));
                        o.title().set("1");
                        o.title().set(o.createTime().format("yyyy/MM/dd"));
                        o.title().set(o.title().nullOrDefault("x"));
                        o.title().setSQL("IFNULL({0},{1})", c -> {
                            c.expression(o.title());
                            c.value("p");
                        });
                    })
                    .where(o -> {
                        o.id().eq("2");
                        o.title().eq("2c");
                    })
                    .executeRows();
            Assert.assertEquals(0, rows);
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("UPDATE `t_topic` SET `stars` = ?,`stars` = CAST(`title` AS SIGNED),`title` = ?,`title` = DATE_FORMAT(`create_time`,'%Y/%m/%d'),`title` = IFNULL(`title`,?),`title` = IFNULL(`title`,?) WHERE `id` = ? AND `title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(Integer),1(String),x(String),p(String),2(String),2c(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }
    }


    @Test
    public void updateTrackParameterTest1() {
        String newTitle = "test123" + new Random().nextInt(100);
        String oldTitle = null;
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {

            trackManager.begin();
            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .asTracking()
                    .where(o -> o.id().eq( "7")).firstNotNull("未找到对应的数据");
            oldTitle = topic.getTitle();
            topic.setTitle(newTitle);
            easyEntityQuery.updatable(topic)
                    .asTable("aaaxxx")
                    .whereColumns(o -> o.FETCHER.columnKeys().title())
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        } finally {
            trackManager.release();
        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `aaaxxx` SET `title` = ? WHERE `id` = ? AND `title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(newTitle + "(String),7(String)," + oldTitle + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void updateTrackParameterTest2() {
        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);
        String newTitle = "test123" + new Random().nextInt(100);
        String oldTitle = null;
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .asTracking()
                    .where(o -> o.id().eq( "7")).firstNotNull("未找到对应的数据");
            oldTitle = topic.getTitle();
            topic.setTitle(newTitle);
            Topic topic1 = easyEntityQuery.queryable(Topic.class)
                    .asNoTracking()
                    .where(o -> o.id().eq( "8")).firstNotNull("未找到对应的数据");
            topic1.setTitle(newTitle);
            easyEntityQuery.updatable(Arrays.asList(topic, topic1))
                    .asTable("aaaxxx")
                    .whereColumns(o -> o.FETCHER.columnKeys().title())
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        } finally {
            trackManager.release();
        }

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        List<JdbcExecuteAfterArg> jdbcExecuteAfterArgs = listenerContext.getJdbcExecuteAfterArgs();
        Assert.assertEquals(3, jdbcExecuteAfterArgs.size());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = jdbcExecuteAfterArgs.get(2);
        if (Objects.equals(newTitle + "(String),7(String)," + oldTitle + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)))) {
            Assert.assertEquals("UPDATE `aaaxxx` SET `title` = ? WHERE `id` = ? AND `title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        } else {
            Assert.assertEquals("UPDATE `aaaxxx` SET `stars` = ?,`create_time` = ? WHERE `id` = ? AND `title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("108(Integer),2023-06-01T10:48:05(LocalDateTime),8(String)," + newTitle + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        listenerContextManager.clear();
    }

    @Test
    public void updateTrackParameterTest3() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        String newTitle = "test123" + new Random().nextInt(100);
        String oldTitle = null;
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyEntityQuery.queryable(Topic.class)
                    .asTracking()
                    .where(o -> o.id().eq( "7")).firstNotNull("未找到对应的数据");
            oldTitle = topic.getTitle();
            topic.setTitle(newTitle);
            Topic topic1 = easyEntityQuery.queryable(Topic.class)
                    .asNoTracking()
                    .where(o -> o.id().eq( "8")).firstNotNull("未找到对应的数据");
            topic1.setTitle(newTitle);
            easyEntityQuery.updatable(Arrays.asList(topic1, topic))
                    .asTable("aaaxxx")
                    .whereColumns(o -> o.FETCHER.columnKeys().title())
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        } finally {
            trackManager.release();
        }

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `aaaxxx` SET `stars` = ?,`create_time` = ? WHERE `id` = ? AND `title` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("108(Integer),2023-06-01T10:48:05(LocalDateTime),8(String)," + newTitle + "(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void updateSetFuncTest1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {
            easyEntityQuery.updatable(Topic.class)
                    .setColumns(t -> {
                        t.title().set(
                                t.title().subString(1, 10).concat(t.id())
                        );
                    }).asTable("a123123")
                    .whereById("123zzzxxx")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        }

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `a123123` SET `title` = CONCAT(SUBSTR(`title`,2,10),`id`) WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123zzzxxx(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void updateSetFuncTest1_1() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {
            easyEntityQuery.updatable(Topic.class)
                    .setColumns(t -> {
                        Expression expression = t.expression();
                        t.title().set(
                                expression.concat(o->o.expression(t.id()).expression(t.title().subString(1, 10)))
                        );
                    }).asTable("a123123")
                    .whereById("123zzzxxx")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        }

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `a123123` SET `title` = CONCAT(`id`,SUBSTR(`title`,2,10)) WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123zzzxxx(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void updateSetFuncTest2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {
            easyEntityQuery.updatable(Topic.class)
                    .setColumns(t -> {
                        t.title().set(t.title().subString(1, 10).concat("123"));
                    }).asTable("a123123")
                    .whereById("123zzzxxx")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        }

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `a123123` SET `title` = CONCAT(SUBSTR(`title`,2,10),?) WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123zzzxxx(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void updateSetFuncTest3() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {
            easyEntityQuery.updatable(Topic.class)
                    .setColumns(t -> {
                        t.title().set(t.title().subString(1, 10).concat(t.id().toLower()));
                    }).asTable("a123123")
                    .whereById("123zzzxxx")
                    .executeRows();
        } catch (Exception ex) {
            Assert.assertNotNull(ex);
        }

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `a123123` SET `title` = CONCAT(SUBSTR(`title`,2,10),LOWER(`id`)) WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123zzzxxx(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
     public void testUpdateIdNull1(){

         ListenerContext listenerContext = new ListenerContext();
         listenerContextManager.startListen(listenerContext);
             easyEntityQuery.updatable(Topic.class)
                     .setColumns(t -> {
                         t.title().set(t.title().subString(1, 10).concat(t.id().toLower()));
                     })
                     .whereById(null)
                     .executeRows();
         Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
         JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
         Assert.assertEquals("UPDATE `t_topic` SET `title` = CONCAT(SUBSTR(`title`,2,10),LOWER(`id`)) WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
         Assert.assertEquals("null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
         listenerContextManager.clear();


    }
    @Test
    public void testColumnConfigure1() {
        try {
            Topic topic = new Topic();
            long rows = easyEntityQuery.insertable(topic)
                    .asTable("xxxxx")
                    .setSQLStrategy(SQLExecuteStrategyEnum.ALL_COLUMNS)
                    .columnConfigure((t, o) -> o.column(t.stars(), "ifnull({0},0)+{1}", (context, sqlParameter) -> {
                        context.expression(t.stars())
                                .value(sqlParameter);
                    })).executeRows();

            Assert.assertEquals(1, rows);
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("INSERT INTO `xxxxx` (`id`,`stars`,`title`,`create_time`) VALUES (?,ifnull(`stars`,0)+?,?,?)", sql);
        }
    }
    @Test
    public void testColumnConfigure2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {
            LinkedHashMap<String,Object> map = new LinkedHashMap<>();
            map.put("id","1");
            map.put("stars","2");

            long rows = easyQueryClient.mapUpdatable(map)
                    .asTable("xxxxx")
                    .columnConfigure(o -> o.column("stars", "ifnull({0},0)+{1}", (context, sqlParameter) -> {
                        context.expression("stars")
                                .value(sqlParameter);
                    }))
                    .whereColumns("id")
                    .executeRows();

            Assert.assertEquals(1, rows);
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `xxxxx` SET `stars` = ifnull(`stars`,0)+? WHERE `id` = ?", sql);
        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `xxxxx` SET `stars` = ifnull(`stars`,0)+? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("2(String),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void testColumnConfigure3() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {
            LinkedHashMap<String,Object> map = new LinkedHashMap<>();
            map.put("id",new MapValue("123","1"));
            map.put("stars","2");

            long rows = easyQueryClient.mapUpdatable(map)
                    .asTable("xxxxx")
                    .whereColumns("id")
                    .executeRows();

            Assert.assertEquals(1, rows);
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("UPDATE `xxxxx` SET `id` = ?,`stars` = ? WHERE `id` = ?", sql);
        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `xxxxx` SET `id` = ?,`stars` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),2(String),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void aaaa(){
//        List<Draft1<String>> list = easyEntityQuery.queryable(BlogEntity.class)
//                .where(b -> {
//                    b.star().eq(1);
//                }).select(b -> Select.DRAFT.of(
//                        b.expression().concat(b.title())
//                )).toList();
//        List<Draft1<String>> list2 = easyEntityQuery.queryable(BlogEntity.class)
//                .where(b -> {
//                    b.star().eq(1);
//                }).select(b -> Select.DRAFT.of(
//                        b.id().concat(b.title())
//                )).toList();
//        BlogEntity blogEntity = new BlogEntity();
//        EntityMetadataManager entityMetadataManager = easyEntityQuery.getRuntimeContext().getEntityMetadataManager();
//        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(blogEntity.getClass());
//        Collection<String> keyProperties = entityMetadata.getKeyProperties();
//        LinkedHashMap<String, Object> keyMap = new LinkedHashMap<>();
//        for (String keyProperty : keyProperties) {
//            Property<Object, ?> getterCaller = entityMetadata.getColumnNotNull(keyProperty).getGetterCaller();
//            Object key = getterCaller.apply(blogEntity);
//            keyMap.put(keyProperty,key);
//        }
//        easyEntityQuery.getEasyQueryClient().queryable(BlogEntity.class)
//                .whereById("123").toList();

        List<SysMenuDto> list2 = easyEntityQuery.queryable(BlogEntity.class)
                .leftJoin(BlogEntity.class,(a,b)->a.id().eq(b.id()))
                .leftJoin(BlogEntity.class,(a,b,c)->a.id().eq(b.id()))
                .leftJoin(BlogEntity.class,(a,b,c,d)->a.id().eq(b.id()))
                .where(b -> {
                    b.star().eq(1);
                }).select(SysMenuDto.class,(a, b, c, d) -> Select.of(
                        a.FETCHER.allFields(),
                        a.expression().concat(s->{
                            s.value("1").expression(d.title()).value("1");
                        }).as(SysMenuDto::getSourceName)
                )).toList();
    }

    @Test
    public void mapUpdateTest511() {


        Supplier<Exception> f = () -> {
            try {
                List<Map<String, Object>> updates = new ArrayList<>();
                HashMap<String, Object> updateMap = new HashMap<>();
                updateMap.put("ID", "2");
                updateMap.put("stars", null);
                updates.add(updateMap);
                easyEntityQuery.mapUpdatable(updates)
                        .asTable("t_topic")
                        .whereColumns("id")
                        .batch()
                        .executeRows();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQueryInvalidOperationException);
        EasyQueryInvalidOperationException exception1 = (EasyQueryInvalidOperationException) exception;
        String message = exception1.getMessage();
        System.out.println(message);
        Assert.assertEquals("where column:id not found in map",message);

    }

    @Test
     public void updateEntityWhere(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setId("123xoiuy");
        blogEntity.setTitle("title");
        blogEntity.setContent("content");
        blogEntity.setStatus(1);
        easyEntityQuery.updatable(blogEntity)
                .where(t_blog -> {
                    t_blog.status().eq(2);
                }).executeRows();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `t_blog` SET `create_time` = ?,`update_time` = ?,`create_by` = ?,`update_by` = ?,`title` = ?,`content` = ?,`url` = ?,`star` = ?,`publish_time` = ?,`score` = ?,`status` = ?,`order` = ?,`is_top` = ?,`top` = ? WHERE `deleted` = ? AND `id` = ? AND `status` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("null(null),null(null),null(null),null(null),title(String),content(String),null(null),null(null),null(null),null(null),1(Integer),null(null),null(null),null(null),false(Boolean),123xoiuy(String),2(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

}
