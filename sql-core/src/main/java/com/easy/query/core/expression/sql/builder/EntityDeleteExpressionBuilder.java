package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.sql.expression.EasyDeleteSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyEntityPredicateSqlExpression;
import com.easy.query.core.expression.sql.expression.EasyEntitySqlExpression;
import com.easy.query.core.expression.sql.expression.EasySqlExpression;

/**
 * @FileName: SqlEntityDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:30
 * @author xuejiaming
 */
public interface EntityDeleteExpressionBuilder extends EntityPredicateExpressionBuilder, LambdaEntityExpressionBuilder {

    PredicateSegment getWhere();

    boolean hasWhere();

    SqlBuilderSegment getWhereColumns();

    boolean hasWhereColumns();

    @Override
    EntityDeleteExpressionBuilder cloneEntityExpressionBuilder();
}