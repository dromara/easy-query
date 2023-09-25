package com.easy.query.api.proxy.sql.core;

import com.easy.query.api.proxy.sql.scec.SQLAliasNativeProxyExpressionContext;
import com.easy.query.api.proxy.sql.scec.SQLAliasNativeProxyExpressionContextImpl;
import com.easy.query.core.expression.builder.core.SQLAsNative;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/7/31 14:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAsProxyNative<TRProxy extends ProxyEntity<TRProxy, TR>, TR, TChain> extends ChainCast<TChain> {
    <T> SQLAsNative<T> getSQLAsNative();

    /**
     * 参数格式化 占位符 {0} {1}
     *
     * @param sqlSegment
     * @return
     */
    default TChain sqlNativeSegment(String sqlSegment) {
        return sqlNativeSegment(true, sqlSegment);
    }

    /**
     * 参数格式化 占位符 {0} {1}
     *
     * @param sqlSegment
     * @return
     */
    default TChain sqlNativeSegment(boolean condition, String sqlSegment) {
        return sqlNativeSegment(condition, sqlSegment, c -> {
        });
    }

    /**
     * 参数格式化 占位符 {0} {1}
     *
     * @param sqlSegment
     * @param contextConsume
     * @return
     */
    default TChain sqlNativeSegment(String sqlSegment, SQLExpression1<SQLAliasNativeProxyExpressionContext<TRProxy, TR>> contextConsume) {
        return sqlNativeSegment(true, sqlSegment, contextConsume);
    }

    /**
     * 参数格式化 占位符 {0} {1}
     *
     * @param sqlSegment
     * @param contextConsume
     * @return
     */
    default TChain sqlNativeSegment(boolean condition, String sqlSegment, SQLExpression1<SQLAliasNativeProxyExpressionContext<TRProxy, TR>> contextConsume) {
        if (condition) {
            getSQLAsNative().sqlNativeSegment(sqlSegment, context -> {
                contextConsume.apply(new SQLAliasNativeProxyExpressionContextImpl<>(context));
            });
        }
        return castChain();
    }
}
