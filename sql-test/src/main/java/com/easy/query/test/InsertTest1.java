package com.easy.query.test;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicAutoNative;
import com.easy.query.test.listener.ListenerContext;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.function.Supplier;

/**
 * create time 2024/1/1 17:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertTest1 extends BaseTest {
    @Test
    public void inertTest1() {

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Topic topic = new Topic();
        topic.setId(String.valueOf(1));
        topic.setStars(1 + 100);
        topic.setTitle("标题" + 1);
        topic.setCreateTime(LocalDateTime.of(2021, 1, 1, 1, 1, 1));

        Supplier<Exception> f = () -> {
            try {
                easyEntityQuery.insertable(topic)
                        .asTable("t_topic_abc")
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
        Assert.assertEquals("INSERT INTO `t_topic_abc` (`id`,`stars`,`title`,`create_time`) VALUES (?,?,?,?)", easyQuerySQLStatementException.getSQL());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("INSERT INTO `t_topic_abc` (`id`,`stars`,`title`,`create_time`) VALUES (?,?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),101(Integer),标题1(String),2021-01-01T01:01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
    public void inertTest2() {


        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        Topic topic = new Topic();
        topic.setId(String.valueOf(1));
        topic.setStars(1 + 100);
        topic.setTitle("标题" + 1);
        topic.setCreateTime(LocalDateTime.of(2021, 1, 1, 1, 1, 1));

        Supplier<Exception> f = () -> {
            try {
                easyEntityQuery.insertable(topic)
                        .asTable("t_topic_abc")
                        .onDuplicateKeyUpdate()
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
        Assert.assertEquals("INSERT INTO `t_topic_abc` (`id`,`stars`,`title`,`create_time`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE `stars` = VALUES(`stars`), `title` = VALUES(`title`), `create_time` = VALUES(`create_time`)", easyQuerySQLStatementException.getSQL());

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("INSERT INTO `t_topic_abc` (`id`,`stars`,`title`,`create_time`) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE `stars` = VALUES(`stars`), `title` = VALUES(`title`), `create_time` = VALUES(`create_time`)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),101(Integer),标题1(String),2021-01-01T01:01:01(LocalDateTime)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }

    @Test
     public void testColumnConfigure(){
         //easyQueryClient.insertable()
//                .columnConfigure(x->{
//                    x.getConfigurer().column()
//                })
//
//        easyQuery.insertable(topicAuto)
//                .asTable("xxxxx")
//                .columnConfigure(o -> o.column(TopicAutoNative::getId, "sde.next_rowid('sde',{0})", (context, sqlParameter) -> {
//                    context.value(sqlParameter);
//                })).executeRows();
     }
    @Test
    public void testColumnConfigure2() {
        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);
        try {
            HashMap<String,Object> map = new HashMap<>();
            map.put("id","1");
            map.put("stars","2");

            easyQueryClient.mapInsertable(map)
                    .asTable("xxx")
                    .columnConfigure(x -> {
                        x.column("stars", "ifnull({0},0)+{1}", (context, sqlParameter) -> {
                            context.expression("stars")
                                    .value(sqlParameter);
                        });
                    }).executeRows();
        } catch (Exception ex) {
            Throwable cause = ex.getCause();
            Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
            EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
            String sql = cause1.getSQL();
            Assert.assertEquals("INSERT INTO `xxx` (`id`,`stars`) VALUES (?,ifnull(`stars`,0)+?)", sql);
        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("INSERT INTO `xxx` (`id`,`stars`) VALUES (?,ifnull(`stars`,0)+?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("1(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
}
