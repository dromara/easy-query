package com.easy.query.test;

import com.easy.query.api.proxy.client.DefaultEasyEntityQuery;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.basic.extension.listener.JdbcExecutorListener;
import com.easy.query.core.bootstrapper.EasyQueryBootstrapper;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.core.draft.Draft2;
import com.easy.query.core.proxy.impl.SQLSelectAllImpl;
import com.easy.query.core.proxy.sql.GroupKeys;
import com.easy.query.core.proxy.sql.Select;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.mssql.config.MsSQLDatabaseConfiguration;
import com.easy.query.test.doc.entity.DocUser;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.proxy.BlogEntityProxy;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.listener.ListenerContextManager;
import com.easy.query.test.listener.MyJdbcListener;
import com.easy.query.test.proxy.InsertOrUpdateMultiKeyEntityProxy;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2024/7/9 13:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertOrUpdateTest extends BaseTest{
    @Test
    public void test1(){
        ListenerContextManager listenerContextManager = new ListenerContextManager();
        MyJdbcListener myJdbcListener = new MyJdbcListener(listenerContextManager);
        EasyQueryClient easyQueryClient = EasyQueryBootstrapper.defaultBuilderConfiguration()
                .setDefaultDataSource(dataSource)
                .optionConfigure(op -> {
                    op.setDeleteThrowError(false);
                    op.setExecutorCorePoolSize(1);
                    op.setExecutorMaximumPoolSize(2);
                    op.setMaxShardingQueryLimit(1);
                })
                .useDatabaseConfigure(new MsSQLDatabaseConfiguration())
                .replaceService(JdbcExecutorListener.class, myJdbcListener)
                .build();

        DefaultEasyEntityQuery defaultEasyEntityQuery = new DefaultEasyEntityQuery(easyQueryClient);


//        List<Draft2<Boolean, Boolean>> list = defaultEasyEntityQuery.queryable(DocUser.class)
//                .manyJoin(o -> o.bankCards())
//                .where(user -> {
//                    user.bankCards().any();
//                    user.bankCards().none();
//                    user.bankCards().anyValue().eq(false);
//                    user.bankCards().noneValue().eq(true);
//
//                })
//                .select(user -> Select.DRAFT.of(
//                        user.bankCards().anyValue(),
//                        user.bankCards().noneValue()
//                ))
//                .toList();

        {

            ListenerContext listenerContext = new ListenerContext();
            listenerContextManager.startListen(listenerContext);

            try {

                InsertOrUpdateMultiKeyEntity insertOrUpdateMultiKeyEntity = new InsertOrUpdateMultiKeyEntity();
                insertOrUpdateMultiKeyEntity.setColumn1("1");
                insertOrUpdateMultiKeyEntity.setColumn2("2");
                insertOrUpdateMultiKeyEntity.setColumn3("3");
                insertOrUpdateMultiKeyEntity.setColumn4("4");
                insertOrUpdateMultiKeyEntity.setColumn5("5");

                long l = defaultEasyEntityQuery.insertable(insertOrUpdateMultiKeyEntity)
                        .setSQLStrategy(SQLExecuteStrategyEnum.ALL_COLUMNS)
                        .onConflictThen(o -> o.FETCHER.allFields(), o -> o.column2())
                        .executeRows();

//                long l2 = defaultEasyEntityQuery.insertable(insertOrUpdateMultiKeyEntity)
//                        .setSQLStrategy(SQLExecuteStrategyEnum.ALL_COLUMNS)
//                        .onConflictThen(o -> {
//                            return new SQLSelectAllImpl(o.getEntitySQLContext(), o.getTable(), null);
//                        })
//                        .executeRows();
            } catch (Exception ignore) {
            }
            Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
            JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
            Assert.assertEquals("MERGE INTO [t_user] t1 USING (SELECT ? AS [column1],? AS [column2],? AS [column3],? AS [column4],? AS [column5] ) t2 ON (t1.[column2] = t2.[column2]) WHEN MATCHED THEN UPDATE SET t1.[column3] = t2.[column3],t1.[column4] = t2.[column4],t1.[column5] = t2.[column5] WHEN NOT MATCHED THEN INSERT ([column1],[column2],[column3],[column4],[column5]) VALUES (t2.[column1],t2.[column2],t2.[column3],t2.[column4],t2.[column5]);", jdbcExecuteAfterArg.getBeforeArg().getSql());
            Assert.assertEquals("1(String),2(String),3(String),4(String),5(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
            listenerContextManager.clear();
        }

    }
}
