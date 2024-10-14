package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.oracle.config.OracleDatabaseConfiguration;
import com.easy.query.test.entity.Topic;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import org.junit.Assert;
import org.junit.Test;

/**
 * create time 2024/10/10 13:36
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpdateTest2  extends BaseTest{

    @Test
    public void testUpdateNull(){
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
            }catch (Exception ex){

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
            }catch (Exception ex){

            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("UPDATE \"t_topic\" SET \"stars\" = ?,\"title\" = ? WHERE \"id\" = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(Integer),123(String),12312333sss(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();

        }

    }
}
