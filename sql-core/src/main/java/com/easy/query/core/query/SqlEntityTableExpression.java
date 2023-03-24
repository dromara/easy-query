package com.easy.query.core.query;

import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;

import java.util.List;

/**
 * @FileName: EntityTableSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 21:50
 * @author xuejiaming
 */
public interface SqlEntityTableExpression extends SqlTableExpressionSegment {
    EntityMetadata getEntityMetadata();
    <T1> String getPropertyName(Property<T1, ?> column);
    String getColumnName(String propertyName);

     SqlExpression<SqlPredicate<Object>> getLogicDeleteQueryFilterExpression();
     SqlExpression<SqlColumnSetter<Object>> getLogicDeletedSqlExpression();
}
