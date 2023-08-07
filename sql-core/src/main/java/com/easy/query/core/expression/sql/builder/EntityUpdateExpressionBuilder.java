package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EntityUpdateSQLExpression;

/**
 * @Description: 文件说明
 * @Date: 2023/3/4 17:04
 * @author xuejiaming
 */
public interface EntityUpdateExpressionBuilder extends EntityPredicateExpressionBuilder, LambdaEntityExpressionBuilder, EntityColumnConfigurerExpressionBuilder,EntityToExpressionBuilder {

     SQLBuilderSegment getSetColumns();
     boolean hasSetColumns();

     PredicateSegment getWhere();
     boolean hasWhere();
     SQLBuilderSegment getSetIgnoreColumns();
     boolean hasSetIgnoreColumns();
     SQLBuilderSegment getWhereColumns();

     @Override
     EntityUpdateExpressionBuilder cloneEntityExpressionBuilder();
     @Override
     EntityUpdateSQLExpression toExpression();

     @Override
     EntityUpdateSQLExpression toExpression(Object entity);
}
