package com.easy.query.api.proxy.client;

import com.easy.query.api.proxy.entity.delete.EntityOnlyDeletable;
import com.easy.query.api.proxy.entity.delete.ExpressionDeletable;
import com.easy.query.api.proxy.entity.insert.EntityOnlyInsertable;
import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.api.proxy.entity.update.EntityOnlyUpdatable;
import com.easy.query.api.proxy.entity.update.ExpressionUpdatable;
import com.easy.query.core.configuration.LoadIncludeConfiguration;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.PropColumn;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * create time 2023/6/21 17:09
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyProxyQuery extends EasyBaseQuery{
    <TProxy extends ProxyEntity<TProxy, T>, T> EntityQueryable<TProxy, T> queryable(TProxy table);
    <TProxy extends ProxyEntity<TProxy, T>, T>  EntityQueryable<TProxy, T> queryable(String sql, TProxy table);

    <T> EntityOnlyInsertable<T> insertable(T entity);

    <T> EntityOnlyInsertable<T> insertable(Collection<T> entities);

    <TProxy extends ProxyEntity<TProxy, T>, T> ExpressionUpdatable<TProxy,T> updatable(TProxy table);

    <T> EntityOnlyUpdatable<T> updatable(T entity);

    <T> EntityOnlyUpdatable<T> updatable(Collection<T> entities);

    <T> EntityOnlyDeletable<T> deletable(T entity);

    <T> EntityOnlyDeletable<T> deletable(Collection<T> entities);

    <TProxy extends ProxyEntity<TProxy, T>, T> ExpressionDeletable<TProxy,T> deletable(TProxy table);


    default  <TProxy extends ProxyEntity<TProxy, T>, T>  void loadInclude(T entity,TProxy table, SQLFuncExpression1<TProxy, PropColumn> navigateProperty){
        loadInclude(Collections.singletonList(entity),table,navigateProperty);
    }
    default <TProxy extends ProxyEntity<TProxy, T>, T> void loadInclude(T entity,TProxy table,SQLFuncExpression1<TProxy, PropColumn> navigateProperty, SQLExpression1<LoadIncludeConfiguration> configure){
        loadInclude(Collections.singletonList(entity),table,navigateProperty,configure);
    }
    default <TProxy extends ProxyEntity<TProxy, T>, T> void loadInclude(List<T> entities,TProxy table,SQLFuncExpression1<TProxy, PropColumn> navigateProperty){
        loadInclude(entities,table,navigateProperty,null);
    }
    default <TProxy extends ProxyEntity<TProxy, T>, T> void loadInclude(List<T> entities,TProxy table, SQLFuncExpression1<TProxy, PropColumn> navigateProperty, SQLExpression1<LoadIncludeConfiguration> configure){
        PropColumn propColumn = navigateProperty.apply(table);
        getEasyQueryClient().loadInclude(entities,propColumn.getValue(),configure);
    }
}
