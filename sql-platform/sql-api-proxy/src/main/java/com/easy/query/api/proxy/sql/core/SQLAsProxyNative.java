package com.easy.query.api.proxy.sql.core;

import com.easy.query.api.proxy.sql.scec.SQLAliasNativeProxyExpressionContext;
import com.easy.query.api.proxy.sql.scec.SQLAliasNativeProxyExpressionContextImpl;
import com.easy.query.core.expression.builder.core.SQLAsNative;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.ChainCast;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

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


    default TChain sqlFunc(SQLFunction sqlFunction){
        return sqlFunc(true,sqlFunction);
    }
    default TChain sqlFunc(boolean condition, SQLFunction sqlFunction){
        if(condition){
            String sqlSegment = sqlFunction.sqlSegment(null);
            getSQLAsNative().sqlNativeSegment(sqlSegment,context->{
                sqlFunction.consume(new SQLNativeChainExpressionContextImpl(null,context));
            });
        }
        return castChain();
    }

    default TChain sqlFuncAs(SQLFunction sqlFunction, SQLColumn<TRProxy,TR> sqlColumn){
        return sqlFuncAs(true,sqlFunction,sqlColumn);
    }
    default TChain sqlFuncAs(boolean condition, SQLFunction sqlFunction, SQLColumn<TRProxy,TR> sqlColumn){
        if(condition){
            String sqlSegment = sqlFunction.sqlSegment(null);
            getSQLAsNative().sqlNativeSegment(sqlSegment,context->{
                SQLNativeChainExpressionContext sqlNativeChainExpressionContext = new SQLNativeChainExpressionContextImpl(null,context);
                sqlNativeChainExpressionContext.setPropertyAlias(sqlColumn.value());
                sqlFunction.consume(sqlNativeChainExpressionContext);
            });
        }
        return castChain();
    }
}
