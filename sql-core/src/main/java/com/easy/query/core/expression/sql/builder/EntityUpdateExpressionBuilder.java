package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.UpdateSQLExpression;

/**
 * @Description: 文件说明
 * @Date: 2023/3/4 17:04
 * @author xuejiaming
 */
public interface EntityUpdateExpressionBuilder extends EntityPredicateExpressionBuilder, LambdaEntityExpressionBuilder,EntityToExpressionBuilder {

     SQLBuilderSegment getSetColumns();
     boolean hasSetColumns();

     PredicateSegment getWhere();
     boolean hasWhere();
     SQLBuilderSegment getSetIgnoreColumns();
     boolean hasSetIgnoreColumns();
     SQLBuilderSegment getWhereColumns();
     boolean hasWhereColumns();

     @Override
     EntityUpdateExpressionBuilder cloneEntityExpressionBuilder();
     @Override
     UpdateSQLExpression toExpression();

     @Override
     UpdateSQLExpression toExpression(Object entity);
}
