package com.easy.query.api.proxy.entity.update;

import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.WithVersionable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.api.update.Updatable;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.common.ToSQLResult;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;

/**
 * @author xuejiaming
 * @FileName: ExpressionUpdatable.java
 * @Description: 文件说明
 * create time 2023/2/24 23:21
 */
public interface ExpressionUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> extends Updatable<T, ExpressionUpdatable<TProxy,T>>, WithVersionable<ExpressionUpdatable<TProxy,T>>, ConfigureVersionable<ExpressionUpdatable<TProxy,T>> {
    TProxy getProxy();
    ClientExpressionUpdatable<T> getClientUpdate();

    default ExpressionUpdatable<TProxy,T> setColumns(SQLActionExpression1<TProxy> columnSetExpression) {
        return setColumns(true,columnSetExpression);
    }

    default ExpressionUpdatable<TProxy,T> setColumns(boolean condition, SQLActionExpression1<TProxy> columnSetExpression) {
        if(condition){
            getProxy().getEntitySQLContext()._set(getClientUpdate().getColumnSetter().getSetter(),()->{
                columnSetExpression.apply(getProxy());
            });
        }
        return this;
    }


    /**
     * where(o->o.id().eq())
     * where(o->{
     *     o.id().eq(1)；
     *     o.title().eq(2)；
     * })
     *
     * @param whereExpression
     * @return
     */
    default ExpressionUpdatable<TProxy,T> where(SQLActionExpression1<TProxy> whereExpression) {
        return where(true,whereExpression);
    }

    /**
     * where(condition，o->o.id().eq())
     * where(condition,o->{
     *     o.id().eq(1)；
     *     o.title().eq(2)；
     * })
     * @param condition
     * @param whereExpression
     * @return
     */
    default ExpressionUpdatable<TProxy,T> where(boolean condition, SQLActionExpression1<TProxy> whereExpression) {
        if(condition){
            getClientUpdate().where(true,where -> {
                getProxy().getEntitySQLContext()._where(where.getFilter(),()->{
                    whereExpression.apply(getProxy());
                });
            });
        }
        return this;
    }

    default ExpressionUpdatable<TProxy,T> whereById(Object id) {
        getClientUpdate().whereById(id);
        return this;
    }

    default ExpressionUpdatable<TProxy,T> whereById(boolean condition, Object id) {
        getClientUpdate().whereById(condition, id);
        return this;
    }


    default <TProperty> ExpressionUpdatable<TProxy,T> whereByIds(Collection<TProperty> ids) {
        getClientUpdate().whereByIds(ids);
        return this;
    }

    default <TProperty> ExpressionUpdatable<TProxy,T> whereByIds(boolean condition, Collection<TProperty> ids) {
        getClientUpdate().whereByIds(condition, ids);
        return this;
    }

    default ExpressionContext getExpressionContext() {
        return getClientUpdate().getExpressionContext();
    }

    default String toSQL() {
        return getClientUpdate().toSQL();
    }

    default String toSQL(ToSQLContext toSQLContext) {
        return getClientUpdate().toSQL(toSQLContext);
    }

    default ToSQLResult toSQLResult() {
        ToSQLContext toSQLContext = DefaultToSQLContext.defaultToSQLContext(getExpressionContext().getTableContext());
        String sql = toSQL(toSQLContext);
        return new ToSQLResult(sql, toSQLContext);
    }
}

