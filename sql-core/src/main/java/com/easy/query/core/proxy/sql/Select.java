package com.easy.query.core.proxy.sql;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.impl.SQLSelectAsImpl;
import com.easy.query.core.proxy.impl.SQLSelectGroupKeyAsImpl;
import com.easy.query.core.proxy.impl.SQLSelectImpl;
import com.easy.query.core.proxy.sql.scec.SQLAliasNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLAliasNativeProxyExpressionContextImpl;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.core.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;
import com.easy.query.core.util.EasyArrayUtil;

/**
 * create time 2023/12/2 17:04
 * 文件说明
 *
 * @author xuejiaming
 */
public class Select {

    public static SQLSelectExpression of(SQLSelectExpression... selects){
        if (EasyArrayUtil.isNotEmpty(selects)) {
            SQLSelectExpression firstSQLSelectAs = selects[0];
            for (int i = 1; i < selects.length; i++) {
                firstSQLSelectAs = firstSQLSelectAs.then(selects[i]);
            }
            return firstSQLSelectAs;
        }
        return SQLSelectAsExpression.empty;
    }
    public static SQLSelectAsExpression of(SQLSelectAsExpression... selectAss){

        if (EasyArrayUtil.isNotEmpty(selectAss)) {
            SQLSelectAsExpression firstSQLSelectAs = selectAss[0];
            for (int i = 1; i < selectAss.length; i++) {
                firstSQLSelectAs = firstSQLSelectAs.then(selectAss[i]);
            }
            return firstSQLSelectAs;
        }
        return SQLSelectAsExpression.empty;
    }


    public static SQLSelectExpression groupKeys(int index) {
        return new SQLSelectGroupKeyAsImpl(index);
    }

    public static SQLSelectExpression sql(String sqlSegment) {
        return sql(sqlSegment, f -> {
        });
    }

    public static SQLSelectExpression sql(String sqlSegment, SQLExpression1<SQLNativeProxyExpressionContext> contextConsume) {
        return new SQLSelectImpl(f -> {
            f.sqlNativeSegment(sqlSegment, c -> {
                contextConsume.apply(new SQLNativeProxyExpressionContextImpl(c));
            });
        });
    }

    public static <TRProxy extends ProxyEntity<TRProxy, TR>, TR> SQLSelectAsExpression sqlAlias(TRProxy proxy, String sqlSegment) {
        return sqlAlias(proxy, sqlSegment, f -> {
        });
    }

    public static <TRProxy extends ProxyEntity<TRProxy, TR>, TR> SQLSelectAsExpression sqlAlias(TRProxy proxy, String sqlSegment, SQLExpression1<SQLAliasNativeProxyExpressionContext<TRProxy, TR>> contextConsume) {
        return new SQLSelectAsImpl(s->{
            s.sqlNativeSegment(sqlSegment, c -> {
                contextConsume.apply(new SQLAliasNativeProxyExpressionContextImpl<>(c));
            });
        },s -> {
            s.sqlNativeSegment(sqlSegment, c -> {
                contextConsume.apply(new SQLAliasNativeProxyExpressionContextImpl<>(c));
            });
        });
    }
}
