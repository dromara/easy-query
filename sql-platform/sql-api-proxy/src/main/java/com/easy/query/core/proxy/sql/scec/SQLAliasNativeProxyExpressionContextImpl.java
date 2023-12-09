package com.easy.query.core.proxy.sql.scec;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

import java.util.Collection;

/**
 * create time 2023/7/29 23:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLAliasNativeProxyExpressionContextImpl<TRProxy extends ProxyEntity<TRProxy, TR>, TR>  implements  SQLAliasNativeProxyExpressionContext<TRProxy,TR>{
    private final SQLNativeExpressionContext sqlNativeExpressionContext;

    public SQLAliasNativeProxyExpressionContextImpl(SQLNativeExpressionContext sqlNativeExpressionContext){

        this.sqlNativeExpressionContext = sqlNativeExpressionContext;
    }

    @Override
    public <TProperty> SQLAliasNativeProxyExpressionContext<TRProxy, TR> expressionAlias(SQLColumn<TRProxy, TProperty> sqlColumn) {
        sqlNativeExpressionContext.expressionAlias(sqlColumn.getValue());
        return this;
    }

    @Override
    public <TProperty> SQLAliasNativeProxyExpressionContext<TRProxy, TR> setPropertyAlias(SQLColumn<TRProxy, TProperty> sqlColumn) {
        sqlNativeExpressionContext.setPropertyAlias(sqlColumn.getValue());
        return this;
    }

    @Override
    public <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> SQLAliasNativeProxyExpressionContext<TRProxy, TR> expression(SQLColumn<TEntityProxy, TProperty> sqlColumn) {
        sqlNativeExpressionContext.expression(sqlColumn.getTable(),sqlColumn.getValue());
        return this;
    }

    @Override
    public <TEntity> SQLAliasNativeProxyExpressionContext<TRProxy, TR> expression(Query<TEntity> subQuery) {
        sqlNativeExpressionContext.expression(subQuery);
        return this;
    }

    @Override
    public <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> SQLAliasNativeProxyExpressionContext<TRProxy, TR> columnName(SQLColumn<TEntityProxy, TProperty> sqlColumn, String columnName) {
        sqlNativeExpressionContext.columnName(sqlColumn.getTable(),columnName);
        return this;
    }

    @Override
    public SQLAliasNativeProxyExpressionContext<TRProxy, TR> value(Object val) {
        sqlNativeExpressionContext.value(val);
        return this;
    }

    @Override
    public <T> SQLAliasNativeProxyExpressionContext<TRProxy, TR> collection(Collection<T> values) {
        sqlNativeExpressionContext.collection(values);
        return this;
    }

    @Override
    public SQLAliasNativeProxyExpressionContext<TRProxy, TR> format(Object formatVal) {
        sqlNativeExpressionContext.format(formatVal);
        return this;
    }

    @Override
    public SQLAliasNativeProxyExpressionContext<TRProxy, TR> setAlias(String alias) {
        sqlNativeExpressionContext.setAlias(alias);
        return this;
    }

    @Override
    public SQLAliasNativeProxyExpressionContext<TRProxy, TR> keepStyle() {
        sqlNativeExpressionContext.keepStyle();
        return this;
    }

    @Override
    public SQLAliasNativeProxyExpressionContext<TRProxy, TR> messageFormat() {
        sqlNativeExpressionContext.messageFormat();
        return this;
    }
}
