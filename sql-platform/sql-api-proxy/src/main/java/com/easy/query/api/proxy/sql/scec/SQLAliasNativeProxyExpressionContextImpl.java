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
public class SQLAliasNativeProxyExpressionContextImpl<TRProxy extends ProxyEntity<TRProxy, TR>, TR>  implements  SQLAliasNativeProxyExpressionContext<TRProxy,TR>{
    private final SQLAliasNativeExpressionContext sqlAliasNativeExpressionContext;

    public SQLAliasNativeProxyExpressionContextImpl(SQLAliasNativeExpressionContext sqlAliasNativeExpressionContext){

        this.sqlAliasNativeExpressionContext = sqlAliasNativeExpressionContext;
    }

    @Override
    public <TProperty> SQLAliasNativeProxyExpressionContext<TRProxy, TR> expressionAlias(SQLColumn<TRProxy, TProperty> sqlColumn) {
        sqlAliasNativeExpressionContext.expressionAlias(sqlColumn.value());
        return this;
    }

    @Override
    public <TProperty> SQLAliasNativeProxyExpressionContext<TRProxy, TR> setPropertyAlias(SQLColumn<TRProxy, TProperty> sqlColumn) {
        sqlAliasNativeExpressionContext.setPropertyAlias(sqlColumn.value());
        return this;
    }

    @Override
    public <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> SQLAliasNativeProxyExpressionContext<TRProxy, TR> expression(SQLColumn<TEntityProxy, TProperty> sqlColumn) {
        sqlAliasNativeExpressionContext.expression(sqlColumn.getTable(),sqlColumn.value());
        return this;
    }

    @Override
    public <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity> SQLAliasNativeProxyExpressionContext<TRProxy, TR> expression(ProxyQueryable<TEntityProxy, TEntity> subQuery) {
        sqlAliasNativeExpressionContext.expression(subQuery.getClientQueryable());
        return this;
    }

    @Override
    public SQLAliasNativeProxyExpressionContext<TRProxy, TR> value(Object val) {
        sqlAliasNativeExpressionContext.value(val);
        return this;
    }

    @Override
    public SQLAliasNativeProxyExpressionContext<TRProxy, TR> format(Object formatVal) {
        sqlAliasNativeExpressionContext.format(formatVal);
        return this;
    }

    @Override
    public SQLAliasNativeProxyExpressionContext<TRProxy, TR> setAlias(String alias) {
        sqlAliasNativeExpressionContext.setAlias(alias);
        return this;
    }
}
