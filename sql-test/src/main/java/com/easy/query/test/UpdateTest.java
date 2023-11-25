package com.easy.query.test;

import com.easy.query.api4j.update.ExpressionUpdatable;
import com.easy.query.api4j.update.impl.EasyEntityUpdatable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryColumnValueUpdateAtomicTrackException;
import com.easy.query.core.exception.EasyQueryConcurrentException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.exception.EasyQueryTableNotInSQLContextException;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.SysUserSQLEncryption;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicAuto;
import com.easy.query.test.entity.TopicLarge;
import com.easy.query.test.entity.TopicTypeTest1;
import com.easy.query.test.entity.TopicTypeTest2;
import com.easy.query.test.entity.TopicValueUpdateAtomicTrack;
import com.easy.query.test.entity.TopicValueUpdateAtomicTrackIgnore;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.enums.TopicTypeEnum;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * @author xuejiaming
 * @FileName: UpdateTest.java
 * @Description: 文件说明
 * @Date: 2023/3/20 21:58
 */
public class UpdateTest extends BaseTest {

    @Test
    public void updateTest1() {
        long rows = easyQuery.updatable(Topic.class)
                .set(Topic::getStars, 12)
                .where(o -> o.eq(Topic::getId, "2"))
                .executeRows();
        Assert.assertEquals(1, rows);
    }

