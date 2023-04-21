package com.easy.query.core.expression.sql;

import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * @FileName: SqlEntityDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:30
 * @author xuejiaming
 */
public interface EntityDeleteExpression extends EntityExpression,LambdaEntityExpression {

    PredicateSegment getWhere();

    boolean hasWhere();

    SqlBuilderSegment getWhereColumns();

    boolean hasWhereColumns();

    default EntityDeleteExpression cloneSqlDeleteExpression(){
        return (EntityDeleteExpression)cloneEntityExpression();
    }

}