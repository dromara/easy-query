package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.builder.impl.UpdateExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyUpdateSqlExpression;

/**
 * @FileName: SqlEntityUpdateExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 17:04
 * @author xuejiaming
 */
public interface EntityUpdateExpressionBuilder extends EntityPredicateExpressionBuilder, LambdaEntityExpressionBuilder,EntityToExpressionBuilder {

     SqlBuilderSegment getSetColumns();
     boolean hasSetColumns();

     PredicateSegment getWhere();
     boolean hasWhere();
     SqlBuilderSegment getSetIgnoreColumns();
     boolean hasSetIgnoreColumns();
     SqlBuilderSegment getWhereColumns();
     boolean hasWhereColumns();

     @Override
     EntityUpdateExpressionBuilder cloneEntityExpressionBuilder();
     @Override
     EasyUpdateSqlExpression toExpression();

     @Override
     EasyUpdateSqlExpression toExpression(Object entity);
}
