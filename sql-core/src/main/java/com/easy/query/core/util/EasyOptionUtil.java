package com.easy.query.core.util;

import com.easy.query.core.basic.extension.print.JdbcSQLPrinter;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2024/9/29 16:45
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyOptionUtil {

    public static boolean isPrintSQL(ExpressionContext expressionContext) {
        Boolean printSQL = expressionContext.getPrintSQL();
        if (printSQL != null) {
            return printSQL;
        }
        QueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
        JdbcSQLPrinter jdbcSQLPrinter = runtimeContext.getJdbcSQLPrinter();
        if (jdbcSQLPrinter != null && jdbcSQLPrinter.printSQL() != null) {
            return jdbcSQLPrinter.printSQL();
        }
        return runtimeContext.getQueryConfiguration().getEasyQueryOption().isPrintSql();
    }
    public static boolean isPrintNavSQL(ExpressionContext expressionContext) {
        Boolean printNavSQL = expressionContext.getPrintNavSQL();
        if (printNavSQL != null) {
            return printNavSQL;
        }
        QueryRuntimeContext runtimeContext = expressionContext.getRuntimeContext();
        JdbcSQLPrinter jdbcSQLPrinter = runtimeContext.getJdbcSQLPrinter();
        if (jdbcSQLPrinter != null && jdbcSQLPrinter.printNavSQL() != null) {
            return jdbcSQLPrinter.printSQL();
        }
        return runtimeContext.getQueryConfiguration().getEasyQueryOption().isPrintNavSql();
    }
}
