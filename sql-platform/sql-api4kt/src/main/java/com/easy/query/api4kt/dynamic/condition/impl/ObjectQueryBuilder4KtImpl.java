package com.easy.query.api4kt.dynamic.condition.impl;

import com.easy.query.api4kt.dynamic.condition.ObjectQueryBuilder4Kt;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.api.dynamic.condition.ObjectQueryBuilder;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/6/11 09:53
 * 文件说明
 *
 * @author xuejiaming
 */
public class ObjectQueryBuilder4KtImpl<TObject,TEntity> implements ObjectQueryBuilder4Kt<TObject,TEntity> {
    private final ObjectQueryBuilder objectQueryBuilder;

    public ObjectQueryBuilder4KtImpl(ObjectQueryBuilder objectQueryBuilder){

        this.objectQueryBuilder = objectQueryBuilder;
    }

    @Override
    public ObjectQueryBuilder4Kt<TObject, TEntity> property(KProperty1<TEntity, ?> entityProperty, KProperty1<TObject, ?> property) {
        objectQueryBuilder.property(EasyKtLambdaUtil.getPropertyName(entityProperty),EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public ObjectQueryBuilder4Kt<TObject, TEntity> property(KProperty1<TEntity, ?> entityProperty, KProperty1<TObject, ?> property1, KProperty1<TObject, ?> property2) {
        objectQueryBuilder.property(EasyKtLambdaUtil.getPropertyName(entityProperty),EasyKtLambdaUtil.getPropertyName(property1), EasyKtLambdaUtil.getPropertyName(property2));
        return this;
    }
}
