package com.easy.query.api.proxy.sql.scec;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.core.expression.segment.scec.context.SQLAliasNativeExpressionContext;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/7/29 23:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLAliasNativeProxyExpressionContextImpl implements  SQLAliasNativeProxyExpressionContext{
    private final SQLAliasNativeExpressionContext sqlAliasNativeExpressionContext;

    public SQLAliasNativeProxyExpressionContextImpl(SQLAliasNativeExpressionContext sqlAliasNativeExpressionContext){

        this.sqlAliasNativeExpressionContext = sqlAliasNativeExpressionContext;
    }

    @Override
    public <TRProxy extends ProxyEntity<TRProxy, TR>, TR, TProperty> SQLAliasNativeProxyExpressionContext expressionAlias(SQLColumn<TRProxy, TProperty> sqlColumn) {
        sqlAliasNativeExpressionContext.expressionAlias(sqlColumn.value());
        return this;
    }

    @Override
    public <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> SQLAliasNativeProxyExpressionContext expression(SQLColumn<TEntityProxy, TProperty> sqlColumn) {
        sqlAliasNativeExpressionContext.expression(sqlColumn.getTable(),sqlColumn.value());
        return this;
    }

    @Override
    public <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity> SQLAliasNativeProxyExpressionContext expression(ProxyQueryable<TEntityProxy, TEntity> subQuery) {
        sqlAliasNativeExpressionContext.expression(subQuery.getClientQueryable());
        return this;
    }

    @Override
    public SQLAliasNativeProxyExpressionContext value(Object val) {
        sqlAliasNativeExpressionContext.value(val);
        return this;
    }

    @Override
    public SQLAliasNativeProxyExpressionContext format(Object formatVal) {
        sqlAliasNativeExpressionContext.format(formatVal);
        return this;
    }

    @Override
    public SQLAliasNativeProxyExpressionContext setAlias(String alias) {
        sqlAliasNativeExpressionContext.setAlias(alias);
        return this;
    }
}
