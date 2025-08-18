package com.easy.query.core.expression.parser.core.base.core;

import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.scec.SQLAliasNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLAliasNativePropertyExpressionContextImpl;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/7/31 13:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAsPropertyNative<TChain> extends SQLTableOwner, ChainCast<TChain> {
    <T> SQLNative<T> getSQLNative();
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
    default TChain sqlNativeSegment(String sqlSegment, SQLActionExpression1<SQLAliasNativePropertyExpressionContext> contextConsume){
        return sqlNativeSegment(true,sqlSegment,contextConsume);
    }

    default TChain sqlNativeSegment(boolean condition,String sqlSegment, SQLActionExpression1<SQLAliasNativePropertyExpressionContext> contextConsume){
        if(condition){
            getSQLNative().sqlNativeSegment(sqlSegment, context->{
                contextConsume.apply(new SQLAliasNativePropertyExpressionContextImpl(getTable(),context));
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
            getSQLNative().sqlNativeSegment(sqlSegment, context->{
                sqlFunction.consume(new SQLNativeChainExpressionContextImpl(getTable(),context));
            });
        }
        return castChain();
    }

    default TChain sqlFuncAs(SQLFunction sqlFunction, String propertyAlias){
        return sqlFuncAs(true,sqlFunction,propertyAlias);
    }
    default TChain sqlFuncAs(boolean condition, SQLFunction sqlFunction, String propertyAlias){
        if(condition){
            String sqlSegment = sqlFunction.sqlSegment(getTable());
            getSQLNative().sqlNativeSegment(sqlSegment, context->{
                SQLNativeChainExpressionContextImpl sqlNativeChainExpressionContext = new SQLNativeChainExpressionContextImpl(getTable(), context);
                sqlNativeChainExpressionContext.setPropertyAlias(propertyAlias);
                sqlFunction.consume(sqlNativeChainExpressionContext);
            });
        }
        return castChain();
    }
    default TChain sqlFuncCustomAs(boolean condition, SQLFunction sqlFunction, String propertyAlias){
        if(condition){
            String sqlSegment = sqlFunction.sqlSegment(getTable());
            getSQLNative().sqlNativeSegment(sqlSegment, context->{
                SQLNativeChainExpressionContextImpl sqlNativeChainExpressionContext = new SQLNativeChainExpressionContextImpl(getTable(), context);
                sqlNativeChainExpressionContext.setAlias(propertyAlias);
                sqlFunction.consume(sqlNativeChainExpressionContext);
            });
        }
        return castChain();
    }

}
