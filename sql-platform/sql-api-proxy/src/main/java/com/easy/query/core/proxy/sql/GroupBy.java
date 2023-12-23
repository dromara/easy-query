package com.easy.query.core.proxy.sql;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.impl.SQLGroupSelectImpl;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/12/2 17:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class GroupBy {
    /**
     * 请使用 {@link #keys(SQLGroupByExpression...)}
     * @param groupByExpressions
     * @return
     */

    @Deprecated
    public static SQLGroupByExpression of(SQLGroupByExpression... groupByExpressions){
        return keys(groupByExpressions);
    }
    public static SQLGroupByExpression keys(SQLGroupByExpression... groupByExpressions){
        if(EasyArrayUtil.isNotEmpty(groupByExpressions)){
            SQLGroupByExpression firstGroupByExpression = groupByExpressions[0];
            for (int i = 1; i < groupByExpressions.length; i++) {
                firstGroupByExpression = firstGroupByExpression._thenBy(groupByExpressions[i]);
            }
            return firstGroupByExpression;
        }
        return SQLGroupByExpression.empty;
    }
    public static SQLGroupByExpression sql(String sqlSegment) {
        return sql(true, sqlSegment, f -> {
        });
    }


    public static SQLGroupByExpression sql(boolean condition, String sqlSegment) {
        if (condition) {
            return sql(true, sqlSegment, f -> {
            });
        }
        return SQLGroupByExpression.empty;
    }

    public static SQLGroupByExpression sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        return sql(true,sqlSegment,contextConsume);
    }

    public static SQLGroupByExpression sql(boolean condition, String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        if (condition) {
            return new SQLGroupSelectImpl(f -> {
                f.sqlNativeSegment(sqlSegment, c -> {
                    contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
                });
            });
        }
        return SQLGroupByExpression.empty;
    }
}
