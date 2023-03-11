package org.easy.query.core.logicdel;

import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.basic.enums.LogicDeleteStrategyEnum;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @FileName: LocalDateTimeGlobalEntityTypeConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/6 22:45
 * @Created by xuejiaming
 */
public class LocalDateTimeGlobalEntityTypeConfiguration extends AbstractGlobalLogicDeleteStrategy {
    @Override
    public String getStrategy() {
        return LogicDeleteStrategyEnum.LOCAL_DATE_TIME.getStrategy();
    }

    @Override
    public Set<Class<?>> expectPropertyTypes() {
        return new HashSet<>(Collections.singletonList(LocalDateTime.class));
    }

    @Override
    protected SqlExpression<SqlPredicate<?>> getQueryFilterExpression(EntityMetadata entityMetadata, Property lambdaProperty) {
        return o->o.isNull(lambdaProperty);
    }

    @Override
    protected SqlExpression<SqlColumnSetter<?>> getDeletedSqlExpression(EntityMetadata entityMetadata, Property lambdaProperty) {
        return o->o.set(lambdaProperty, LocalDateTime.now());
    }
}
