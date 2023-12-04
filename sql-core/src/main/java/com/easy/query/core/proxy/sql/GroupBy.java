package com.easy.query.core.proxy.sql;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.SQLGroupSelect;
import com.easy.query.core.proxy.impl.SQLGroupSelectImpl;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;

/**
 * create time 2023/12/2 17:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupBy {
    public static SQLGroupSelect sql(String sqlSegment) {
        return sql(true, sqlSegment, f -> {
        });
    }


    public static SQLGroupSelect sql(boolean condition, String sqlSegment) {
        if (condition) {
            return sql(true, sqlSegment, f -> {
            });
        }
        return SQLGroupSelect.empty;
    }

    public static SQLGroupSelect sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        return sql(true,sqlSegment,contextConsume);
    }

    public static SQLGroupSelect sql(boolean condition, String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        if (condition) {
            return new SQLGroupSelectImpl(f -> {
                f.sqlNativeSegment(sqlSegment, c -> {
                    contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
                });
            });
        }
        return SQLGroupSelect.empty;
    }
}
