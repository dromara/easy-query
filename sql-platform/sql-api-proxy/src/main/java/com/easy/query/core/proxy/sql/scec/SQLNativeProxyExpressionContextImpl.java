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
public class SQLNativeProxyExpressionContextImpl implements SQLNativeProxyExpressionContext {
    private final SQLNativeExpressionContext sqlConstExpressionContext;

    public SQLNativeProxyExpressionContextImpl(SQLNativeExpressionContext sqlConstExpressionContext){

        this.sqlConstExpressionContext = sqlConstExpressionContext;
    }
    @Override
    public <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> SQLNativeProxyExpressionContext expression(SQLColumn<TEntityProxy, TProperty> property) {
        sqlConstExpressionContext.expression(property.getTable(),property.getValue());
        return this;
    }

    @Override
    public <TEntity> SQLNativeProxyExpressionContext expression(Query<TEntity> subQuery) {
        sqlConstExpressionContext.expression(subQuery);
        return this;
    }

    @Override
    public <TEntityProxy extends ProxyEntity<TEntityProxy, TEntity>, TEntity, TProperty> SQLNativeProxyExpressionContext columnName(SQLColumn<TEntityProxy, TProperty> sqlColumn, String columnName) {
        sqlConstExpressionContext.columnName(sqlColumn.getTable(),columnName);
        return this;
    }

    @Override
    public SQLNativeProxyExpressionContext value(Object val) {
        sqlConstExpressionContext.value(val);
        return this;
    }

    @Override
    public <T> SQLNativeProxyExpressionContext collection(Collection<T> values) {
        sqlConstExpressionContext.collection(values);
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

    @Override
    public SQLNativeExpressionContext getSQLNativeExpressionContext() {
        return sqlConstExpressionContext;
    }
}
