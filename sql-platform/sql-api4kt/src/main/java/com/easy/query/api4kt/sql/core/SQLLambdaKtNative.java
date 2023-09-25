package com.easy.query.api4kt.sql.core;

import com.easy.query.api4kt.sql.scec.SQLNativeLambdaKtExpressionContext;
import com.easy.query.api4kt.sql.scec.SQLNativeLambdaKtExpressionContextImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;

/**
 * create time 2023/7/31 14:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLLambdaKtNative<T1,TChain> extends ChainCast<TChain> {
    <T> SQLPropertyNative<T> getSQLPropertyNative();


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
    default TChain sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeLambdaKtExpressionContext<T1>> contextConsume){
        return sqlNativeSegment(true,sqlSegment,contextConsume);
    }
    /**
     * 参数格式化 占位符 {0} {1}
     * @param sqlSegment
     * @param contextConsume
     * @return
     */
    default TChain sqlNativeSegment(boolean condition,String sqlSegment, SQLExpression1<SQLNativeLambdaKtExpressionContext<T1>> contextConsume){
        if(condition){
            getSQLPropertyNative().sqlNativeSegment(sqlSegment,context->{
                contextConsume.apply(new SQLNativeLambdaKtExpressionContextImpl<>(context));
            });
        }
        return castChain();
    }
}
