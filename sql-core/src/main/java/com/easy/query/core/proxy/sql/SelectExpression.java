package com.easy.query.core.proxy.sql;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelect;
import com.easy.query.core.proxy.SQLSelectAs;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;
import com.easy.query.core.proxy.impl.SQLSelectGroupKeyAsImpl;
import com.easy.query.core.proxy.impl.SQLSelectImpl;
import com.easy.query.core.proxy.sql.scec.SQLAliasNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLAliasNativeProxyExpressionContextImpl;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;

/**
 * create time 2023/12/2 17:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class SelectExpression {


    public static SQLSelect groupKeys(int index) {
        return new SQLSelectGroupKeyAsImpl(index);
    }

    public static SQLSelect sql(String sqlSegment) {
        return sql(sqlSegment, f -> {
        });
    }

    public static SQLSelect sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        return new SQLSelectImpl(f -> {
            f.sqlNativeSegment(sqlSegment, c -> {
                contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
            });
        });
    }

    public static <TRProxy extends ProxyEntity<TRProxy, TR>, TR> SQLSelectAs sqlAlias(TRProxy proxy, String sqlSegment) {
        return sqlAlias(proxy, sqlSegment, f -> {
        });
    }

    public static <TRProxy extends ProxyEntity<TRProxy, TR>, TR> SQLSelectAs sqlAlias(TRProxy proxy, String sqlSegment, SQLExpression1<SQLAliasNativeProxyExpressionContext<TRProxy, TR>> contextConsume) {
        return new SQLSelectAsImpl(s -> {
            s.sqlNativeSegment(sqlSegment, c -> {
                contextConsume.apply(new SQLAliasNativeProxyExpressionContextImpl<>(c));
            });
        });
    }
}
