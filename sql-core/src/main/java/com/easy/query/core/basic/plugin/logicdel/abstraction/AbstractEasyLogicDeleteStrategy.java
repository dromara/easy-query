package com.easy.query.core.basic.plugin.logicdel.abstraction;

import com.easy.query.core.basic.plugin.logicdel.EasyLogicDeleteStrategy;
import com.easy.query.core.basic.plugin.logicdel.LogicDeleteBuilder;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLColumnSetter;
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
        SQLExpression<SQLWherePredicate<Object>> predicateFilterExpression = getPredicateFilterExpression(builder,lambdaProperty);
        SQLExpression<SQLColumnSetter<Object>> deletedSQLExpression = getDeletedSQLExpression(builder,lambdaProperty);
        builder.configure(predicateFilterExpression,deletedSQLExpression);
    }
    protected abstract SQLExpression<SQLWherePredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, Property<Object,?> lambdaProperty);
    protected abstract SQLExpression<SQLColumnSetter<Object>> getDeletedSQLExpression(LogicDeleteBuilder builder, Property<Object,?> lambdaProperty);
}
