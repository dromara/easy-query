package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * @Description: 文件说明
 * create time 2023/3/4 16:30
 * @author xuejiaming
 */
public interface EntityDeleteExpressionBuilder extends EntityPredicateExpressionBuilder, LambdaEntityExpressionBuilder {

    PredicateSegment getWhere();

    boolean hasWhere();

    SQLBuilderSegment getWhereColumns();

    boolean hasWhereColumns();

    @Override
    EntityDeleteExpressionBuilder cloneEntityExpressionBuilder();
}