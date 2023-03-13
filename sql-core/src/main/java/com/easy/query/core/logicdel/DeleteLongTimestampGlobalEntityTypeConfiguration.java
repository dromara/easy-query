package com.easy.query.core.logicdel;

import com.easy.query.core.basic.enums.LogicDeleteStrategyEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @FileName: TimestampGlobalEntityTypeConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/6 22:45
 * @Created by xuejiaming
 */
public class DeleteLongTimestampGlobalEntityTypeConfiguration extends AbstractGlobalLogicDeleteStrategy {
    @Override
    public String getStrategy() {
        return LogicDeleteStrategyEnum.DELETE_LONG_TIMESTAMP.getStrategy();
    }

    @Override
    public Set<Class<?>> expectPropertyTypes() {
        return new HashSet<>(Arrays.asList(Long.class,long.class));
    }


    @Override
    protected SqlExpression<SqlPredicate<?>> getQueryFilterExpression(EntityMetadata entityMetadata, Property lambdaProperty) {
        return o->o.eq(lambdaProperty,0);
    }

    @Override
    protected SqlExpression<SqlColumnSetter<?>> getDeletedSqlExpression(EntityMetadata entityMetadata, Property lambdaProperty) {
        return o->o.set(lambdaProperty, System.currentTimeMillis());
    }
}
