package com.easy.query.api.proxy.sql.impl;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2024/1/25 11:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyEntityFillResult<TRProxyEntity extends ProxyEntity<TRProxyEntity, TREntity>, TREntity, TProperty, T1> {
    private final EntityQueryable<TRProxyEntity, TREntity> queryable;
    private final SQLFuncExpression1<TRProxyEntity,SQLColumn<TRProxyEntity, TProperty>> targetSQLColumn;
    private final Property<T1, TProperty> selfProperty;
    private final boolean consumeNull;

    public ProxyEntityFillResult(EntityQueryable<TRProxyEntity, TREntity> queryable, SQLFuncExpression1<TRProxyEntity,SQLColumn<TRProxyEntity, TProperty>> targetSQLColumn, Property<T1, TProperty> selfProperty) {
        this(queryable, targetSQLColumn, selfProperty, false);
    }


    public ProxyEntityFillResult(EntityQueryable<TRProxyEntity, TREntity> queryable, SQLFuncExpression1<TRProxyEntity,SQLColumn<TRProxyEntity, TProperty>> targetSQLColumn, Property<T1, TProperty> selfProperty, boolean consumeNull) {

        this.queryable = queryable;
        this.targetSQLColumn = targetSQLColumn;
        this.selfProperty = selfProperty;
        this.consumeNull = consumeNull;
    }

    public EntityQueryable<TRProxyEntity, TREntity> getQueryable() {
        return queryable;
    }

    public SQLFuncExpression1<TRProxyEntity,SQLColumn<TRProxyEntity, TProperty>> getTargetSQLColumn() {
        return targetSQLColumn;
    }

    public Property<T1, TProperty> getSelfProperty() {
        return selfProperty;
    }

    public boolean isConsumeNull() {
        return consumeNull;
    }
}