    @Test
    public void updateTest1_1() {
        TopicProxy table = TopicProxy.createTable();
        String sql = easyProxyQuery.updatable(table)
                .set(table.stars(), 123)
                .where(o -> o.eq(table.id(), "2"))
                .toSQL();
        Assert.assertEquals("UPDATE `t_topic` SET `stars` = ? WHERE `id` = ?",sql);
    }
    @Test
    public void updateTest1_2() {
        TopicProxy table = TopicProxy.createTable();
        String sql = easyProxyQuery.updatable(table)
                .set(table.stars(), 123)
                .where(o -> o.eq(table.id(), "2"))
                .toSQL();
        Assert.assertEquals("UPDATE `t_topic` SET `stars` = ? WHERE `id` = ?",sql);
        try {
            String sql1 = easyProxyQuery.updatable(table)
                    .set(table.stars(), 123)
                    .where(o -> o.eq(table.id(), "2"))
                    .toSQL();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQueryTableNotInSQLContextException);
            Assert.assertTrue(ex.getMessage().startsWith("not found table:[Topic:"));
            Assert.assertTrue(ex.getMessage().endsWith("] in sql context"));
        }
    }

    @Test
    public void updateTest2() {
        long rows = easyQuery.updatable(Topic.class)
                .set(Topic::getStars, 12)
                .where(o -> o.eq(Topic::getId, "2x"))
                .executeRows();
        Assert.assertEquals(0, rows);
    }

    @Test
    public void updateTest3() {
        Topic topic = easyQuery.queryable(Topic.class).whereById("3").firstOrNull();
        Assert.assertNotNull(topic);
        Assert.assertEquals("3", topic.getId());
        long rows = easyQuery.updatable(Topic.class)
                .setWithColumn(Topic::getTitle, Topic::getStars)
                .where(o -> o.eq(Topic::getId, topic.getId()))
                .executeRows();
        Assert.assertEquals(1, rows);
        Topic topic2 = easyQuery.queryable(Topic.class).whereById("3").firstOrNull();
        Assert.assertNotNull(topic2);
        Assert.assertEquals(String.valueOf(topic.getStars()), topic2.getTitle());
    }

    @Test
    public void updateTest4() {
        long rows = easyQuery.updatable(Topic.class)
                .set(Topic::getStars, 2)
                .whereById("5").executeRows();
        Assert.assertEquals(1, rows);


        long rows1 = easyQuery.updatable(Topic.class)
                .setIncrement(Topic::getStars)
                .whereById("5").executeRows();
        Assert.assertEquals(1, rows1);
        Topic topic1 = easyQuery.queryable(Topic.class)
                .whereById("5").firstOrNull();
        Assert.assertNotNull(topic1);
        Assert.assertEquals(3, (int) topic1.getStars());

        long rows2 = easyQuery.updatable(Topic.class)
                .setIncrement(Topic::getStars, 2)
                .where(o -> o.eq(Topic::getId, "5")).executeRows();
        Assert.assertEquals(1, rows2);
        Topic topic2 = easyQuery.queryable(Topic.class)
                .whereById("5").firstOrNull();
        Assert.assertNotNull(topic2);
        Assert.assertEquals(5, (int) topic2.getStars());

        long rows3 = easyQuery.updatable(Topic.class)
                .setDecrement(Topic::getStars)
                .where(o -> o.eq(Topic::getId, "5")).executeRows();
        Assert.assertEquals(1, rows3);
        Topic topic3 = easyQuery.queryable(Topic.class)
                .whereById("5").firstOrNull();
        Assert.assertNotNull(topic3);
        Assert.assertEquals(4, (int) topic3.getStars());

        long rows4 = easyQuery.updatable(Topic.class)
                .setDecrement(Topic::getStars, 2)
                .where(o -> o.eq(Topic::getId, "5")).executeRows();
        Assert.assertEquals(1, rows4);
        Topic topic4 = easyQuery.queryable(Topic.class)
                .whereById("5").firstOrNull();
        Assert.assertNotNull(topic4);
        Assert.assertEquals(2, (int) topic4.getStars());
    }

    @Test
    public void updateTest5() {
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "7")).firstNotNull("未找到对应的数据");
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            String sql = ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic))
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `stars` = ?,`title` = ?,`create_time` = ? WHERE `id` = ?", sql);
            long l = easyQuery.updatable(topic).executeRows();
            Assert.assertEquals(1, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest6() {
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "7")).asTracking().firstNotNull("未找到对应的数据");
            String newTitle = "test123" + new Random().nextInt(100000);
            topic.setTitle(newTitle);
            String sql = ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic))
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ?", sql);
            long l = easyQuery.updatable(topic).executeRows();
            Assert.assertEquals(1, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest7() {
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "7")).firstNotNull("未找到对应的数据");
            boolean b = easyQuery.addTracking(topic);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            String sql = ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic))
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ?", sql);
            long l = easyQuery.updatable(topic).executeRows();
            Assert.assertEquals(1, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest8() {
        long l = easyQuery.updatable(Topic.class)
                .set(Topic::getTitle, null)
                .whereById("9").executeRows();
        Assert.assertEquals(1, l);
        Topic topic = easyQuery.queryable(Topic.class)
                .whereById("9").firstOrNull();
        Assert.assertNotNull(topic);
        String updateSql = ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic).setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS))
                .toSQL(topic);
        Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ?", updateSql);
        long l1 = easyQuery.updatable(topic)
                .setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS)
                .executeRows();
        Assert.assertEquals(1, l1);
    }

    @Test
    public void updateTest9() {
        long l = easyQuery.updatable(Topic.class)
                .set(Topic::getTitle, null)
                .whereById("10").executeRows();
        Assert.assertEquals(1, l);
        Topic topic = easyQuery.queryable(Topic.class)
                .whereById("10").firstOrNull();
        Assert.assertNotNull(topic);
        String updateSQL = easyQuery.updatable(topic)
                .setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS)
                .toSQL(topic);
        Assert.assertEquals("UPDATE `t_topic` SET `stars` = ?,`create_time` = ? WHERE `id` = ?", updateSQL);
        long l1 = easyQuery.updatable(topic)
                .setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS)
                .executeRows();
        Assert.assertEquals(1, l1);
    }

    @Test
    public void updateTest10() {
        try {

            easyQuery.updatable(Topic.class)
                    .set(Topic::getStars, 12)
                    .where(o -> o.eq(Topic::getId, UUID.randomUUID().toString()))
                    .executeRows(1, "123");
        } catch (Exception e) {
            Assert.assertEquals(EasyQueryConcurrentException.class, e.getClass());
        }
    }

    @Test
    public void updateTest11() {
        TopicTypeTest1 topicType = easyQuery.queryable(TopicTypeTest1.class)
                .whereById("123").firstOrNull();
        if (topicType != null) {
            long l = easyQuery.deletable(topicType).executeRows();
            Assert.assertEquals(1, l);
        }
        ExpressionUpdatable<TopicTypeTest1> queryable = easyQuery.updatable(TopicTypeTest1.class)
                .set(TopicTypeTest1::getStars, 234)
                .where(o -> o.eq(TopicTypeTest1::getTopicType, TopicTypeEnum.CLASSER));
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(queryable.getExpressionContext().getTableContext());
        String sql = queryable.toSQL(toSQLContext);
        Assert.assertEquals("UPDATE `t_topic_type` SET `stars` = ? WHERE `topic_type` = ?", sql);
        String parameterToString = EasySQLUtil.sqlParameterToString(toSQLContext.getParameters());
        Assert.assertEquals("234(Integer),CLASSER(TopicTypeEnum)", parameterToString);

        long l = easyQuery.updatable(TopicTypeTest1.class)
                .set(TopicTypeTest1::getStars, 234)
                .where(o -> o.eq(TopicTypeTest1::getTopicType, TopicTypeEnum.CLASSER))
                .executeRows();
    }
    @Test
    public void updateTest11_1() {
        TopicTypeTest2 topicType = easyQuery.queryable(TopicTypeTest2.class)
                .whereById("123").firstOrNull();
        if (topicType != null) {
            long l = easyQuery.deletable(topicType).executeRows();
            Assert.assertEquals(1, l);
        }
        ExpressionUpdatable<TopicTypeTest2> queryable = easyQuery.updatable(TopicTypeTest2.class)
                .set(TopicTypeTest2::getStars, 234)
                .where(o -> o.eq(TopicTypeTest2::getTopicType, TopicTypeEnum.CLASSER));
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(queryable.getExpressionContext().getTableContext());
        String sql = queryable.toSQL(toSQLContext);
        Assert.assertEquals("UPDATE `t_topic_type` SET `stars` = ? WHERE `topic_type` = ?", sql);
        String parameterToString = EasySQLUtil.sqlParameterToString(toSQLContext.getParameters());
        Assert.assertEquals("234(Integer),CLASSER(TopicTypeEnum)", parameterToString);

        long l = easyQuery.updatable(TopicTypeTest2.class)
                .set(TopicTypeTest2::getStars, 234)
                .where(o -> o.eq(TopicTypeTest2::getTopicType, TopicTypeEnum.CLASSER))
                .executeRows();
    }

    @Test
    public void updateTest12() {
        Topic topic = easyQuery.queryable(Topic.class).whereById("15").firstOrNull();
        Assert.assertNotNull(topic);
        long rows4 = easyQuery.updatable(topic)
                .setColumns(o -> o.column(Topic::getCreateTime))
                .whereColumns(o -> o.column(Topic::getStars)).executeRows();
        Assert.assertEquals(1, rows4);
    }
    @Test
    public void updateTest12_1() {
        Topic topic = easyQuery.queryable(Topic.class).whereById("15").firstOrNull();
        Assert.assertNotNull(topic);
        String sql = easyProxyQuery.updatable(topic)
                .useProxy(TopicProxy.createTable())
                .whereColumns(o -> o.columnKeys().column(o.t().stars())).toSQL(topic);
        Assert.assertEquals("UPDATE `t_topic` SET `title` = ?,`create_time` = ? WHERE `id` = ? AND `stars` = ?",sql);
    }
    @Test
    public void updateTest12_2() {
        Topic topic = easyQuery.queryable(Topic.class).whereById("15").firstOrNull();
        Assert.assertNotNull(topic);
        String sql = easyProxyQuery.updatableProxy(topic)
                .whereColumns(o -> o.columnKeys().column(o.t().stars())).toSQL(topic);
        Assert.assertEquals("UPDATE `t_topic` SET `title` = ?,`create_time` = ? WHERE `id` = ? AND `stars` = ?",sql);
    }


    @Test
    public void updateTest13() {
        long l = easyQuery.updatable(Topic.class)
                .set(Topic::getTitle, "2123")
                .where(o -> o.exists(easyQuery.queryable("select * from `t_topic`", Topic.class).where(x -> x.eq(o, Topic::getId, Topic::getId))).isNotNull(Topic::getTitle)).executeRows();
        Assert.assertEquals(99, l);
        String sql = easyQuery.updatable(Topic.class)
                .set(Topic::getTitle, "2123")
                .where(o -> o.exists(easyQuery.queryable("select * from `t_topic`", Topic.class).where(x -> x.eq(o, Topic::getId, Topic::getId))).isNotNull(Topic::getTitle)).toSQL();
        Assert.assertEquals("UPDATE `t_topic` t SET t.`title` = ? WHERE EXISTS (SELECT 1 FROM (select * from `t_topic`) t1 WHERE t1.`id` = t.`id`) AND t.`title` IS NOT NULL", sql);
    }

    @Test
    public void updateTest14() {
        long l = easyQuery.updatable(Topic.class)
                .set(Topic::getTitle, "2123")
                .where(o -> o.exists(easyQuery.queryable("select * from `t_topic`", Topic.class).where(x -> x.eq(o, Topic::getId, Topic::getId))).isNull(Topic::getId)).executeRows();
        Assert.assertEquals(0, l);
        String sql = easyQuery.updatable(Topic.class)
                .set(Topic::getTitle, "2123")
                .where(o -> o.exists(easyQuery.queryable("select * from `t_topic`", Topic.class).where(x -> x.eq(o, Topic::getId, Topic::getId))).isNull(Topic::getId)).toSQL();
        Assert.assertEquals("UPDATE `t_topic` t SET t.`title` = ? WHERE EXISTS (SELECT 1 FROM (select * from `t_topic`) t1 WHERE t1.`id` = t.`id`) AND t.`id` IS NULL", sql);
    }

    @Test
    public void updateTest15() {
        TopicValueUpdateAtomicTrack topicValueUpdateAtomicTrack = new TopicValueUpdateAtomicTrack();
        topicValueUpdateAtomicTrack.setId("123");
        topicValueUpdateAtomicTrack.setStars(99);
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {
            trackManager.begin();
            easyQuery.addTracking(topicValueUpdateAtomicTrack);
            topicValueUpdateAtomicTrack.setStars(98);
            long l = easyQuery.updatable(topicValueUpdateAtomicTrack).executeRows();
            System.out.println(l);
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
            Assert.assertEquals("UPDATE `t_topic_value_atomic` SET `stars` = `stars`- ? WHERE `id` = ? AND `stars` >= ?", sql);
        } finally {
            trackManager.release();
        }
    }
    @Test
    public void updateTest15_1() {
        TopicValueUpdateAtomicTrackIgnore topicValueUpdateAtomicTrack = new TopicValueUpdateAtomicTrackIgnore();
        topicValueUpdateAtomicTrack.setId("123");
        topicValueUpdateAtomicTrack.setStars(99);
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {
            trackManager.begin();
            easyQuery.addTracking(topicValueUpdateAtomicTrack);
            topicValueUpdateAtomicTrack.setStars(98);
            long l = easyQuery.updatable(topicValueUpdateAtomicTrack).executeRows();
            System.out.println(l);
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
            Assert.assertEquals("UPDATE `t_topic_value_atomic` SET `stars` = `stars`- ? WHERE `id` = ? AND `stars` >= ?", sql);
        } finally {
            trackManager.release();
        }
    }
    @Test
    public void updateTest15_2() {
        TopicValueUpdateAtomicTrackIgnore topicValueUpdateAtomicTrack = new TopicValueUpdateAtomicTrackIgnore();
        topicValueUpdateAtomicTrack.setId("123");
        topicValueUpdateAtomicTrack.setStars(99);
        Supplier<Exception> queryF=()->{
            try {
                easyQuery.addTracking(topicValueUpdateAtomicTrack);
                topicValueUpdateAtomicTrack.setStars(98);
                long l = easyQuery.updatable(topicValueUpdateAtomicTrack).executeRows();
                System.out.println(l);
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };

        Exception ex = queryF.get();
        Assert.assertNotNull(ex);
        Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
        Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
        String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
        Assert.assertEquals("UPDATE `t_topic_value_atomic` SET `title` = ?,`topic_type` = ?,`create_time` = ? WHERE `id` = ?", sql);
    }
    @Test
    public void updateTest15_3() {
        TopicValueUpdateAtomicTrack topicValueUpdateAtomicTrack = new TopicValueUpdateAtomicTrack();
        topicValueUpdateAtomicTrack.setId("123");
        topicValueUpdateAtomicTrack.setStars(99);
        try {
            easyQuery.addTracking(topicValueUpdateAtomicTrack);
            topicValueUpdateAtomicTrack.setStars(98);
            long l = easyQuery.updatable(topicValueUpdateAtomicTrack).executeRows();
            System.out.println(l);
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof EasyQueryColumnValueUpdateAtomicTrackException);
            Assert.assertEquals("entity:TopicValueUpdateAtomicTrack property:[stars] has configure value update atomic track，but current update not use track update.",ex.getMessage());
        }
    }

    @Test
    public void updateTest16() {
        TopicAuto topicAuto = null;
        long l = easyQuery.updatable(topicAuto)
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
//        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
//        try {
//            trackManager.begin();
//            easyQuery.addTracking(topicValueUpdateAtomicTrack);
//            topicValueUpdateAtomicTrack.setStars(98);
//            long l = easyQuery.updatable(topicValueUpdateAtomicTrack).executeRows();
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
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = new Topic();
            topic.setId("123xx");
            easyQuery.addTracking(topic);
            String newTitle = "test123" + new Random().nextInt(100000);
            topic.setTitle(newTitle);
            String sql = ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic))
                    .whereColumns(o -> o.column(Topic::getId).column(Topic::getTitle))
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ? AND `title` IS NULL", sql);
            long l = easyQuery.updatable(topic).executeRows();
            Assert.assertEquals(0, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest17_1() {
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = new Topic();
            topic.setId("123xx");
            easyQuery.addTracking(topic);
            String newTitle = "test123" + new Random().nextInt(100000);
            topic.setTitle(newTitle);
            String sql = ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic))
                    .whereColumns(o ->{
                        Assert.assertNotNull(o.getTable());
                        o.columnKeys().column(Topic::getTitle);
                    })
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ? AND `title` IS NULL", sql);
            long l = easyQuery.updatable(topic).executeRows();
            Assert.assertEquals(0, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }
    @Test
    public void updateTest17_2() {
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = new Topic();
            topic.setId("123xx");
            easyQuery.addTracking(topic);
            String newTitle = "test123" + new Random().nextInt(100000);
            topic.setTitle(newTitle);
            String sql = ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic))
                    .whereColumns(o ->{
                        o.columnAll().columnIgnore(Topic::getTitle);
                    })
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ? AND `stars` IS NULL AND `create_time` IS NULL AND `alias` IS NULL", sql);
            long l = easyQuery.updatable(topic).executeRows();
            Assert.assertEquals(0, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest18() {
        try {

            long rows = easyQuery.updatable(Topic.class)
                    .asTable("xxxxx")
                    .setSQLSegment(Topic::getStars, "ifnull({0},0)+{1}", (context) -> {
                        context.expression(Topic::getStars)
                                .value(1);
                    })
                    .where(o -> o.eq(Topic::getId, "2"))
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
            long rows = easyQuery.updatable(topic)
                    .asTable("xxxxx")
                    .columnConfigure(o -> o.column(Topic::getStars, "ifnull({0},0)+{1}", (context, sqlParameter) -> {
                        context.expression(Topic::getStars)
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
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "7")).firstNotNull("未找到对应的数据");
            boolean b = easyQuery.addTracking(topic);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            String sql = ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic))
                    .whereColumns(o -> o.column(Topic::getId).column(Topic::getTitle))
                    .toSQL(topic);
            Assert.assertEquals("UPDATE `t_topic` SET `title` = ? WHERE `id` = ? AND `title` = ?", sql);
            long l = easyQuery.updatable(topic).executeRows();
            Assert.assertEquals(1, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }

    @Test
    public void updateTest21() {
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "7")).firstNotNull("未找到对应的数据");
            boolean b = easyQuery.addTracking(topic);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic))
                    .asTable("x1234")
                    .whereColumns(o -> o.column(Topic::getId).column(Topic::getTitle))
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
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "7")).firstNotNull("未找到对应的数据");
            boolean b = easyQuery.addTracking(topic);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic))
                    .asTable("x123")
                    .whereColumns(o -> o.column(Topic::getId))
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
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "7")).firstNotNull("未找到对应的数据");
            boolean b = easyQuery.addTracking(topic);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic))
                    .asTable("x123")
                    .whereColumns(o -> o.column(Topic::getTitle))
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

            Topic topic = easyQuery.queryable(Topic.class)
                    .where(o -> o.eq(Topic::getId, "7")).firstNotNull("未找到对应的数据");
            boolean b = easyQuery.addTracking(topic);
            Assert.assertFalse(b);
            String newTitle = "test123" + new Random().nextInt(100);
            topic.setTitle(newTitle);
            ((EasyEntityUpdatable<Topic>) easyQuery.updatable(topic))
                    .asTable("x123")
                    .whereColumns(o -> o.column(Topic::getTitle))
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
            long rows = easyQuery.updatable(user)
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

            SysUserSQLEncryption topic = easyQuery.queryable(SysUserSQLEncryption.class)
                    .asTable("x123")
                    .where(o -> o.eq(SysUserSQLEncryption::getId, "7")).firstNotNull("未找到对应的数据");
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

            easyQuery.updatable(SysUserSQLEncryption.class)
                    .asTable("x123")
                    .set(SysUserSQLEncryption::getPhone, "123123")
                    .where(o -> o.eq(SysUserSQLEncryption::getId, "7")).executeRows();
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
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            TopicLarge topicLarge = new TopicLarge();
            topicLarge.setId("1");
            topicLarge.setStars(1);
            topicLarge.setTitle("1");
            topicLarge.setTitle1("2");
            topicLarge.setCreateTime(LocalDateTime.now());
            easyQuery.addTracking(topicLarge);
            topicLarge.setTitle("xx");
