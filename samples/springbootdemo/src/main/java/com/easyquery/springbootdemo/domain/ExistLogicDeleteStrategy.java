package com.easyquery.springbootdemo.domain;

import com.easy.query.core.basic.plugin.logicdel.LogicDeleteBuilder;
import com.easy.query.core.basic.plugin.logicdel.abstraction.AbstractLogicDeleteStrategy;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * create time 2023/5/22 15:22
 * 文件说明
 *
 * @author xuejiaming
 */@Component
public class ExistLogicDeleteStrategy extends AbstractLogicDeleteStrategy {

    public static final String STRATEGY_NAME = "ExistLogicDelete";

    private final Set<Class<?>> allowTypes = new HashSet<>(Arrays.asList(Boolean.class, Boolean.TYPE));

    @Override
    protected SQLExpression1<WherePredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder logicDeleteBuilder, String propertyName) {
        return o -> o.eq(propertyName, true);
    }

    @Override
    protected SQLExpression1<ColumnSetter<Object>> getDeletedSQLExpression(LogicDeleteBuilder builder, String propertyName) {
        return o -> o.set(propertyName, false);
    }

    @Override
    public String getStrategy() {
        return STRATEGY_NAME;
    }

    @Override
    public Set<Class<?>> allowedPropertyTypes() {
        return allowTypes;
    }
}