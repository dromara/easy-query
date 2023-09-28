package com.easy.query.api.proxy.sql.scec;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/7/29 23:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeProxyExpressionContextImpl implements SQLNativeProxyExpressionContext {
    private final SQLNativeExpressionContext sqlConstExpressionContext;

    public SQLNativeProxyExpressionContextImpl(SQLNativeExpressionContext sqlConstExpressionContext){

        this.sqlConstExpressionContext = sqlConstExpressionContext;
    }
    @Override
    public <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> SQLNativeProxyExpressionContext expression(SQLColumn<TEntityProxy, TProperty> property) {
        sqlConstExpressionContext.expression(property.getTable(),property.value());
        return this;
    }

    @Override
    public <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity> SQLNativeProxyExpressionContext expression(ProxyQueryable<TEntityProxy, TEntity> subQuery) {
        sqlConstExpressionContext.expression(subQuery.getClientQueryable());
        return this;
    }

    @Override
    public SQLNativeProxyExpressionContext value(Object val) {
        sqlConstExpressionContext.value(val);
        return this;
    }

    @Override
    public SQLNativeProxyExpressionContext format(Object formatVal) {
        sqlConstExpressionContext.format(formatVal);
        return this;
    }

    @Override
    public SQLNativeProxyExpressionContext setAlias(String alias) {
        sqlConstExpressionContext.setAlias(alias);
        return this;
    }

    @Override
    public SQLNativeProxyExpressionContext keepStyle() {
        sqlConstExpressionContext.keepStyle();
        return this;
    }

    @Override
    public SQLNativeProxyExpressionContext messageFormat() {
        sqlConstExpressionContext.messageFormat();
        return this;
    }
}
