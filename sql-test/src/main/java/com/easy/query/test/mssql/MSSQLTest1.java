package com.easy.query.test.mssql;

import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mssql.entity.MSSQLA;
import com.easy.query.test.mssql.entity.MSSQLB;
import com.easy.query.test.mssql.proxy.MSSQLADTOProxy;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/6/16 20:30
 * 文件说明
 *
 * @author xuejiaming
 */
public class MSSQLTest1 extends MsSQLBaseTest2{
    @Test
    public void test1(){

        ListenerContext listenerContext = new ListenerContext();
        listenerContextManager.startListen(listenerContext);

        try {

            List<MSSQLADTO> list = entityQuery.queryable(MSSQLA.class)
                    .leftJoin(MSSQLB.class,(m, m2) -> m.id().eq(m2.id()))
                    .select((m,m2) -> new MSSQLADTOProxy()
                            .code().set(m.code())
                            .name().set(m2.code())
                    )
                    .where(m -> {
                        m.name().eq("123");
                        m.code().eq("123");
                    }).toList();
        }catch (Exception ex){

        }
        Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
        JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
        Assert.assertEquals("SELECT t2.[code] AS [code],t2.[name] AS [name] FROM (SELECT t.[Code] AS [code],t1.[Code] AS [name] FROM [t_mssql_a] t LEFT JOIN [t_mssql_b] t1 ON t.[Id] = t1.[Id]) t2 WHERE t2.[name] = ? AND t2.[code] = ?", jdbcExecuteAfterArg.getBeforeArg().getSql());
        Assert.assertEquals("123(String),123(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));
        listenerContextManager.clear();
    }
}
