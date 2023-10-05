package com.easy.query.api4j.sql.core;

import com.easy.query.api4j.sql.scec.SQLNativeLambdaExpressionContext;
import com.easy.query.api4j.sql.scec.SQLNativeLambdaExpressionContextImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;

/**
 * create time 2023/7/31 14:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLLambdaNative<TEntity,TChain> extends ChainCast<TChain> {
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
    default TChain sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativeLambdaExpressionContext<TEntity>> contextConsume){
        return sqlNativeSegment(true,sqlSegment,contextConsume);
    }

    default TChain sqlNativeSegment(boolean condition,String sqlSegment, SQLExpression1<SQLNativeLambdaExpressionContext<TEntity>> contextConsume){
        if(condition){
            getSQLPropertyNative().sqlNativeSegment(sqlSegment,context->{
                contextConsume.apply(new SQLNativeLambdaExpressionContextImpl<>(context));
            });
        }
        return castChain();
    }
    default TChain sqlFunc(SQLFuncLambdaInvoker<TEntity> sqlFuncLambdaInvoker){
        return sqlFunc(true,sqlFuncLambdaInvoker);
    }
    default TChain sqlFunc(boolean condition,SQLFuncLambdaInvoker<TEntity> sqlFuncLambdaInvoker){
        if(condition){
            String sqlSegment = sqlFuncLambdaInvoker.sqlSegment();
            getSQLPropertyNative().sqlNativeSegment(sqlSegment,context->{
                sqlFuncLambdaInvoker.contextConsume(new SQLNativeLambdaExpressionContextImpl<>(context));
            });
        }
        return castChain();
    }

}