//            String newTitle = "test123" + new Random().nextInt(100000);
//            topic.setTitle(newTitle);
            String sql = ((EasyEntityUpdatable<TopicLarge>) easyQuery.updatable(topicLarge))
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
        String sql = ((EasyEntityUpdatable<TopicLarge>) easyQuery.updatable(topicLarge))
                .toSQL(topicLarge);
        Assert.assertEquals("UPDATE `t_topic` SET `stars` = ?,`create_time` = ? WHERE `id` = ?", sql);
    }
    @Test
    public void updateTest30() {
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {
            BlogEntity blogEntity = new BlogEntity();
            blogEntity.setId("123123123x");
            blogEntity.setContent("123");
            blogEntity.setScore(new BigDecimal("1.0"));

            trackManager.begin();

            boolean b = easyQuery.addTracking(blogEntity);
            Assert.assertTrue(b);
            String newTitle = "test123" + new Random().nextInt(100);
            blogEntity.setContent("111");
            blogEntity.setScore(new BigDecimal("1"));//1.0和1一样
            ((EasyEntityUpdatable<BlogEntity>) easyQuery.updatable(blogEntity))
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
     public void mapUpdateTest1(){
        try {

            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("id","123");
            stringObjectHashMap.put("name","123");
            easyQuery.mapUpdatable(stringObjectHashMap).asTable("aaa")
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
     public void mapUpdateTest2(){
        try {

            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("id","123");
            stringObjectHashMap.put("name","123");
            stringObjectHashMap.put("name1",null);
            easyQuery.mapUpdatable(stringObjectHashMap).asTable("aaa")
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
     public void mapUpdateTest3(){
        try {

            HashMap<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("id","123");
            stringObjectHashMap.put("name","123");
            stringObjectHashMap.put("name1",null);
            easyQuery.mapUpdatable(stringObjectHashMap).asTable("aaa")
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
    public void mapUpdateTest4(){

        long rows = easyQuery.updatable(Topic.class)
                .set(Topic::getStars, 12)
                .where(o -> o.eq(Topic::getId, "2"))
                .executeRows();
        Assert.assertEquals(1, rows);
        List<Map<String, Object>> updates = new ArrayList<>();
        HashMap<String, Object> updateMap = new HashMap<>();
        updateMap.put("id","2");
        updateMap.put("stars",12);
        updates.add(updateMap);
        HashMap<String, Object> update1Map = new HashMap<>();
        update1Map.put("id","2");
        update1Map.put("stars",12);
        updates.add(update1Map);
        easyQuery.mapUpdatable(updates)
                .asTable("t_topic")
                .whereColumns("id")
                .batch()
                .executeRows();
    }
    @Test
    public void mapUpdateTest5(){


        Supplier<Exception> f = () -> {
            try {
                List<Map<String, Object>> updates = new ArrayList<>();
                HashMap<String, Object> updateMap = new HashMap<>();
                updateMap.put("id","2");
                updateMap.put("stars",null);
                updates.add(updateMap);
                easyQuery.mapUpdatable(updates)
                        .asTable("t_topic")
                        .whereColumns("id")
                        .batch()
                        .executeRows();
            }catch (Exception ex){
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
        Assert.assertEquals("UPDATE `t_topic` SET `stars` = ? WHERE `id` = ?",easyQuerySQLStatementException.getSQL());


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            List<Map<String, Object>> updates = new ArrayList<>();
            HashMap<String, Object> updateMap = new HashMap<>();
            updateMap.put("id","2");
            updateMap.put("stars",12);
            updates.add(updateMap);
            easyQuery.mapUpdatable(updates)
                    .asTable("t_topic")
                    .whereColumns("id")
                    .batch()
                    .executeRows();

            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("UPDATE `t_topic` SET `stars` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals(1,jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().size());
            Assert.assertEquals("12(Integer),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
    }
}
