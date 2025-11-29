package com.easy.query.test;

import com.easy.query.api.proxy.base.StringProxy;
import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.entity.EntityMappingRule;
import com.easy.query.core.basic.entity.PropertyFirstEntityMappingRule;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.builder.core.ValueFilterFactory;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.oracle.config.OracleDatabaseConfiguration;
import com.easy.query.test.common.MyValueFilterFactory;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.entity.Topic2;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.vo.Topic2DTO;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2024/10/10 13:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpdateTest2 extends BaseTest {
    @Test
    public void testUpdateNull() {
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
                .build();
        DefaultEasyEntityQuery defaultEasyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);

        EasyQueryOption easyQueryOption = defaultEasyEntityQuery.getRuntimeContext().getQueryConfiguration().getEasyQueryOption();
        SQLExecuteStrategyEnum updateStrategy = easyQueryOption.getUpdateStrategy();

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                Topic topic = new Topic();
                topic.setId("12312333sss");
                topic.setTitle("123");
                defaultEasyEntityQuery.updatable(topic).executeRows();
            } catch (Exception ex) {

            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("UPDATE \"t_topic\" SET \"title\" = ? WHERE \"id\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String),12312333sss(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                Topic topic = new Topic();
                topic.setId("12312333sss");
                topic.setTitle("123");
                topic.setStars(1);
                defaultEasyEntityQuery.updatable(topic).executeRows();
            } catch (Exception ex) {

            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("UPDATE \"t_topic\" SET \"stars\" = ?,\"title\" = ? WHERE \"id\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(Integer),123(String),12312333sss(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }

    }

    @Test
    public void update1() {
        easyEntityQuery.updatable(Topic.class)
                .where(t -> {
                    t.id().in(easyEntityQuery.queryable(Topic.class).select(t1 -> new StringProxy(t1.id().max())).select("*"));
                    t.id().eq("x123321xxs");
                }).setColumns(t -> t.stars().set(123123))
                .executeRows();
    }


    @Test
    public void testUpdateValueTest() {
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
                .replaceService(ValueFilterFactory.class, MyValueFilterFactory.class)
                .build();
        DefaultEasyEntityQuery defaultEasyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);

        EasyQueryOption easyQueryOption = defaultEasyEntityQuery.getRuntimeContext().getQueryConfiguration().getEasyQueryOption();
        SQLExecuteStrategyEnum updateStrategy = easyQueryOption.getUpdateStrategy();

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                defaultEasyEntityQuery.updatable(Topic.class)
                        .setColumns(t -> t.title().set("1"))
                        .where(t -> t.stars().eq((Integer) null))
                        .executeRows();

            } catch (Exception ex) {

            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("UPDATE \"t_topic\" SET \"title\" = ? WHERE \"stars\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                defaultEasyEntityQuery.deletable(Topic.class)
                        .where(t -> t.stars().eq((Integer) null))
                        .executeRows();

            } catch (Exception ex) {

            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("DELETE FROM \"t_topic\" WHERE \"stars\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("null(null)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }
        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                List<Topic> list = defaultEasyEntityQuery.queryable(Topic.class)
                        .where(t -> t.stars().eq((Integer) null))
                        .where(t -> t.title().eq("123"))
                        .toList();

            } catch (Exception ex) {

            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT \"id\",\"stars\",\"title\",\"create_time\" FROM \"t_topic\" WHERE \"title\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }

    }


//    @Test
//    public  void updateTest1(){
//        ArrayList<Topic> topics = new ArrayList<>();
//        {
//
//            Topic topic = new Topic();
//            topic.setId("ax1");
//            topic.setTitle("ax1");
//            topics.add(topic);
//        }
//        {
//
//            Topic topic = new Topic();
//            topic.setId("ax2");
//            topic.setTitle("ax2");
//            topics.add(topic);
//        }
//        {
//
//            Topic topic = new Topic();
//            topic.setId("ax3");
//            topic.setTitle("ax3");
//            topics.add(topic);
//        }
//        easyEntityQuery.updatable(topics)
//                .setColumns(t -> t.FETCHER.title());
//    }


    @Test
    public void test11() {

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
                .replaceService(EntityMappingRule.class, PropertyFirstEntityMappingRule.class)
                .build();
        DefaultEasyEntityQuery eq = new DefaultEasyEntityQuery(easyQueryClient);


        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);
            try {
                eq.queryable(Topic2.class)
                        .where(t -> t.title().eq("123"))
                        .select(Topic2DTO.class, t -> t.FETCHER.allFields())
                        .toList();

            } catch (Exception ex) {

            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("SELECT t.\"id\",t.\"abc\" AS \"title\" FROM \"t_topic\" t WHERE t.\"abc\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }
    }
}
