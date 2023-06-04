package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.function.Function;

/**
 * @FileName: EntityTableSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 21:50
 * @author xuejiaming
 */
public interface EntityTableExpressionBuilder extends TableExpressionBuilder {
    EntityMetadata getEntityMetadata();

    void setTableNameAs(Function<String, String> tableNameAs);

    void setSchemaAs(Function<String, String> schemaAs);

    void asAlias(String alias);

    String getColumnName(String propertyName);

    SQLExpression1<WherePredicate<Object>> getLogicDeleteQueryFilterExpression();

    SQLExpression1<ColumnSetter<Object>> getLogicDeletedSQLExpression();

    EntityTableExpressionBuilder copyEntityTableExpressionBuilder();

    @Override
    EntityTableSQLExpression toExpression();
}
