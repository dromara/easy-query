package com.easy.query.api4kt.dynamic.sort.impl;

import com.easy.query.api4kt.dynamic.sort.ObjectSortBuilder4Kt;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.api.dynamic.sort.ObjectSortBuilder;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/6/11 11:16
 * 文件说明
 *
 * @author xuejiaming
 */
public class ObjectSortBuilder4KtImpl<TEntity> implements ObjectSortBuilder4Kt<TEntity> {
    private final ObjectSortBuilder objectSortBuilder;

    public ObjectSortBuilder4KtImpl(ObjectSortBuilder objectSortBuilder){

        this.objectSortBuilder = objectSortBuilder;
    }

    @Override
    public ObjectSortBuilder4Kt<TEntity> orderBy(String propertyName, boolean asc) {
        objectSortBuilder.orderBy(propertyName,asc);
        return this;
    }

    @Override
    public ObjectSortBuilder4Kt<TEntity> allowed(KProperty1<TEntity, ?> property) {
        objectSortBuilder.allowed(EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public ObjectSortBuilder4Kt<TEntity> notAllowed(KProperty1<TEntity, ?> property) {
        objectSortBuilder.notAllowed(EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }
}
