package com.easy.query.api4j.sql.core;

import com.easy.query.api4j.sql.scec.SQLNativeLambdaExpressionContext;
import com.easy.query.api4j.sql.scec.SQLNativeLambdaExpressionContextImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.SQLPropertyNative;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/7/31 14:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLLambdaNative<TEntity,TChain> extends EntitySQLTableOwner<TEntity>,ChainCast<TChain> {
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
    default TChain sqlFunc(SQLFunction sqlFunction){
        return sqlFunc(true,sqlFunction);
    }
    default TChain sqlFunc(boolean condition, SQLFunction sqlFunction){
        if(condition){
            String sqlSegment = sqlFunction.sqlSegment(getTable());
            getSQLPropertyNative().sqlNativeSegment(sqlSegment,context->{
                sqlFunction.consume(context.getSQLNativeChainExpressionContext());
            });
        }
        return castChain();
    }

}
