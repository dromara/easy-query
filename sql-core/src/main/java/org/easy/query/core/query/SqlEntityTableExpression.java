package org.easy.query.core.query;

import org.easy.query.core.abstraction.metadata.EntityMetadata;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;

import java.util.List;

/**
 * @FileName: EntityTableSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 21:50
 * @Created by xuejiaming
 */
public interface SqlEntityTableExpression extends SqlTableExpressionSegment {
    EntityMetadata getEntityMetadata();
    <T1> String getPropertyName(Property<T1, ?> column);
    String getColumnName(String propertyName);

     SqlExpression<SqlPredicate<?>> getLogicDeleteQueryFilterExpression();
     List<String> getSelectInterceptorNames();
     SqlExpression<SqlColumnSetter<?>> getLogicDeletedSqlExpression();
}
