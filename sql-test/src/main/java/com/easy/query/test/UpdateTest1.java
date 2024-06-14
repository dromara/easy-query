package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.function.Supplier;

/**
 * create time 2024/1/1 17:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpdateTest1 extends BaseTest {

//    @Test
//    public void testUpdate0() {
//        easyEntityQuery.updatable(SysUser.class)
//                .setColumns(s -> s.id().set("123x xx"))
//                .where(s -> {
//                    s.myBlog().id().eq("123e");
//                }).executeRows();
//    }
    @Test
    public void testUpdate1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Supplier<Exception> f = () -> {
            try {
                easyEntityQuery.updatable(Topic.class)
                        .setColumns(o -> {
                            o.title().set("title_abc");
                        })
                        .asSchema(x -> x + "_abc")
                        .asTable(o -> o + "_abc")
                        .whereById("1")
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
        Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `title` = ? WHERE `id` = ?", easyQuerySQLStatementException.getSQL());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `title` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("title_abc(String),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testUpdate1_1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Supplier<Exception> f = () -> {
            try {
                easyEntityQuery.updatable(Topic.class)
                        .setColumns(o -> {
                            o.title().setNull();
                        })
                        .asSchema(x -> x + "_abc")
                        .asTable(o -> o + "_abc")
                        .whereById("1")
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
        Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `title` = ? WHERE `id` = ?", easyQuerySQLStatementException.getSQL());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `title` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("null(null),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testUpdate1_2() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Supplier<Exception> f = () -> {
            try {
                easyEntityQuery.updatable(Topic.class)
                        .setColumns(o -> {
                            //o.title().set(o.stars());编译报错因为starts是Integer类型二title是String类型
                            o.title().set(o.stars().toStr());
                        })
                        .asSchema(x -> x + "_abc")
                        .asTable(o -> o + "_abc")
                        .whereById("1")
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
        Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `title` = CAST(`stars` AS CHAR) WHERE `id` = ?", easyQuerySQLStatementException.getSQL());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `title` = CAST(`stars` AS CHAR) WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testUpdate1_3() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Supplier<Exception> f = () -> {
            try {
                easyEntityQuery.updatable(Topic.class)
                        .setColumns(o -> {
                            o.title().set(o.stars().asAnyType(String.class));
                        })
                        .asSchema(x -> x + "_abc")
                        .asTable(o -> o + "_abc")
                        .whereById("1")
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
        Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `title` = `stars` WHERE `id` = ?", easyQuerySQLStatementException.getSQL());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `title` = `stars` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();

    }

    @Test
    public void testUpdate2() {


        Topic topic = new Topic();
        topic.setId(String.valueOf(1));
        topic.setStars(1 + 100);
        topic.setTitle("标题" + 1);
        topic.setCreateTime(LocalDateTime.of(2021, 1, 1, 1, 1, 1));

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Supplier<Exception> f = () -> {
            try {
                easyEntityQuery.updatable(topic)
                        .asSchema(x -> x + "_abc")
                        .asTable(o -> o + "_abc")
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
        Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `stars` = ?,`title` = ?,`create_time` = ? WHERE `id` = ?", easyQuerySQLStatementException.getSQL());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `stars` = ?,`title` = ?,`create_time` = ? WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("101(Integer),标题1(String),2021-01-01T01:01:01(LocalDateTime),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testUpdate3() {


        Topic topic = new Topic();
        topic.setId(String.valueOf(1));
        topic.setStars(1 + 100);
        topic.setTitle("标题" + 1);
        topic.setCreateTime(LocalDateTime.of(2021, 1, 1, 1, 1, 1));

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Supplier<Exception> f = () -> {
            try {
                easyEntityQuery.updatable(topic)
                        .asSchema(x -> x + "_abc")
                        .asTable(o -> o + "_abc")
                        .setColumns(o -> o.FETCHER.title().stars())
                        .whereColumns(o -> o.FETCHER.columnKeys().stars())
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
        Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `title` = ?,`stars` = ? WHERE `id` = ? AND `stars` = ?", easyQuerySQLStatementException.getSQL());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `title` = ?,`stars` = ? WHERE `id` = ? AND `stars` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("标题1(String),101(Integer),1(String),101(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
//
//    @Test
//    public void testUpdate3_1() {
//
//        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
//        try {
//            trackManager.begin();
//
//
//            TopicConcurrent topic = new TopicConcurrent();
//            topic.setId(String.valueOf(1));
//            topic.setStars(1 + 100);
//            topic.setTitle("标题" + 1);
//            topic.setCreateTime(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
//            easyEntityQuery.addTracking(topic);
//            topic.setStars(2+100);
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//            Supplier<Exception> f = () -> {
//                try {
//                    easyEntityQuery.updatable(topic)
//                            .asSchema(x -> x + "_abc")
//                            .asTable(o -> o + "_abc")
//                            .setColumns(o -> o.FETCHER.title().stars())
////                        .whereColumns(o->o.FETCHER.columnKeys().stars())
//                            .executeRows();
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
//            Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `title` = ?,`stars` = ? WHERE `id` = ? AND `stars` = ?", easyQuerySQLStatementException.getSQL());
//
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `title` = ?,`stars` = ? WHERE `id` = ? AND `stars` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("标题1(String),102(Integer),1(String),101(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//
//        } finally {
//            trackManager.release();
//        }
//    }
//
//    @Test
//    public void testUpdate3_2() {
//
//        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
//        try {
//            trackManager.begin();
//
//
//            TopicConcurrent topic = new TopicConcurrent();
//            topic.setId(String.valueOf(1));
//            topic.setStars(1 + 100);
//            topic.setTitle("标题" + 1);
//            topic.setCreateTime(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
//            easyEntityQuery.addTracking(topic);
//            topic.setStars(2+100);
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//            Supplier<Exception> f = () -> {
//                try {
//                    easyEntityQuery.updatable(topic)
//                            .asSchema(x -> x + "_abc")
//                            .asTable(o -> o + "_abc")
////                        .whereColumns(o->o.FETCHER.columnKeys().stars())
//                            .executeRows();
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
//            Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `stars` = ? WHERE `id` = ? AND `stars` = ?", easyQuerySQLStatementException.getSQL());
//
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `stars` = ? WHERE `id` = ? AND `stars` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("102(Integer),1(String),101(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//
//        } finally {
//            trackManager.release();
//        }
//    }
//
//    @Test
//    public void testUpdate3_3() {
//
//        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
//        try {
//            trackManager.begin();
//
//
//            TopicConcurrent topic = new TopicConcurrent();
//            topic.setId(String.valueOf(1));
//            topic.setStars(1 + 100);
//            topic.setTitle("标题" + 1);
//            topic.setCreateTime(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
//            easyEntityQuery.addTracking(topic);
//            topic.setStars(2+100);
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//            Supplier<Exception> f = () -> {
//                try {
//                    easyEntityQuery.updatable(topic)
//                            .asSchema(x -> x + "_abc")
//                            .asTable(o -> o + "_abc")
//                        .whereColumns(o->o.FETCHER.columnKeys().stars())
//                            .executeRows();
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
//            Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `stars` = ? WHERE `id` = ? AND `stars` = ?", easyQuerySQLStatementException.getSQL());
//
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `stars` = ? WHERE `id` = ? AND `stars` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("102(Integer),1(String),101(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//
//        } finally {
//            trackManager.release();
//        }
//    }
//    @Test
//    public void testUpdate3_4() {
//
//        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
//        try {
//            trackManager.begin();
//            List<TopicConcurrent> topics=new ArrayList<TopicConcurrent>();
//
//            {
//
//                TopicConcurrent topic = new TopicConcurrent();
//                topic.setId(String.valueOf(1));
//                topic.setStars(1 + 100);
//                topic.setTitle("标题" + 1);
//                topic.setCreateTime(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
//                easyEntityQuery.addTracking(topic);
//                topic.setStars(2+100);
//                topics.add(topic);
//            }
//            {
////                SQLFunc fx = easyEntityQuery.getRuntimeContext().fx();
////                fx.concat(x->x.sqlFunc().value("年"))
//                TopicConcurrent topic = new TopicConcurrent();
//                topic.setId(String.valueOf(2));
//                topic.setStars(3 + 100);
//                topic.setTitle("标题" + 1);
//                topic.setCreateTime(LocalDateTime.of(2021, 1, 1, 1, 1, 1));
//                easyEntityQuery.addTracking(topic);
//                topic.setStars(4+100);
//                topics.add(topic);
//            }
//            ListenerContext listenerContext = new ListenerContext();
//            listenerContextManager.startListen(listenerContext);
//            Supplier<Exception> f = () -> {
//                try {
//                    easyEntityQuery.updatable(topics)
//                            .asSchema(x -> x + "_abc")
//                            .asTable(o -> o + "_abc")
//                            .batch()
//                            .executeRows();
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
//            Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `stars` = ? WHERE `id` = ? AND `stars` = ?", easyQuerySQLStatementException.getSQL());
//
//            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
//            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
//            Assert.assertEquals("UPDATE `_abc`.`t_topic_abc` SET `stars` = ? WHERE `id` = ? AND `stars` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
//            Assert.assertEquals("102(Integer),1(String),101(Integer)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
//            listenerContextManager.clear();
//
//        } finally {
//            trackManager.release();
//        }
//    }

    @Test
    public void testUpdate4() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Supplier<Exception> f = () -> {
            try {
                easyEntityQuery.updatable(BlogEntity.class)
                        .asSchema(x -> x + "_abc")
                        .asTable(o -> o + "_abc")
                        .setColumns(o -> {
                            o.isTop().set(o.isTop().not());
                        })
                        .whereById("2xxxa")
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
        Assert.assertEquals("UPDATE `_abc`.`t_blog_abc` SET `is_top` = (NOT `is_top`) WHERE `deleted` = ? AND `id` = ?", easyQuerySQLStatementException.getSQL());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `_abc`.`t_blog_abc` SET `is_top` = (NOT `is_top`) WHERE `deleted` = ? AND `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("false(Boolean),2xxxa(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testUpdate5() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Supplier<Exception> f = () -> {
            try {
                easyEntityQuery.updatable(BlogEntity.class)
                        .asSchema(x -> x + "_abc")
                        .asTable(o -> o + "_abc")
                        .setColumns(o -> {
                            o.title().set(
                                    o.expression().caseWhen(() -> o.title().eq("123"))
                                            .then("1")
                                            .elseEnd("2")
                            );
                        })
                        .whereById("2xxxa")
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
        Assert.assertEquals("UPDATE `_abc`.`t_blog_abc` SET `title` = (CASE WHEN `title` = ? THEN ? ELSE ? END) WHERE `deleted` = ? AND `id` = ?", easyQuerySQLStatementException.getSQL());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `_abc`.`t_blog_abc` SET `title` = (CASE WHEN `title` = ? THEN ? ELSE ? END) WHERE `deleted` = ? AND `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),1(String),2(String),false(Boolean),2xxxa(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void updateNoSet(){
        {

            long l = easyEntityQuery.updatable(Topic.class)
                    .where(t -> t.id().eq("xxxmmnbv"))
                    .executeRows();
            Assert.assertEquals(0,l);
        }
        {

            easyEntityQuery.updatable(Topic.class)
                    .where(t -> t.id().eq("xxxmmnbv"))
                    .executeRows(0,"123");
        }
    }



    @Test
    public void updateTest51() {
        TrackManager trackManager = easyQuery.getRuntimeContext().getTrackManager();
        try {

            trackManager.begin();
            Topic topic = easyQuery.queryable(Topic.class).asTracking()
                    .whereById("1").firstNotNull();

            long l = easyQuery.updatable(topic).executeRows();
            Assert.assertEquals(0, l);
        } finally {

            trackManager.release();
        }
        Assert.assertFalse(trackManager.currentThreadTracking());
    }
}
