package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLColumnSetter;
import com.easy.query.core.expression.sql.expression.EasyTableSQLExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.lambda.SQLExpression;

import java.util.function.Function;

/**
 * @FileName: EntityTableSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 21:50
 * @author xuejiaming
 */
public interface EntityTableExpressionBuilder extends TableExpressionBuilder {
    EntityMetadata getEntityMetadata();
    void setTableNameAs(Function<String,String> tableNameAs);
    boolean tableNameIsAs();
    String getColumnName(String propertyName);

     SQLExpression<SQLWherePredicate<Object>> getLogicDeleteQueryFilterExpression();
     SQLExpression<SQLColumnSetter<Object>> getLogicDeletedSQLExpression();

    EntityTableExpressionBuilder copyEntityTableExpressionBuilder();

    @Override
    EasyTableSQLExpression toExpression();
}
