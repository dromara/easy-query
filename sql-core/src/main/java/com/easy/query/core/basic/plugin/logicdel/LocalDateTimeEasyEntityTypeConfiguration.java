package com.easy.query.core.basic.plugin.logicdel;

import com.easy.query.core.basic.enums.LogicDeleteStrategyEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;

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
    protected SqlExpression<SqlPredicate<Object>> getPredicateFilterExpression(LogicDeleteBuilder builder, Property<Object,?> lambdaProperty) {
        return o->o.isNull(lambdaProperty);
    }

    @Override
    protected SqlExpression<SqlColumnSetter<Object>> getDeletedSqlExpression(LogicDeleteBuilder builder, Property<Object,?> lambdaProperty) {
        return o->o.set(lambdaProperty, LocalDateTime.now());
    }
}
