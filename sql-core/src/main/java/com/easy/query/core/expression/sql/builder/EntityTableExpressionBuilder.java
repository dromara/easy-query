package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.SQLColumnSetter;
import com.easy.query.core.expression.sql.expression.TableSQLExpression;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.lambda.SQLExpression1;

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

     SQLExpression1<SQLWherePredicate<Object>> getLogicDeleteQueryFilterExpression();
     SQLExpression1<SQLColumnSetter<Object>> getLogicDeletedSQLExpression();

    EntityTableExpressionBuilder copyEntityTableExpressionBuilder();

    @Override
    TableSQLExpression toExpression();
}
