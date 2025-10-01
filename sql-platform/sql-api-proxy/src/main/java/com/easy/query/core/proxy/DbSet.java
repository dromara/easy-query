package com.easy.query.core.proxy;

import com.easy.query.api.proxy.entity.delete.EntityDeletable;
import com.easy.query.api.proxy.entity.delete.ExpressionDeletable;
import com.easy.query.api.proxy.entity.insert.EntityInsertable;
import com.easy.query.api.proxy.entity.save.EntitySavable;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.update.EntityUpdatable;
import com.easy.query.api.proxy.entity.update.ExpressionUpdatable;
import com.easy.query.core.expression.lambda.SQLActionExpression1;

import java.util.Collection;

/**
 * create time 2025/9/28 20:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DbSet<TProxy extends ProxyEntity<TProxy, T>, T> extends EntityQueryable<TProxy, T> {

    EntityInsertable<TProxy, T> insertable(T entity);
    EntityInsertable<TProxy, T> insertable(Collection<T> entities);
    ExpressionUpdatable<TProxy, T> setColumns(SQLActionExpression1<TProxy> columnSetExpression);
    EntityUpdatable<TProxy, T> updatable(T entity);
    EntityUpdatable<TProxy, T> updatable(Collection<T> entities);
    EntityDeletable<TProxy, T> deletable(T entity);
    EntityDeletable<TProxy, T> deletable(Collection<T> entities);
    ExpressionDeletable<TProxy, T> deleteBy(SQLActionExpression1<TProxy> whereExpression);
    EntitySavable<TProxy, T> savable(T entity);
    EntitySavable<TProxy, T> savable(Collection<T> entities);
}
