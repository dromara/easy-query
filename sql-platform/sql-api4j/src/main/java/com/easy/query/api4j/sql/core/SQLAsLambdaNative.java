package com.easy.query.api4j.sql.core;

import com.easy.query.api4j.sql.scec.SQLAliasNativeLambdaExpressionContext;
import com.easy.query.api4j.sql.scec.SQLAliasNativeLambdaExpressionContextImpl;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.core.SQLAsPropertyNative;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/7/31 14:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLAsLambdaNative<TEntity,TR,TChain> extends ChainCast<TChain> {
    <T> SQLAsPropertyNative<T> getSQLAsPropertyNative();

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
    default TChain sqlNativeSegment(String sqlSegment, SQLExpression1<SQLAliasNativeLambdaExpressionContext<TEntity,TR>> contextConsume){
        return sqlNativeSegment(true,sqlSegment,contextConsume);
    }

    default TChain sqlNativeSegment(boolean condition,String sqlSegment, SQLExpression1<SQLAliasNativeLambdaExpressionContext<TEntity,TR>> contextConsume){
        if(condition){
            getSQLAsPropertyNative().sqlNativeSegment(sqlSegment,context->{
                contextConsume.apply(new SQLAliasNativeLambdaExpressionContextImpl<>(context));
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
            getSQLAsPropertyNative().sqlNativeSegment(sqlSegment,context->{
                sqlFunction.consume(context.getSQLNativeChainExpressionContext());
            });
        }
        return castChain();
    }

    default TChain funcAs(SQLFunction sqlFunction, Property<TR,?> property){
        return funcAs(true,sqlFunction,property);
    }
    default TChain funcAs(boolean condition, SQLFunction sqlFunction,Property<TR,?> property){
        if(condition){
            String sqlSegment = sqlFunction.sqlSegment();
            getSQLAsPropertyNative().sqlNativeSegment(sqlSegment,context->{
                SQLNativeChainExpressionContext sqlNativeChainExpressionContext = context.getSQLNativeChainExpressionContext();
                sqlNativeChainExpressionContext.setPropertyAlias(EasyLambdaUtil.getPropertyName(property));
                sqlFunction.consume(sqlNativeChainExpressionContext);
            });
        }
        return castChain();
    }

}
