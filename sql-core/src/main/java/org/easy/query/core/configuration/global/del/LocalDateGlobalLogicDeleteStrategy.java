package org.easy.query.core.configuration.global.del;

import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.basic.enums.LogicDeleteStrategyEnum;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @FileName: LocalDateGlobalEntityTypeConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/6 22:45
 * @Created by xuejiaming
 */
public  class LocalDateGlobalLogicDeleteStrategy extends AbstractGlobalLogicDeleteStrategy {
    @Override
    public String getStrategy() {
        return LogicDeleteStrategyEnum.LOCAL_DATE.getStrategy();
    }

    @Override
    public Set<Class<?>> expectPropertyTypes() {
        return new HashSet<>(Collections.singletonList(LocalDate.class));
    }


    @Override
    protected SqlExpression<SqlPredicate<?>> getQueryFilterExpression(EntityMetadata entityMetadata, Property lambdaProperty) {
        return o->o.isNull(lambdaProperty);
    }

    @Override
    protected SqlExpression<SqlColumnSetter<?>> getDeletedSqlExpression(EntityMetadata entityMetadata, Property lambdaProperty) {
        return o->o.set(lambdaProperty,LocalDate.now());
    }
}
