package com.easy.query.core.expression.parser.core.base.core;

import com.easy.query.core.expression.builder.core.SQLAsNative;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.scec.SQLAliasNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLAliasNativePropertyExpressionContextImpl;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/7/31 13:23
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAsPropertyNative<TChain> extends SQLTableOwner, ChainCast<TChain> {
    <T> SQLAsNative<T> getSQLAsNative();
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
    default TChain sqlNativeSegment(String sqlSegment, SQLExpression1<SQLAliasNativePropertyExpressionContext> contextConsume){
        return sqlNativeSegment(true,sqlSegment,contextConsume);
    }

    default TChain sqlNativeSegment(boolean condition,String sqlSegment, SQLExpression1<SQLAliasNativePropertyExpressionContext> contextConsume){
        if(condition){
            getSQLAsNative().sqlNativeSegment(sqlSegment,context->{
                contextConsume.apply(new SQLAliasNativePropertyExpressionContextImpl(getTable(),context));
            });
        }
        return castChain();
    }

    default TChain func(SQLFunction sqlFunction){
        return func(true,sqlFunction);
    }
    default TChain func(boolean condition, SQLFunction sqlFunction){
        if(condition){
            String sqlSegment = sqlFunction.sqlSegment();
            getSQLAsNative().sqlNativeSegment(sqlSegment,context->{
                sqlFunction.consume(new SQLAliasNativePropertyExpressionContextImpl(getTable(),context));
            });
        }
        return castChain();
    }

}
