package com.easy.query.core.expression.sql;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSetter;
import com.easy.query.core.expression.parser.abstraction.SqlPredicate;

import java.util.function.Function;

/**
 * @FileName: EntityTableSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 21:50
 * @author xuejiaming
 */
public interface EntityTableExpression extends TableExpressionSegment {
    EntityMetadata getEntityMetadata();
    <T1> String getPropertyName(Property<T1, ?> column);
    String getColumnName(String propertyName);
    String getTableName();
    void setTableNameAs(Function<String,String> tableNameAs);

     SqlExpression<SqlPredicate<Object>> getLogicDeleteQueryFilterExpression();
     SqlExpression<SqlColumnSetter<Object>> getLogicDeletedSqlExpression();
}
