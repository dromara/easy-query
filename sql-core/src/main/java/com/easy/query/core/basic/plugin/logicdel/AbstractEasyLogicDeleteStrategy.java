package com.easy.query.core.basic.plugin.logicdel;

import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.core.SqlWherePredicate;
import com.easy.query.core.expression.parser.core.SqlColumnSetter;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.ClassUtil;

import java.util.Set;

/**
 * @FileName: AbstractGlobalLogicDeleteStrategy.java
 * @Description: 文件说明
 * @Date: 2023/3/7 08:04
 * @author xuejiaming
 */
public abstract class AbstractEasyLogicDeleteStrategy implements EasyLogicDeleteStrategy {

    @Override
    public void configure(LogicDeleteBuilder builder) {
        Set<Class<?>> allowTypes = allowedPropertyTypes();
        if(allowTypes==null||allowTypes.isEmpty()){
            throw new EasyQueryException("plz set expectPropertyTypes values");
        }
        EntityMetadata entityMetadata = builder.getEntityMetadata();
        String propertyName = builder.getPropertyName();
        Class<?> propertyType = builder.getPropertyType();
        if(!allowTypes.contains(propertyType)){
            throw new EasyQueryException(ClassUtil.getSimpleName(entityMetadata.getEntityClass())+"."+propertyName+" logic delete not support, property type not allowed");
        }
        Property<Object, ?> lambdaProperty = builder.getPropertyLambda();
        SqlExpression<SqlWherePredicate<Object>> predicateFilterExpression = getPredicateFilterExpression(builder,lambdaProperty);
        SqlExpression<SqlColumnSetter<Object>> deletedSqlExpression = getDeletedSqlExpression(builder,lambdaProperty);
        builder.configure(predicateFilterExpression,deletedSqlExpression);
    }
    protected abstract SqlExpression<SqlWherePredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, Property<Object,?> lambdaProperty);
    protected abstract SqlExpression<SqlColumnSetter<Object>> getDeletedSqlExpression(LogicDeleteBuilder builder, Property<Object,?> lambdaProperty);
}
