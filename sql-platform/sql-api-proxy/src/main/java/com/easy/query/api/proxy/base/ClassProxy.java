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
public class ClassProxy<T> extends AbstractProxyEntity<ClassProxy<T>, T> {


    private final Class<T> entityClass;

    public ClassProxy(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }


    public <TProperty> SQLAnyTypeColumn<ClassProxy<T>, TProperty> field(String fieldName) {
        return getAnyTypeColumn(fieldName, EasyObjectUtil.typeCastNotNull(Object.class));
    }
    public <TProperty> SQLAnyTypeColumn<ClassProxy<T>, TProperty> field(Property<T, TProperty> fieldName) {
        return getAnyTypeColumn(EasyPropertyLambdaUtil.getPropertyName(fieldName), EasyObjectUtil.typeCastNotNull(Object.class));
    }

}