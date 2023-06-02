package com.easy.query.core.basic.plugin.logicdel.impl;

import com.easy.query.core.basic.plugin.logicdel.LogicDeleteBuilder;
import com.easy.query.core.basic.plugin.logicdel.LogicDeleteStrategyEnum;
import com.easy.query.core.basic.plugin.logicdel.abstraction.AbstractLogicDeleteStrategy;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @FileName: ClassGlobalEntityTypeConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/6 22:45
 * @author xuejiaming
 */
public class BooleanEasyEntityTypeConfiguration extends AbstractLogicDeleteStrategy {
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
    protected SQLExpression1<WherePredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, String propertyName) {
        return o -> o.eq(propertyName, false);
    }

    @Override
    protected SQLExpression1<ColumnSetter<Object>> getDeletedSQLExpression(LogicDeleteBuilder builder, String propertyName) {
        return o -> o.set(propertyName, true);
    }
}
