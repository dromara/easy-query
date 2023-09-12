package com.easy.query.api4kt.sql.core;

import com.easy.query.api4kt.sql.scec.SQLAliasNativeLambdaKtExpressionContext;
import com.easy.query.api4kt.sql.scec.SQLAliasNativeLambdaKtExpressionContextImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.core.SQLAsPropertyNative;

/**
 * create time 2023/7/31 14:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAsLambdaKtNative<T1,TChain> {
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
    default TChain sqlNativeSegment(String sqlSegment, SQLExpression1<SQLAliasNativeLambdaKtExpressionContext<T1>> contextConsume){
        return sqlNativeSegment(true,sqlSegment,contextConsume);
    }
    /**
     * 参数格式化 占位符 {0} {1}
     * @param sqlSegment
     * @param contextConsume
     * @return
     */
    default TChain sqlNativeSegment(boolean condition,String sqlSegment, SQLExpression1<SQLAliasNativeLambdaKtExpressionContext<T1>> contextConsume){
        if(condition){
            getSQLAsPropertyNative().sqlNativeSegment(sqlSegment,context->{
                contextConsume.apply(new SQLAliasNativeLambdaKtExpressionContextImpl<>(context));
            });
        }
        return castTChain();
    }
}
