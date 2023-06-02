package com.easy.query.core.basic.plugin.logicdel.abstraction;

import com.easy.query.core.basic.plugin.logicdel.LogicDeleteBuilder;
import com.easy.query.core.basic.plugin.logicdel.LogicDeleteStrategy;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;

import java.util.Set;

/**
 * 逻辑删除抽象类
 */
public abstract class AbstractLogicDeleteStrategy implements LogicDeleteStrategy {

    @Override
    public void configure(LogicDeleteBuilder builder) {
        Set<Class<?>> allowTypes = allowedPropertyTypes();
        if (allowTypes == null || allowTypes.isEmpty()) {
            throw new EasyQueryException("plz set expectPropertyTypes values");
        }
        EntityMetadata entityMetadata = builder.getEntityMetadata();
        String propertyName = builder.getPropertyName();
        Class<?> propertyType = builder.getPropertyType();
        if (!allowTypes.contains(propertyType)) {
            throw new EasyQueryException(EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()) + "." + propertyName + " logic delete not support, property type not allowed");
        }
        SQLExpression1<WherePredicate<Object>> predicateFilterExpression = getPredicateFilterExpression(builder, propertyName);
        SQLExpression1<ColumnSetter<Object>> deletedSQLExpression = getDeletedSQLExpression(builder, propertyName);
        builder.configure(predicateFilterExpression, deletedSQLExpression);
    }

    protected abstract SQLExpression1<WherePredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, String propertyName);

    protected abstract SQLExpression1<ColumnSetter<Object>> getDeletedSQLExpression(LogicDeleteBuilder builder, String propertyName);
}
