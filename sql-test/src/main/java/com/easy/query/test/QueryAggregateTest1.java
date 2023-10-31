package com.easy.query.test;

import com.easy.query.api4j.select.Queryable10;
import com.easy.query.api4j.select.Queryable3;
import com.easy.query.api4j.select.Queryable4;
import com.easy.query.api4j.select.Queryable5;
import com.easy.query.api4j.select.Queryable6;
import com.easy.query.api4j.select.Queryable7;
import com.easy.query.api4j.select.Queryable8;
import com.easy.query.api4j.select.Queryable9;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.test.entity.Topic;
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
public class QueryAggregateTest1 extends BaseTest{

    public Queryable10<Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic> join1(){
        Queryable10<Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t7(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t8(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t9(), Topic::getId, Topic::getId));
        return query;
    }
    public Queryable9<Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic> join2(){
        Queryable9<Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t7(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t8(), Topic::getId, Topic::getId));
        return query;
    }
    public Queryable8<Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic> join3(){
        Queryable8<Topic, Topic, Topic, Topic, Topic, Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t7(), Topic::getId, Topic::getId));
        return query;
    }
    public Queryable7<Topic, Topic, Topic, Topic, Topic, Topic, Topic> join4(){
        Queryable7<Topic, Topic, Topic, Topic, Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t6(), Topic::getId, Topic::getId));
        return query;
    }
    public Queryable6<Topic, Topic, Topic, Topic, Topic, Topic> join5(){
        Queryable6<Topic, Topic, Topic, Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t5(), Topic::getId, Topic::getId));
        return query;
    }
    public  Queryable5<Topic, Topic, Topic, Topic, Topic> join6(){
        Queryable5<Topic, Topic, Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t4(), Topic::getId, Topic::getId));
        return query;
    }
    public Queryable4<Topic, Topic, Topic, Topic> join7(){
        Queryable4<Topic, Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t3(), Topic::getId, Topic::getId));
        return query;
    }
    public Queryable3<Topic, Topic, Topic> join8(){
        Queryable3<Topic, Topic, Topic> query = easyQuery.queryable(Topic.class)
                .rightJoin(Topic.class, (t, t1) -> t.eq(t1, Topic::getId, Topic::getId))
                .rightJoinMerge(Topic.class, o -> o.t().eq(o.t2(), Topic::getId, Topic::getId));
        return query;
    }

    @Test
    public void test1(){
        {
            Supplier<Exception> f = () -> {
                try {
                    join1().asTable("abcv").sumOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
                        t.column(Topic::getStars);
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
                    join1().asTable("abcv").sumOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
                        t.column(Topic::getStars);
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
                    join1().asTable("abcv").sumBigDecimalOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
                        t.column(Topic::getStars);
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
                    join1().asTable("abcv").sumBigDecimalOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
                        t.column(Topic::getStars);
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
                    join1().asTable("abcv").maxOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
                        t.column(Topic::getStars);
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
                    join1().asTable("abcv").maxOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
                        t.column(Topic::getStars);
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
                    join1().asTable("abcv").minOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
                        t.column(Topic::getStars);
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
                    join1().asTable("abcv").minOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
                        t.column(Topic::getStars);
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
                    join1().asTable("abcv").avgOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
                        t.column(Topic::getStars);
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
                    join1().asTable("abcv").avgBigDecimalOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
                        t.column(Topic::getStars);
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
                    join1().asTable("abcv").avgFloatOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
                        t.column(Topic::getStars);
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
                    join1().asTable("abcv").avgOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
                        t.column(Topic::getStars);
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
                    join1().asTable("abcv").avgBigDecimalOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
                        t.column(Topic::getStars);
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
                    join1().asTable("abcv").avgFloatOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8,t9)->{
                        t.column(Topic::getStars);
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
                    join2().asTable("abcv").sumOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
                        t.column(Topic::getStars);
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
                    join2().asTable("abcv").sumOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
                        t.column(Topic::getStars);
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
                    join2().asTable("abcv").sumBigDecimalOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
                        t.column(Topic::getStars);
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
                    join2().asTable("abcv").sumBigDecimalOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
                        t.column(Topic::getStars);
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
                    join2().asTable("abcv").maxOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
                        t.column(Topic::getStars);
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
                    join2().asTable("abcv").maxOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
                        t.column(Topic::getStars);
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
                    join2().asTable("abcv").minOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
                        t.column(Topic::getStars);
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
                    join2().asTable("abcv").minOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
                        t.column(Topic::getStars);
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
                    join2().asTable("abcv").avgOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
                        t.column(Topic::getStars);
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
                    join2().asTable("abcv").avgBigDecimalOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
                        t.column(Topic::getStars);
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
                    join2().asTable("abcv").avgFloatOrNull((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
                        t.column(Topic::getStars);
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
                    join2().asTable("abcv").avgOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
                        t.column(Topic::getStars);
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
                    join2().asTable("abcv").avgBigDecimalOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
                        t.column(Topic::getStars);
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
                    join2().asTable("abcv").avgFloatOrDefault((t,t1,t2,t3,t4,t5,t6,t7,t8)->{
                        t.column(Topic::getStars);
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
                    join3().asTable("abcv").sumOrDefault((t,t1,t2,t3,t4,t5,t6,t7)->{
                        t.column(Topic::getStars);
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
                    join3().asTable("abcv").sumOrNull((t,t1,t2,t3,t4,t5,t6,t7)->{
                        t.column(Topic::getStars);
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
                    join3().asTable("abcv").sumBigDecimalOrDefault((t,t1,t2,t3,t4,t5,t6,t7)->{
                        t.column(Topic::getStars);
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
                    join3().asTable("abcv").sumBigDecimalOrNull((t,t1,t2,t3,t4,t5,t6,t7)->{
                        t.column(Topic::getStars);
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
                    join3().asTable("abcv").maxOrNull((t,t1,t2,t3,t4,t5,t6,t7)->{
                        t.column(Topic::getStars);
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
                    join3().asTable("abcv").maxOrDefault((t,t1,t2,t3,t4,t5,t6,t7)->{
                        t.column(Topic::getStars);
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
                    join3().asTable("abcv").minOrNull((t,t1,t2,t3,t4,t5,t6,t7)->{
                        t.column(Topic::getStars);
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
                    join3().asTable("abcv").minOrDefault((t,t1,t2,t3,t4,t5,t6,t7)->{
                        t.column(Topic::getStars);
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
                    join3().asTable("abcv").avgOrNull((t,t1,t2,t3,t4,t5,t6,t7)->{
                        t.column(Topic::getStars);
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
                    join3().asTable("abcv").avgBigDecimalOrNull((t,t1,t2,t3,t4,t5,t6,t7)->{
                        t.column(Topic::getStars);
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
                    join3().asTable("abcv").avgFloatOrNull((t,t1,t2,t3,t4,t5,t6,t7)->{
                        t.column(Topic::getStars);
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
                    join3().asTable("abcv").avgOrDefault((t,t1,t2,t3,t4,t5,t6,t7)->{
                        t.column(Topic::getStars);
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
                    join3().asTable("abcv").avgBigDecimalOrDefault((t,t1,t2,t3,t4,t5,t6,t7)->{
                        t.column(Topic::getStars);
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
                    join3().asTable("abcv").avgFloatOrDefault((t,t1,t2,t3,t4,t5,t6,t7)->{
                        t.column(Topic::getStars);
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
                    join4().asTable("abcv").sumOrDefault((t,t1,t2,t3,t4,t5,t6)->{
                        t.column(Topic::getStars);
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
                    join4().asTable("abcv").sumOrNull((t,t1,t2,t3,t4,t5,t6)->{
                        t.column(Topic::getStars);
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
                    join4().asTable("abcv").sumBigDecimalOrDefault((t,t1,t2,t3,t4,t5,t6)->{
                        t.column(Topic::getStars);
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
                    join4().asTable("abcv").sumBigDecimalOrNull((t,t1,t2,t3,t4,t5,t6)->{
                        t.column(Topic::getStars);
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
                    join4().asTable("abcv").maxOrNull((t,t1,t2,t3,t4,t5,t6)->{
                        t.column(Topic::getStars);
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
                    join4().asTable("abcv").maxOrDefault((t,t1,t2,t3,t4,t5,t6)->{
                        t.column(Topic::getStars);
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
                    join4().asTable("abcv").minOrNull((t,t1,t2,t3,t4,t5,t6)->{
                        t.column(Topic::getStars);
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
                    join4().asTable("abcv").minOrDefault((t,t1,t2,t3,t4,t5,t6)->{
                        t.column(Topic::getStars);
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
                    join4().asTable("abcv").avgOrNull((t,t1,t2,t3,t4,t5,t6)->{
                        t.column(Topic::getStars);
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
                    join4().asTable("abcv").avgBigDecimalOrNull((t,t1,t2,t3,t4,t5,t6)->{
                        t.column(Topic::getStars);
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
                    join4().asTable("abcv").avgFloatOrNull((t,t1,t2,t3,t4,t5,t6)->{
                        t.column(Topic::getStars);
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
                    join4().asTable("abcv").avgOrDefault((t,t1,t2,t3,t4,t5,t6)->{
                        t.column(Topic::getStars);
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
                    join4().asTable("abcv").avgBigDecimalOrDefault((t,t1,t2,t3,t4,t5,t6)->{
                        t.column(Topic::getStars);
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
                    join4().asTable("abcv").avgFloatOrDefault((t,t1,t2,t3,t4,t5,t6)->{
                        t.column(Topic::getStars);
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
                    join5().asTable("abcv").sumOrDefault((t,t1,t2,t3,t4,t5)->{
                        t.column(Topic::getStars);
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
                    join5().asTable("abcv").sumOrNull((t,t1,t2,t3,t4,t5)->{
                        t.column(Topic::getStars);
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
                    join5().asTable("abcv").sumBigDecimalOrDefault((t,t1,t2,t3,t4,t5)->{
                        t.column(Topic::getStars);
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
                    join5().asTable("abcv").sumBigDecimalOrNull((t,t1,t2,t3,t4,t5)->{
                        t.column(Topic::getStars);
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
                    join5().asTable("abcv").maxOrNull((t,t1,t2,t3,t4,t5)->{
                        t.column(Topic::getStars);
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
                    join5().asTable("abcv").maxOrDefault((t,t1,t2,t3,t4,t5)->{
                        t.column(Topic::getStars);
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
                    join5().asTable("abcv").minOrNull((t,t1,t2,t3,t4,t5)->{
                        t.column(Topic::getStars);
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
                    join5().asTable("abcv").minOrDefault((t,t1,t2,t3,t4,t5)->{
                        t.column(Topic::getStars);
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
                    join5().asTable("abcv").avgOrNull((t,t1,t2,t3,t4,t5)->{
                        t.column(Topic::getStars);
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
                    join5().asTable("abcv").avgBigDecimalOrNull((t,t1,t2,t3,t4,t5)->{
                        t.column(Topic::getStars);
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
                    join5().asTable("abcv").avgFloatOrNull((t,t1,t2,t3,t4,t5)->{
                        t.column(Topic::getStars);
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
                    join5().asTable("abcv").avgOrDefault((t,t1,t2,t3,t4,t5)->{
                        t.column(Topic::getStars);
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
                    join5().asTable("abcv").avgBigDecimalOrDefault((t,t1,t2,t3,t4,t5)->{
                        t.column(Topic::getStars);
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
                    join5().asTable("abcv").avgFloatOrDefault((t,t1,t2,t3,t4,t5)->{
                        t.column(Topic::getStars);
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
                    join6().asTable("abcv").sumOrDefault((t,t1,t2,t3,t4)->{
                        t.column(Topic::getStars);
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
                    join6().asTable("abcv").sumOrNull((t,t1,t2,t3,t4)->{
                        t.column(Topic::getStars);
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
                    join6().asTable("abcv").sumBigDecimalOrDefault((t,t1,t2,t3,t4)->{
                        t.column(Topic::getStars);
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
                    join6().asTable("abcv").sumBigDecimalOrNull((t,t1,t2,t3,t4)->{
                        t.column(Topic::getStars);
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
                    join6().asTable("abcv").maxOrNull((t,t1,t2,t3,t4)->{
                        t.column(Topic::getStars);
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
                    join6().asTable("abcv").maxOrDefault((t,t1,t2,t3,t4)->{
                        t.column(Topic::getStars);
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
                    join6().asTable("abcv").minOrNull((t,t1,t2,t3,t4)->{
                        t.column(Topic::getStars);
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
                    join6().asTable("abcv").minOrDefault((t,t1,t2,t3,t4)->{
                        t.column(Topic::getStars);
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
                    join6().asTable("abcv").avgOrNull((t,t1,t2,t3,t4)->{
                        t.column(Topic::getStars);
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
                    join6().asTable("abcv").avgBigDecimalOrNull((t,t1,t2,t3,t4)->{
                        t.column(Topic::getStars);
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
                    join6().asTable("abcv").avgFloatOrNull((t,t1,t2,t3,t4)->{
                        t.column(Topic::getStars);
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
                    join6().asTable("abcv").avgOrDefault((t,t1,t2,t3,t4)->{
                        t.column(Topic::getStars);
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
                    join6().asTable("abcv").avgBigDecimalOrDefault((t,t1,t2,t3,t4)->{
                        t.column(Topic::getStars);
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
                    join6().asTable("abcv").avgFloatOrDefault((t,t1,t2,t3,t4)->{
                        t.column(Topic::getStars);
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
                    join7().asTable("abcv").sumOrDefault((t,t1,t2,t3)->{
                        t.column(Topic::getStars);
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
                    join7().asTable("abcv").sumOrNull((t,t1,t2,t3)->{
                        t.column(Topic::getStars);
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
                    join7().asTable("abcv").sumBigDecimalOrDefault((t,t1,t2,t3)->{
                        t.column(Topic::getStars);
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
                    join7().asTable("abcv").sumBigDecimalOrNull((t,t1,t2,t3)->{
                        t.column(Topic::getStars);
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
                    join7().asTable("abcv").maxOrNull((t,t1,t2,t3)->{
                        t.column(Topic::getStars);
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
                    join7().asTable("abcv").maxOrDefault((t,t1,t2,t3)->{
                        t.column(Topic::getStars);
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
                    join7().asTable("abcv").minOrNull((t,t1,t2,t3)->{
                        t.column(Topic::getStars);
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
                    join7().asTable("abcv").minOrDefault((t,t1,t2,t3)->{
                        t.column(Topic::getStars);
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
                    join7().asTable("abcv").avgOrNull((t,t1,t2,t3)->{
                        t.column(Topic::getStars);
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
                    join7().asTable("abcv").avgBigDecimalOrNull((t,t1,t2,t3)->{
                        t.column(Topic::getStars);
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
                    join7().asTable("abcv").avgFloatOrNull((t,t1,t2,t3)->{
                        t.column(Topic::getStars);
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
                    join7().asTable("abcv").avgOrDefault((t,t1,t2,t3)->{
                        t.column(Topic::getStars);
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
                    join7().asTable("abcv").avgBigDecimalOrDefault((t,t1,t2,t3)->{
                        t.column(Topic::getStars);
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
                    join7().asTable("abcv").avgFloatOrDefault((t,t1,t2,t3)->{
                        t.column(Topic::getStars);
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
                    join8().asTable("abcv").sumOrDefault((t,t1,t2)->{
                        t.column(Topic::getStars);
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
                    join8().asTable("abcv").sumOrNull((t,t1,t2)->{
                        t.column(Topic::getStars);
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
                    join8().asTable("abcv").sumBigDecimalOrDefault((t,t1,t2)->{
                        t.column(Topic::getStars);
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
                    join8().asTable("abcv").sumBigDecimalOrNull((t,t1,t2)->{
                        t.column(Topic::getStars);
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
                    join8().asTable("abcv").maxOrNull((t,t1,t2)->{
                        t.column(Topic::getStars);
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
                    join8().asTable("abcv").maxOrDefault((t,t1,t2)->{
                        t.column(Topic::getStars);
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
                    join8().asTable("abcv").minOrNull((t,t1,t2)->{
                        t.column(Topic::getStars);
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
                    join8().asTable("abcv").minOrDefault((t,t1,t2)->{
                        t.column(Topic::getStars);
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
                    join8().asTable("abcv").avgOrNull((t,t1,t2)->{
                        t.column(Topic::getStars);
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
                    join8().asTable("abcv").avgBigDecimalOrNull((t,t1,t2)->{
                        t.column(Topic::getStars);
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
                    join8().asTable("abcv").avgFloatOrNull((t,t1,t2)->{
                        t.column(Topic::getStars);
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
                    join8().asTable("abcv").avgOrDefault((t,t1,t2)->{
                        t.column(Topic::getStars);
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
                    join8().asTable("abcv").avgBigDecimalOrDefault((t,t1,t2)->{
                        t.column(Topic::getStars);
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
                    join8().asTable("abcv").avgFloatOrDefault((t,t1,t2)->{
                        t.column(Topic::getStars);
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
