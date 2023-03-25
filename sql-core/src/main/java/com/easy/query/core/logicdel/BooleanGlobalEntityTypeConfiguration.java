package com.easy.query.core.logicdel;

import com.easy.query.core.basic.enums.LogicDeleteStrategyEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @FileName: ClassGlobalEntityTypeConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/6 22:45
 * @author xuejiaming
 */
public class BooleanGlobalEntityTypeConfiguration extends AbstractGlobalLogicDeleteStrategy {
    private static final Set<Class<?>> allowedPropertyTypes =new HashSet<>(Collections.singletonList(Boolean.class));
    @Override
    public String getStrategy() {
        return LogicDeleteStrategyEnum.BOOLEAN.getStrategy();
    }

    @Override
    public Set<Class<?>> allowedPropertyTypes() {
        return allowedPropertyTypes;
    }


    @Override
    protected SqlExpression<SqlPredicate<Object>> getQueryFilterExpression(EntityMetadata entityMetadata, Property<Object,?> lambdaProperty) {
        return o->o.eq(lambdaProperty,false);
    }

    @Override
    protected SqlExpression<SqlColumnSetter<Object>> getDeletedSqlExpression(EntityMetadata entityMetadata, Property<Object,?> lambdaProperty) {
        return o->o.set(lambdaProperty, true);
    }
}
