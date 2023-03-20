package com.easy.query.core.query;

import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;

/**
 * @FileName: SqlEntityUpdateExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 17:04
 * @Created by xuejiaming
 */
public interface SqlEntityUpdateExpression extends SqlEntityExpression {

     SqlBuilderSegment getSetColumns();
     boolean hasSetColumns();

     PredicateSegment getWhere();
     boolean hasWhere();
     SqlBuilderSegment getSetIgnoreColumns();
     boolean hasSetIgnoreColumns();
     SqlBuilderSegment getWhereColumns();
     boolean hasWhereColumns();

     String toSql(Object entity);
}
