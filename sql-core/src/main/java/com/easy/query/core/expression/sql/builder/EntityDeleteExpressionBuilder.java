package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * @FileName: SqlEntityDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:30
 * @author xuejiaming
 */
public interface EntityDeleteExpressionBuilder extends EntityExpressionBuilder, LambdaEntityExpressionBuilder {

    PredicateSegment getWhere();

    boolean hasWhere();

    SqlBuilderSegment getWhereColumns();

    boolean hasWhereColumns();

    default EntityDeleteExpressionBuilder cloneSqlDeleteExpressionBuilder(){
        return (EntityDeleteExpressionBuilder) cloneEntityExpressionBuilder();
    }

}