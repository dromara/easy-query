package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.configuration.nameconversion.MapKeyNameConversion;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.oracle.config.OracleDatabaseConfiguration;
import com.easy.query.test.conversion.UpperMapKeyNameConversion;
import com.easy.query.test.dto.autodto.SchoolClassAOProp9;
import com.easy.query.test.entity.TestInsert;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.TopicAutoNative;
import com.easy.query.test.entity.TopicFile;
import com.easy.query.test.entity.proxy.TopicFileProxy;
import com.easy.query.test.entity.school.SchoolClass;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
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
            LinkedHashMap<String,Object> map = new LinkedHashMap<>();
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


    @Test
    public void testInsert(){
        DatabaseCodeFirst databaseCodeFirst = easyEntityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(Arrays.asList(TestInsert.class));
        codeFirstCommand.executeWithTransaction(arg->{
            System.out.println(arg.getSQL());
            arg.commit();
        });

        easyEntityQuery.deletable(TestInsert.class).disableLogicDelete().allowDeleteStatement(true)
                .where(t -> t.id().isNotNull())
                .executeRows();
        ArrayList<TestInsert> testInserts = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            TestInsert testInsert = new TestInsert();
            testInsert.setId(String.valueOf(i));
            testInsert.setColumn1(i%2==0?i+":column1":null);
            testInsert.setColumn2(i%3==0?i+":column2":null);
            testInserts.add(testInsert);
        }


        ListenerContext listenerContext = new ListenerContext(true);
        listenerContextManager.startListen(listenerContext);

        easyEntityQuery.insertable(testInserts).batch().executeRows();

        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArgs());
        Assert.assertEquals(4, listenerContext.getJdbcExecuteAfterArgs().size());

        {

            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(0);
            Assert.assertEquals("INSERT INTO `t_test_insert` (`id`,`column1`,`column2`) VALUES (?,?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("0(String),0:column1(String),0:column2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            Assert.assertEquals("6(String),6:column1(String),6:column2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(1)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(1);
            Assert.assertEquals("INSERT INTO `t_test_insert` (`id`) VALUES (?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            Assert.assertEquals("5(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(1)));
            Assert.assertEquals("7(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(2)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(2);
            Assert.assertEquals("INSERT INTO `t_test_insert` (`id`,`column1`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("2(String),2:column1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            Assert.assertEquals("4(String),4:column1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(1)));
            Assert.assertEquals("8(String),8:column1(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(2)));
        }
        {
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArgs().get(3);
            Assert.assertEquals("INSERT INTO `t_test_insert` (`id`,`column2`) VALUES (?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("3(String),3:column2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        }
        System.out.println("1");
        easyEntityQuery.deletable(TestInsert.class).disableLogicDelete().allowDeleteStatement(true)
                .where(t -> t.id().isNotNull())
                .executeRows();
    }


    @Test
    public void tesInsertMapKey2(){
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setExecutorCorePoolSize(1);
                    op.setExecutorMaximumPoolSize(2);
                    op.setMaxShardingQueryLimit(1);
                    op.setUpdateStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS);
                })
                .useDatabaseConfigure(new OracleDatabaseConfiguration())
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .replaceService(MapKeyNameConversion.class, new UpperMapKeyNameConversion())
                .build();
        DefaultEasyEntityQuery defaultEasyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);

        {


            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                LinkedHashMap<String,Object> map = new LinkedHashMap<>();
                map.put("id","1");
                map.put("stars","2");
                map.put("UserName","2");

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
                Assert.assertEquals("INSERT INTO \"xxx\" (\"ID\",\"STARS\",\"USERNAME\") VALUES (?,ifnull(\"STARS\",0)+?,?)", sql);
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("INSERT INTO \"xxx\" (\"ID\",\"STARS\",\"USERNAME\") VALUES (?,ifnull(\"STARS\",0)+?,?)", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),2(String),2(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }
        {

            try {

                HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                stringObjectHashMap.put("id", "123");
                stringObjectHashMap.put("name", "123");
                stringObjectHashMap.put("name1", null);
                easyQueryClient.mapUpdatable(stringObjectHashMap).asTable("aaa")
                        .setSQLStrategy(SQLExecuteStrategyEnum.ALL_COLUMNS)
                        .whereColumns("id")
                        .executeRows();
            } catch (Exception ex) {
                Throwable cause = ex.getCause();
                Assert.assertTrue(cause instanceof EasyQuerySQLStatementException);
                EasyQuerySQLStatementException cause1 = (EasyQuerySQLStatementException) cause;
                String sql = cause1.getSQL();
                Assert.assertEquals("UPDATE \"aaa\" SET \"NAME\" = ?,\"NAME1\" = ? WHERE \"ID\" = ?", sql);
            }
        }

    }
}
