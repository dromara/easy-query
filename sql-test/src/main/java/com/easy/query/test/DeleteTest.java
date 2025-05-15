package com.easy.query.test;

import com.easy.query.api.proxy.entity.delete.ExpressionDeletable;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Supplier;

/**
 * @author xuejiaming
 * @FileName: DeleteTest.java
 * @Description: 文件说明
 * create time 2023/3/21 12:54
 */
public class DeleteTest extends BaseTest {
    @Test
    public void deleteTest1() {
        Topic topic = easyEntityQuery.queryable(Topic.class).whereById("999").firstOrNull();
        if (topic == null) {
            topic = new Topic();
            topic.setId("999");
            topic.setTitle("title999");
            topic.setCreateTime(LocalDateTime.now());
            topic.setStars(999);
            long l = easyEntityQuery.insertable(topic).executeRows();
            Assert.assertEquals(1, l);
        }
        String deleteSQL = easyEntityQuery.deletable(Topic.class).whereById("999").toSQL();
        Assert.assertEquals("DELETE FROM `t_topic` WHERE `id` = ?", deleteSQL);
        long l = easyEntityQuery.deletable(Topic.class).whereById("999").executeRows();
        Assert.assertEquals(1, l);
    }

    @Test
    public void deleteTest2() {
        Topic topic = easyEntityQuery.queryable(Topic.class).whereById("998").firstOrNull();
        if (topic == null) {
            topic = new Topic();
            topic.setId("998");
            topic.setTitle("title998");
            topic.setCreateTime(LocalDateTime.now());
            topic.setStars(998);
            long l = easyEntityQuery.insertable(topic).executeRows();
            Assert.assertEquals(1, l);
        }
        String deleteSql = easyEntityQuery.deletable(Topic.class).where(o -> o.title().eq( "title998")).toSQL();
        Assert.assertEquals("DELETE FROM `t_topic` WHERE `title` = ?", deleteSql);
        long l = easyEntityQuery.deletable(Topic.class).where(o -> o.title().eq( "title998")).executeRows();
        Assert.assertEquals(1, l);

    }

    @Test
    public void deleteTest3() {
        Topic topic = easyEntityQuery.queryable(Topic.class).whereById("997").firstOrNull();
        if (topic == null) {
            topic = new Topic();
            topic.setId("997");
            topic.setTitle("title997");
            topic.setCreateTime(LocalDateTime.now());
            topic.setStars(997);
            long l = easyEntityQuery.insertable(topic).executeRows();
            Assert.assertEquals(1, l);
        }
        String deleteSql = easyEntityQuery.deletable(topic).toSQL();
        Assert.assertEquals("DELETE FROM `t_topic` WHERE `id` = ?", deleteSql);
        long l = easyEntityQuery.deletable(topic).executeRows();
        Assert.assertEquals(1, l);
    }
    @Test
    public void deleteTest3_1() {
        Topic topic = easyEntityQuery.queryable(Topic.class).whereById("997x").firstOrNull();
        if (topic == null) {
            topic = new Topic();
            topic.setId("997x");
            topic.setTitle("title997");
            topic.setCreateTime(LocalDateTime.now());
            topic.setStars(997);
            long l = easyEntityQuery.insertable(topic).executeRows();
            Assert.assertEquals(1, l);
        }
        String deleteSql = easyEntityQuery.deletable(Collections.singletonList(topic)).toSQL();
        Assert.assertEquals("DELETE FROM `t_topic` WHERE `id` = ?", deleteSql);
        long l = easyEntityQuery.deletable(Collections.singletonList(topic)).executeRows();
        Assert.assertEquals(1, l);
    }

    @Test
    public void deleteTest4() {
        Topic topic = easyEntityQuery.queryable(Topic.class).whereById("996").firstOrNull();
        if (topic == null) {
            topic = new Topic();
            topic.setId("996");
            topic.setTitle("title996");
            topic.setCreateTime(LocalDateTime.now());
            topic.setStars(996);
            long l = easyEntityQuery.insertable(topic).executeRows();
            Assert.assertEquals(1, l);
        }
        String deleteSql = easyEntityQuery.deletable(topic).toSQL();
        Assert.assertEquals("DELETE FROM `t_topic` WHERE `id` = ?", deleteSql);
        long l = easyEntityQuery.deletable(topic).executeRows();
        Assert.assertEquals(1, l);
    }

