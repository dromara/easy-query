package com.easy.query.test;

import com.easy.query.api.proxy.entity.select.EntityQueryable10;
import com.easy.query.api.proxy.entity.select.EntityQueryable3;
import com.easy.query.api.proxy.entity.select.EntityQueryable4;
import com.easy.query.api.proxy.entity.select.EntityQueryable5;
import com.easy.query.api.proxy.entity.select.EntityQueryable6;
import com.easy.query.api.proxy.entity.select.EntityQueryable7;
import com.easy.query.api.proxy.entity.select.EntityQueryable8;
import com.easy.query.api.proxy.entity.select.EntityQueryable9;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.proxy.TopicProxy;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.function.Supplier;

/**
 * create time 2023/10/31 08:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryAggregateTest2 extends BaseTest{
    public EntityQueryable10<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join1(){
        EntityQueryable10<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t9.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t10.id()));
        return query;
    }
    public EntityQueryable9<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join2(){
        EntityQueryable9<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t9.id()));
        return query;
    }
    public EntityQueryable8<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join3(){
        EntityQueryable8<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t8.id()));
        return query;
    }
    public EntityQueryable7<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join4(){
        EntityQueryable7<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t7.id()));
        return query;
    }
    public EntityQueryable6<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join5(){
        EntityQueryable6<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t6.id()));
        return query;
    }
    public EntityQueryable5<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join6(){
        EntityQueryable5<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t5.id()));
        return query;
    }
    public EntityQueryable4<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join7(){
        EntityQueryable4<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t4.id()));
        return query;
    }
    public EntityQueryable3<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> join8(){
        EntityQueryable3<TopicProxy, Topic, TopicProxy, Topic, TopicProxy, Topic> query = easyEntityQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.id().eq(t1.id()))
                .rightJoinMerge(Topic.class, o -> o.t1.id().eq(o.t3.id()));
        return query;
    }

    @Test
    public void test1(){
        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").sumOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `abcv` t9 ON t.`id` = t9.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").sumOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `abcv` t9 ON t.`id` = t9.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").sumBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    }, BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `abcv` t9 ON t.`id` = t9.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").sumBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `abcv` t9 ON t.`id` = t9.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").maxOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `abcv` t9 ON t.`id` = t9.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").maxOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `abcv` t9 ON t.`id` = t9.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").minOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `abcv` t9 ON t.`id` = t9.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").minOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `abcv` t9 ON t.`id` = t9.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").avgOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `abcv` t9 ON t.`id` = t9.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").avgBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `abcv` t9 ON t.`id` = t9.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").avgFloatOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `abcv` t9 ON t.`id` = t9.`id`",sql);
        }

        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").avgOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1d);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `abcv` t9 ON t.`id` = t9.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").avgBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    },BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `abcv` t9 ON t.`id` = t9.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").avgFloatOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1f);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `t_topic` t8 ON t.`id` = t8.`id` RIGHT JOIN `abcv` t9 ON t.`id` = t9.`id`",sql);
        }

    }
    @Test
    public void test2(){
        {
            Supplier<Exception> f = () -> {
                try {
                    join2().asTable("abcv").sumOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join2().asTable("abcv").sumOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join2().asTable("abcv").sumBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    }, BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join2().asTable("abcv").sumBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join2().asTable("abcv").maxOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join2().asTable("abcv").maxOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join2().asTable("abcv").minOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join2().asTable("abcv").minOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join2().asTable("abcv").avgOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join2().asTable("abcv").avgBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join2().asTable("abcv").avgFloatOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`",sql);
        }

        {
            Supplier<Exception> f = () -> {
                try {
                    join2().asTable("abcv").avgOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1d);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join2().asTable("abcv").avgBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    },BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join2().asTable("abcv").avgFloatOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1f);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `t_topic` t7 ON t.`id` = t7.`id` RIGHT JOIN `abcv` t8 ON t.`id` = t8.`id`",sql);
        }

    }

    @Test
    public void test3(){
        {
            Supplier<Exception> f = () -> {
                try {
                    join3().asTable("abcv").sumOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join3().asTable("abcv").sumOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join3().asTable("abcv").sumBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    }, BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join3().asTable("abcv").sumBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join3().asTable("abcv").maxOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join3().asTable("abcv").maxOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join3().asTable("abcv").minOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join3().asTable("abcv").minOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join3().asTable("abcv").avgOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join3().asTable("abcv").avgBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join3().asTable("abcv").avgFloatOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`",sql);
        }

        {
            Supplier<Exception> f = () -> {
                try {
                    join3().asTable("abcv").avgOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1d);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join3().asTable("abcv").avgBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    },BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join3().asTable("abcv").avgFloatOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1f);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `t_topic` t6 ON t.`id` = t6.`id` RIGHT JOIN `abcv` t7 ON t.`id` = t7.`id`",sql);
        }

    }

    @Test
    public void test4(){
        {
            Supplier<Exception> f = () -> {
                try {
                    join4().asTable("abcv").sumOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join4().asTable("abcv").sumOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join4().asTable("abcv").sumBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    }, BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join4().asTable("abcv").sumBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join4().asTable("abcv").maxOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join4().asTable("abcv").maxOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join4().asTable("abcv").minOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join4().asTable("abcv").minOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join4().asTable("abcv").avgOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join4().asTable("abcv").avgBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join4().asTable("abcv").avgFloatOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`",sql);
        }

        {
            Supplier<Exception> f = () -> {
                try {
                    join4().asTable("abcv").avgOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1d);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join4().asTable("abcv").avgBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    },BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join4().asTable("abcv").avgFloatOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1f);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `t_topic` t5 ON t.`id` = t5.`id` RIGHT JOIN `abcv` t6 ON t.`id` = t6.`id`",sql);
        }

    }
    @Test
    public void test5(){
        {
            Supplier<Exception> f = () -> {
                try {
                    join5().asTable("abcv").sumOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join5().asTable("abcv").sumOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join5().asTable("abcv").sumBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    }, BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join5().asTable("abcv").sumBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join5().asTable("abcv").maxOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join5().asTable("abcv").maxOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join5().asTable("abcv").minOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join5().asTable("abcv").minOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join5().asTable("abcv").avgOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join5().asTable("abcv").avgBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join5().asTable("abcv").avgFloatOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`",sql);
        }

        {
            Supplier<Exception> f = () -> {
                try {
                    join5().asTable("abcv").avgOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1d);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join5().asTable("abcv").avgBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    },BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join5().asTable("abcv").avgFloatOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1f);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `t_topic` t4 ON t.`id` = t4.`id` RIGHT JOIN `abcv` t5 ON t.`id` = t5.`id`",sql);
        }

    }
    @Test
    public void test6(){
        {
            Supplier<Exception> f = () -> {
                try {
                    join6().asTable("abcv").sumOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join6().asTable("abcv").sumOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join6().asTable("abcv").sumBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    }, BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join6().asTable("abcv").sumBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join6().asTable("abcv").maxOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join6().asTable("abcv").maxOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join6().asTable("abcv").minOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join6().asTable("abcv").minOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join6().asTable("abcv").avgOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join6().asTable("abcv").avgBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join6().asTable("abcv").avgFloatOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`",sql);
        }

        {
            Supplier<Exception> f = () -> {
                try {
                    join6().asTable("abcv").avgOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1d);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join6().asTable("abcv").avgBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    },BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join6().asTable("abcv").avgFloatOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1f);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `t_topic` t3 ON t.`id` = t3.`id` RIGHT JOIN `abcv` t4 ON t.`id` = t4.`id`",sql);
        }

    }
    @Test
    public void test7(){
        {
            Supplier<Exception> f = () -> {
                try {
                    join7().asTable("abcv").sumOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join7().asTable("abcv").sumOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join7().asTable("abcv").sumBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    }, BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join7().asTable("abcv").sumBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join7().asTable("abcv").maxOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join7().asTable("abcv").maxOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join7().asTable("abcv").minOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join7().asTable("abcv").minOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join7().asTable("abcv").avgOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join7().asTable("abcv").avgBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join7().asTable("abcv").avgFloatOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`",sql);
        }

        {
            Supplier<Exception> f = () -> {
                try {
                    join7().asTable("abcv").avgOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1d);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join7().asTable("abcv").avgBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    },BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join7().asTable("abcv").avgFloatOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1f);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `t_topic` t2 ON t.`id` = t2.`id` RIGHT JOIN `abcv` t3 ON t.`id` = t3.`id`",sql);
        }

    }
    @Test
    public void test8(){
        {
            Supplier<Exception> f = () -> {
                try {
                    join8().asTable("abcv").sumOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join8().asTable("abcv").sumOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join8().asTable("abcv").sumBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    }, BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join8().asTable("abcv").sumBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT SUM(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join8().asTable("abcv").maxOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join8().asTable("abcv").maxOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MAX(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join8().asTable("abcv").minOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join8().asTable("abcv").minOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT MIN(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join8().asTable("abcv").avgOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join8().asTable("abcv").avgBigDecimalOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join8().asTable("abcv").avgFloatOrNullMerge(o->{
                        return o.t1.stars();
                    });
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`",sql);
        }

        {
            Supplier<Exception> f = () -> {
                try {
                    join8().asTable("abcv").avgOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1d);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join8().asTable("abcv").avgBigDecimalOrDefaultMerge(o->{
                        return o.t1.stars();
                    },BigDecimal.ZERO);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`",sql);
        }
        {
            Supplier<Exception> f = () -> {
                try {
                    join8().asTable("abcv").avgFloatOrDefaultMerge(o->{
                        return o.t1.stars();
                    },1f);
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
            String sql = easyQuerySQLStatementException.getSQL();
            Assert.assertEquals("SELECT AVG(t.`stars`) FROM `t_topic` t RIGHT JOIN `t_topic` t1 ON t.`id` = t1.`id` RIGHT JOIN `abcv` t2 ON t.`id` = t2.`id`",sql);
        }

    }
}
