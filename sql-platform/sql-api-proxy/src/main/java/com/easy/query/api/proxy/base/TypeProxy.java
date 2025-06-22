package com.easy.query.api.proxy.base;

import com.easy.query.api.proxy.util.EasyPropertyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.columns.types.SQLAnyTypeColumn;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/6/29 09:22
 *
 * @author xuejiaming
 */
public class TypeProxy<T> extends AbstractProxyEntity<TypeProxy<T>, T> {


    private final Class<T> entityClass;

    public TypeProxy(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }


    public <TProperty> SQLAnyTypeColumn<TypeProxy<T>, TProperty> column(String property) {
        return getAnyTypeColumn(property, EasyObjectUtil.typeCastNotNull(Object.class));
    }
    public <TProperty> SQLAnyTypeColumn<TypeProxy<T>, TProperty> column(Property<T, TProperty> property) {
        return getAnyTypeColumn(EasyPropertyLambdaUtil.getPropertyName(property), EasyObjectUtil.typeCastNotNull(Object.class));
    }

}