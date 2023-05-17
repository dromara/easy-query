package com.easy.query.core.basic.plugin.logicdel.impl;

import com.easy.query.core.basic.enums.LogicDeleteStrategyEnum;
import com.easy.query.core.basic.plugin.logicdel.LogicDeleteBuilder;
import com.easy.query.core.basic.plugin.logicdel.abstraction.AbstractEasyLogicDeleteStrategy;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLColumnSetter;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @FileName: LocalDateTimeGlobalEntityTypeConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/6 22:45
 * @author xuejiaming
 */
public class LocalDateTimeEasyEntityTypeConfiguration extends AbstractEasyLogicDeleteStrategy {
    private static final Set<Class<?>> allowedPropertyTypes =new HashSet<>(Collections.singletonList(LocalDateTime.class));
    @Override
    public String getStrategy() {
        return LogicDeleteStrategyEnum.LOCAL_DATE_TIME.getStrategy();
    }

    @Override
    public Set<Class<?>> allowedPropertyTypes() {
        return allowedPropertyTypes;
    }

    @Override
    protected SQLExpression<SQLWherePredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, Property<Object,?> lambdaProperty) {
        return o->o.isNull(lambdaProperty);
    }

    @Override
    protected SQLExpression<SQLColumnSetter<Object>> getDeletedSQLExpression(LogicDeleteBuilder builder, Property<Object,?> lambdaProperty) {
        return o->o.set(lambdaProperty, LocalDateTime.now());
    }
}
