package com.easy.query.api.proxy.entity.insert;

import com.easy.query.api.proxy.internal.ProxyEntityOnDuplicateKeyUpdate;
import com.easy.query.api.proxy.sql.ProxyColumnConfigurer;
import com.easy.query.core.basic.api.insert.Insertable;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;

/**
 * create time 2023/12/7 13:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityInsertable<TProxy extends ProxyEntity<TProxy, T>, T> extends Insertable<T, EntityInsertable<TProxy,T>> , ProxyEntityOnDuplicateKeyUpdate<TProxy,T,EntityInsertable<TProxy,T>> {
    @Override
    EntityInsertable<TProxy,T> insert(T entity);

    @Override
    EntityInsertable<TProxy,T> insert(Collection<T> entities);


    EntityInsertable<TProxy,T> columnConfigure(SQLExpression1<ProxyColumnConfigurer<TProxy,T>> columnConfigureExpression);
}
