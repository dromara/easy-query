package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.sql.expression.EasySqlExpression;
import com.easy.query.core.expression.sql.expression.EasyTableSqlExpression;
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
public interface EntityTableExpressionBuilder extends TableExpressionBuilder {
    EntityMetadata getEntityMetadata();
    String getTableName();
    void setTableNameAs(Function<String,String> tableNameAs);
    boolean tableNameIsAs();
    <T1> String getPropertyName(Property<T1, ?> column);
    String getColumnName(String propertyName);

     SqlExpression<SqlPredicate<Object>> getLogicDeleteQueryFilterExpression();
     SqlExpression<SqlColumnSetter<Object>> getLogicDeletedSqlExpression();

    EntityTableExpressionBuilder copyEntityTableExpressionBuilder();

    @Override
    EasyTableSqlExpression toExpression();
}
