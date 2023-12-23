package com.easy.query.core.proxy.sql;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLGroupByExpression;
import com.easy.query.core.proxy.core.draft.group.GroupKey1;
import com.easy.query.core.proxy.core.draft.group.GroupKeyFetcher;
import com.easy.query.core.proxy.core.draft.group.GroupKeyFetcherImpl;
import com.easy.query.core.proxy.core.draft.group.proxy.GroupKey1Proxy;
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


    public static <T1> GroupKeyFetcher<GroupKey1<T1>, GroupKey1Proxy<T1>> keys(PropTypeColumn<T1> column1) {
        GroupKey1Proxy<T1> groupKey1Proxy = new GroupKey1Proxy<>();
        GroupKeyFetcherImpl<GroupKey1<T1>, GroupKey1Proxy<T1>> groupKeyFetcher = new GroupKeyFetcherImpl<>(new GroupKey1<>(), groupKey1Proxy);
        groupKeyFetcher.fetch(0,column1);
        return groupKeyFetcher;
    }
    public static SQLGroupByExpression of(SQLGroupByExpression... groupByExpressions){
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