    @Test
    public void deleteTest6() {
        Topic topic = easyEntityQuery.queryable(Topic.class).whereById("995").firstOrNull();
        if (topic == null) {
            topic = new Topic();
            topic.setId("995");
            topic.setTitle("title995");
            topic.setCreateTime(LocalDateTime.now());
            topic.setStars(995);
            long l = easyEntityQuery.insertable(topic).executeRows();
            Assert.assertEquals(1, l);
        }
        try {
            long l = easyEntityQuery.deletable(Topic.class).whereById("999").allowDeleteStatement(false).executeRows();
        } catch (Exception e) {
            Assert.assertEquals(EasyQueryInvalidOperationException.class, e.getClass());
        }

    }



    @Test
    public void deleteTest13() {
//        Topic topic = easyEntityQuery.queryable(Topic.class).whereById("996").firstOrNull();
//        if(topic==null){
//            topic=new Topic();
//            topic.setId("996");
//            topic.setTitle("title996");
//            topic.setCreateTime(LocalDateTime.now());
//            topic.setStars(996);
//            long l = easyEntityQuery.insertable(topic).executeRows();
//            Assert.assertEquals(1,l);
//        }ExpressionDeletable<TopicProxy, Topic> topicExpressionDeletable1 = 
        ExpressionDeletable<TopicProxy, Topic> topicExpressionDeletable1 = easyEntityQuery.deletable(Topic.class).enableLogicDelete();
        boolean b1 = topicExpressionDeletable1.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        Assert.assertTrue(b1);
        ExpressionDeletable<TopicProxy, Topic> topicExpressionDeletable = easyEntityQuery.deletable(Topic.class).disableLogicDelete();
        boolean b = topicExpressionDeletable.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.LOGIC_DELETE);
        Assert.assertFalse(b);
        ExpressionDeletable<TopicProxy, Topic> topicExpressionDeletable2 = easyEntityQuery.deletable(Topic.class).allowDeleteStatement(true);
        boolean b2 = topicExpressionDeletable2.getExpressionContext().isDeleteThrow();
        Assert.assertFalse(b2);
        ExpressionDeletable<TopicProxy, Topic> topicExpressionDeletable3 = easyEntityQuery.deletable(Topic.class).allowDeleteStatement(false);
        boolean b3 = topicExpressionDeletable3.getExpressionContext().isDeleteThrow();
        Assert.assertTrue(b3);
    }

    @Test
    public void deleteTest14() {
        Topic topic = null;
        String sql = easyEntityQuery.getEasyQueryClient().deletable(topic).toSQL();
        Assert.assertNull(sql);
        String sql1 = easyEntityQuery.getEasyQueryClient().deletable(topic).useLogicDelete(false)
                .noInterceptor().noInterceptor("1")
                .useInterceptor().useInterceptor("1")
                .allowDeleteStatement(true).toSQL();
        Assert.assertNull(sql1);
        ExpressionContext expressionContext = easyEntityQuery.getEasyQueryClient().deletable(topic).getExpressionContext();
        Assert.assertNull(expressionContext);
        long l = easyEntityQuery.getEasyQueryClient().deletable(topic).executeRows();
        Assert.assertEquals(0, l);
        easyEntityQuery.getEasyQueryClient().deletable(topic).executeRows(0, "123");
        String sql2 = easyQueryClient.deletable(topic).toSQL();
        Assert.assertNull(sql2);
        String sql3 = easyQueryClient.deletable(topic).toSQL(null);
        Assert.assertNull(sql3);
    }

    @Test
    public void deleteTest5(){

        String sql = easyEntityQuery.deletable(BlogEntity.class)
                .where(o -> o.id().eq( "id123456"))
                .toSQL();
        Assert.assertEquals("UPDATE `t_blog` SET `deleted` = ? WHERE `deleted` = ? AND `id` = ?",sql);
        String sql1 = easyEntityQuery.deletable(BlogEntity.class)
                .where(o -> o.id().eq( "id123456"))
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .toSQL();
        Assert.assertEquals("DELETE FROM `t_blog` WHERE `id` = ?",sql1);

        long l1 = easyEntityQuery.deletable(BlogEntity.class)
                .where(o -> o.id().eq( "id123456")).executeRows();
        Assert.assertEquals(0,l1);

        long l = easyEntityQuery.deletable(BlogEntity.class)
                .where(o->o.id().eq("id123456"))
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        Assert.assertEquals(0,l);
    }
    @Test
    public void deleteTest19(){
        String sql = easyEntityQuery.deletable(BlogEntity.class)
                .where(o -> o.id().eq("id123456"))
                .toSQL();
        Assert.assertEquals("UPDATE `t_blog` SET `deleted` = ? WHERE `deleted` = ? AND `id` = ?",sql);


        String sql2 = easyEntityQuery.deletable(BlogEntity.class)
                .where(o -> {
                    o.id().eq("id123456");
                    o.createTime().le(LocalDateTime.of(2023,1,1,1,1));
                })
                .toSQL();
        Assert.assertEquals("UPDATE `t_blog` SET `deleted` = ? WHERE `deleted` = ? AND `id` = ? AND `create_time` <= ?",sql2);
        String sql1 = easyEntityQuery.deletable(BlogEntity.class)
                .where(o -> o.id().eq("id123456"))
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .toSQL();
        Assert.assertEquals("DELETE FROM `t_blog` WHERE `id` = ?",sql1);

        long l1 = easyEntityQuery.deletable(BlogEntity.class)
                .where(o -> o.id().eq("id123456")).executeRows();
        Assert.assertEquals(0,l1);

        long l = easyEntityQuery.deletable(BlogEntity.class)
                .where(o->o.id().eq("id123456"))
                .disableLogicDelete()
                .allowDeleteStatement(true)
                .executeRows();
        Assert.assertEquals(0,l);
    }
    @Test
    public void deleteTest20(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Supplier<Exception> f = () -> {
            try {
                long l = easyEntityQuery.deletable(BlogEntity.class)
                        .asTable(o->o+"123abc")
                        .where(o -> {
                            o.id().eq("id123456");
                            o.createTime().le(LocalDateTime.of(2023, 1, 1, 1, 1));
                        })
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
        Assert.assertEquals("UPDATE `t_blog123abc` SET `deleted` = ? WHERE `deleted` = ? AND `id` = ? AND `create_time` <= ?", easyQuerySQLStatementException.getSQL());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `t_blog123abc` SET `deleted` = ? WHERE `deleted` = ? AND `id` = ? AND `create_time` <= ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("true(Boolean),false(Boolean),id123456(String),2023-01-01T01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void deleteTest21(){

        Topic topic = new Topic();
        topic.setId(String.valueOf(1));
        topic.setStars(1 + 100);
        topic.setTitle("标题" + 1);
        topic.setCreateTime(LocalDateTime.of(2021, 1, 1, 1, 1, 1));

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Supplier<Exception> f = () -> {
            try {
                long l = easyEntityQuery.deletable(topic)
                        .asTable(o->o+"123abc")
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
        Assert.assertEquals("DELETE FROM `t_topic123abc` WHERE `id` = ?", easyQuerySQLStatementException.getSQL());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("DELETE FROM `t_topic123abc` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
    @Test
    public void deleteTest22(){

        BlogEntity blog = new BlogEntity();
        blog.setId(String.valueOf(1));
        blog.setTitle("标题" + 1);
        blog.setCreateTime(LocalDateTime.of(2021, 1, 1, 1, 1, 1));

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Supplier<Exception> f = () -> {
            try {
                long l = easyEntityQuery.deletable(blog)
                        .asTable(o->o+"123abc")
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
        Assert.assertEquals("UPDATE `t_blog123abc` SET `deleted` = ? WHERE `deleted` = ? AND `id` = ?", easyQuerySQLStatementException.getSQL());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("UPDATE `t_blog123abc` SET `deleted` = ? WHERE `deleted` = ? AND `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("true(Boolean),false(Boolean),1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void testBatchDelete(){
        ArrayList<Topic> topics = new ArrayList<>();
        {
            Topic topic = new Topic();
            topic.setId("123pppxxx");
            topic.setTitle("123123");
            topic.setStars(2);
            topics.add(topic);
        }
        {
            Topic topic = new Topic();
            topic.setId("123pppxxx1");
            topic.setTitle("1231231");
            topic.setStars(1);
            topics.add(topic);
        }


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.deletable(topics)
                .batch().executeRows();
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("DELETE FROM `t_topic` WHERE `id` = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals(2,jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().size());
        Assert.assertEquals("123pppxxx(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        Assert.assertEquals("123pppxxx1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(1)));
        listenerContextManager.clear();
    }
}
