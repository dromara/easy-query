package org.easy.query.core.query;

import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * @FileName: SqlEntityDeleteExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:30
 * @Created by xuejiaming
 */
public interface SqlEntityDeleteExpression extends SqlEntityExpression {

    PredicateSegment getWhere();

    boolean hasWhere();

    SqlBuilderSegment getWhereColumns();

    boolean hasWhereColumns();
}