package com.easy.query.api.proxy.sql.core;

import com.easy.query.api.proxy.sql.scec.SQLAliasNativeProxyExpressionContext;
import com.easy.query.api.proxy.sql.scec.SQLAliasNativeProxyExpressionContextImpl;
import com.easy.query.core.expression.builder.core.SQLAsNative;
import com.easy.query.core.expression.lambda.SQLExpression1;

/**
 * create time 2023/7/31 14:35
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAsProxyNative<TChain> {
    <T> SQLAsNative<T> getSQLAsNative();
    TChain castTChain();

    /**
     * 参数格式化 占位符 {0} {1}
     * @param sqlSegment
     * @return
     */
    default TChain sqlNativeSegment(String sqlSegment){
        return sqlNativeSegment(true,sqlSegment);
    }
    /**
     * 参数格式化 占位符 {0} {1}
     * @param sqlSegment
     * @return
     */
    default TChain sqlNativeSegment(boolean condition,String sqlSegment){
        return sqlNativeSegment(condition,sqlSegment,c->{});
    }

    /**
     * 参数格式化 占位符 {0} {1}
     * @param sqlSegment
     * @param contextConsume
     * @return
     */
    default TChain sqlNativeSegment(String sqlSegment, SQLExpression1<SQLAliasNativeProxyExpressionContext> contextConsume){
        return sqlNativeSegment(true,sqlSegment,contextConsume);
    }
    /**
     * 参数格式化 占位符 {0} {1}
     * @param sqlSegment
     * @param contextConsume
     * @return
     */
    default TChain sqlNativeSegment(boolean condition,String sqlSegment, SQLExpression1<SQLAliasNativeProxyExpressionContext> contextConsume){
        if(condition){
            getSQLAsNative().sqlNativeSegment(sqlSegment,context->{
                contextConsume.apply(new SQLAliasNativeProxyExpressionContextImpl(context));
            });
        }
        return castTChain();
    }
}
