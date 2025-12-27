package com.easy.query.test.pgsql;

import com.easy.query.api.proxy.util.EasyProxyParamExpressionUtil;
import com.easy.query.core.basic.extension.listener.JdbcExecuteAfterArg;
import com.easy.query.core.proxy.columns.types.SQLStringTypeColumn;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.util.EasySQLUtil;
import com.easy.query.test.listener.ListenerContext;
import com.easy.query.test.mysql8.entity.TableNoKey;
import org.jetbrains.annotations.NotNull;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/12/27 15:21
 * 文件说明
 *
 * @author xuejiaming
 */
public class QueryTest18 extends PgSQLBaseTest {


    @Test
    public void testLikeStartsWith() {
        String id = "testLikeStartsWith";
        entityQuery.deletable(TableNoKey.class).where(t -> {
            t.column1().eq(id);
        }).executeRows();
        try {

            TableNoKey tableNoKey = new TableNoKey();
            tableNoKey.setColumn1(id);
            tableNoKey.setColumn2("小_明%在哪里");
            entityQuery.insertable(tableNoKey).executeRows();


            List<TableNoKey> list = entityQuery.queryable(TableNoKey.class)
                    .where(t -> {
                        startsWith(t.column2(), "小_明%");
                    }).toList();

            Assert.assertTrue(list.size() > 0);


            {


                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                List<TableNoKey> list2 = entityQuery.queryable(TableNoKey.class)
                        .where(t -> {
                            t.column2().startsWith("小_明%");
                        }).toList();

                listenerContextManager.clear();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT \"column1\",\"column2\",\"column3\" FROM \"no_key_table\" WHERE \"column2\" LIKE CONCAT((?)::TEXT,'%') ESCAPE '\\'", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("小\\_明\\%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

                Assert.assertTrue(list2.size() > 0);
            }
            {


                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                List<TableNoKey> list2 = entityQuery.queryable(TableNoKey.class)
                        .where(t -> {
                            t.column2().contains("小_明%");
                        }).toList();

                listenerContextManager.clear();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT \"column1\",\"column2\",\"column3\" FROM \"no_key_table\" WHERE \"column2\" LIKE CONCAT('%',(?)::TEXT,'%') ESCAPE '\\'", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("小\\_明\\%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

                Assert.assertTrue(list2.size() > 0);
            }
            {


                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                List<TableNoKey> list2 = entityQuery.queryable(TableNoKey.class)
                        .where(t -> {
                            t.column2().endsWith("小_明%在哪里");
                        }).toList();

                listenerContextManager.clear();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT \"column1\",\"column2\",\"column3\" FROM \"no_key_table\" WHERE \"column2\" LIKE CONCAT('%',(?)::TEXT) ESCAPE '\\'", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("小\\_明\\%在哪里(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

                Assert.assertTrue(list2.size() > 0);
            }
            {


                ListenerContext listenerContext = new ListenerContext();
                listenerContextManager.startListen(listenerContext);

                List<TableNoKey> list2 = entityQuery.queryable(TableNoKey.class)
                        .where(t -> {
                            t.column2().startsWith("小_明%");
                            t.column2().contains("小_明%");
                        }).toList();

                listenerContextManager.clear();
                Assert.assertNotNull(listenerContext.getJdbcExecuteAfterArg());
                JdbcExecuteAfterArg jdbcExecuteAfterArg = listenerContext.getJdbcExecuteAfterArg();
                Assert.assertEquals("SELECT \"column1\",\"column2\",\"column3\" FROM \"no_key_table\" WHERE \"column2\" LIKE CONCAT((?)::TEXT,'%') ESCAPE '\\' AND \"column2\" LIKE CONCAT('%',(?)::TEXT,'%') ESCAPE '\\'", jdbcExecuteAfterArg.getBeforeArg().getSql());
                Assert.assertEquals("小\\_明\\%(String),小\\_明\\%(String)", EasySQLUtil.sqlParameterToString(jdbcExecuteAfterArg.getBeforeArg().getSqlParameters().get(0)));

                Assert.assertTrue(list2.size() > 0);
            }


        } finally {

            entityQuery.deletable(TableNoKey.class).where(t -> {
                t.column1().eq(id);
            }).executeRows();
        }

    }
    public static void startsWith(SQLStringTypeColumn<?> column, @NotNull String matchValue) {
        if (matchValue.contains("%") || matchValue.contains("_")) {
            Expression expression = EasyProxyParamExpressionUtil.parseContextExpressionByParameters(column);
            String escape = escape(matchValue);
            expression.rawSQLCommand("{0} LIKE CONCAT({1},'%') ESCAPE '\\'", column, escape);
        } else {
            column.startsWith(matchValue);
        }
    }

    /**
     * 转义 LIKE 中的特殊字符：%, _, \
     * 使用 ESCAPE '\'
     */
    public static String escape(String input) {
        if (input == null) return null;

        return input
                .replace("\\", "\\\\")   // 转义反斜杠
                .replace("%", "\\%")      // 转义百分号
                .replace("_", "\\_");     // 转义下划线
    }
}
