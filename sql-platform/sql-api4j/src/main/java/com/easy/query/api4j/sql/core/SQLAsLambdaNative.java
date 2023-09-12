package com.easy.query.api4j.sql.core;

import com.easy.query.api4j.sql.scec.SQLAliasNativeLambdaExpressionContext;
import com.easy.query.api4j.sql.scec.SQLAliasNativeLambdaExpressionContextImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.core.SQLAsPropertyNative;

/**
 * create time 2023/7/31 14:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAsLambdaNative<TEntity,TChain> {
    <T> SQLAsPropertyNative<T> getSQLAsPropertyNative();
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
    default TChain sqlNativeSegment(String sqlSegment, SQLExpression1<SQLAliasNativeLambdaExpressionContext<TEntity>> contextConsume){
        return sqlNativeSegment(true,sqlSegment,contextConsume);
    }

    default TChain sqlNativeSegment(boolean condition,String sqlSegment, SQLExpression1<SQLAliasNativeLambdaExpressionContext<TEntity>> contextConsume){
        if(condition){
            getSQLAsPropertyNative().sqlNativeSegment(sqlSegment,context->{
                contextConsume.apply(new SQLAliasNativeLambdaExpressionContextImpl<>(context));
            });
        }
        return castTChain();
    }

}
