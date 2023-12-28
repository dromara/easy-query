package com.easy.query.api.proxy.entity.update;

import com.easy.query.api.proxy.sql.ProxyColumnConfigurer;
import com.easy.query.api.proxy.sql.impl.ProxyColumnConfigurerImpl;
import com.easy.query.core.basic.api.internal.ConfigureVersionable;
import com.easy.query.core.basic.api.internal.SQLExecuteStrategy;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.basic.api.update.Updatable;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.fetcher.EntityFetcher;

/**
 * create time 2023/12/7 13:55
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityUpdatable<TProxy extends ProxyEntity<TProxy, T>, T> extends Updatable<T, EntityUpdatable<TProxy,T>>, SQLExecuteStrategy<EntityUpdatable<TProxy,T>>,
        ConfigureVersionable<EntityUpdatable<TProxy,T>> {
    TProxy getTProxy();
    ClientEntityUpdatable<T> getClientUpdate();
    default EntityUpdatable<TProxy,T> columnConfigure(SQLExpression2<TProxy,ProxyColumnConfigurer<TProxy,T>> columnConfigureExpression){
        getClientUpdate().columnConfigure(configurer->{
            columnConfigureExpression.apply(getTProxy(),new ProxyColumnConfigurerImpl<>(configurer.getConfigurer()));
        });
        return this;
    }

    /**
     * 只更新列
     * @param columnSelectorExpression
     * @return
     */
    default EntityUpdatable<TProxy,T> setColumns(SQLFuncExpression1<TProxy, SQLSelectExpression> columnSelectorExpression) {
        return setColumns(true,columnSelectorExpression);
    }

    default EntityUpdatable<TProxy,T> setColumns(boolean condition, SQLFuncExpression1<TProxy, SQLSelectExpression> columnSelectorExpression) {
        if(condition){
            getClientUpdate().setColumns(selector -> {
                SQLSelectExpression sqlSelectExpression = columnSelectorExpression.apply(getTProxy());
                sqlSelectExpression.accept(selector.getOnlySelector());
            });
        }
        return this;
    }

    default EntityUpdatable<TProxy,T> setIgnoreColumns(SQLFuncExpression1<TProxy, SQLSelectExpression> columnSelectorExpression) {
        return setIgnoreColumns(true,columnSelectorExpression);
    }

    default EntityUpdatable<TProxy,T> setIgnoreColumns(boolean condition, SQLFuncExpression1<TProxy, SQLSelectExpression> columnSelectorExpression) {
        getClientUpdate().setIgnoreColumns(condition, selector -> {
            SQLSelectExpression sqlSelectExpression = columnSelectorExpression.apply(getTProxy());
            sqlSelectExpression.accept(selector.getOnlySelector());
        });
        return this;
    }

    default EntityUpdatable<TProxy,T> whereColumns(SQLFuncExpression1<TProxy, SQLSelectExpression> columnSelectorExpression) {
        return whereColumns(true,columnSelectorExpression);
    }

    default EntityUpdatable<TProxy,T> whereColumns(boolean condition, SQLFuncExpression1<TProxy, SQLSelectExpression> columnSelectorExpression) {
        getClientUpdate().whereColumns(condition, selector -> {
            SQLSelectExpression sqlSelectExpression = columnSelectorExpression.apply(getTProxy());
            sqlSelectExpression.accept(selector.getOnlySelector());
        });
        return this;
    }
    default String toSQL(Object entity) {
        return getClientUpdate().toSQL(entity);
    }
}
