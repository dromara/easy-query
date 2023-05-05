package com.easy.query.core.basic.plugin.logicdel;

import com.easy.query.core.basic.enums.LogicDeleteStrategyEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.core.SqlPredicate;
import com.easy.query.core.expression.parser.core.SqlColumnSetter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @FileName: ClassGlobalEntityTypeConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/6 22:45
 * @author xuejiaming
 */
public class BooleanEasyEntityTypeConfiguration extends AbstractEasyLogicDeleteStrategy {
    private static final Set<Class<?>> allowedPropertyTypes =new HashSet<>(Arrays.asList(Boolean.class,boolean.class));
    @Override
    public String getStrategy() {
        return LogicDeleteStrategyEnum.BOOLEAN.getStrategy();
    }

    @Override
    public Set<Class<?>> allowedPropertyTypes() {
        return allowedPropertyTypes;
    }


    @Override
    protected SqlExpression<SqlPredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, Property<Object,?> lambdaProperty) {
        return o->o.eq(lambdaProperty,false);
    }

    @Override
    protected SqlExpression<SqlColumnSetter<Object>> getDeletedSqlExpression(LogicDeleteBuilder builder, Property<Object,?> lambdaProperty) {
        return o->o.set(lambdaProperty, true);
    }
}
