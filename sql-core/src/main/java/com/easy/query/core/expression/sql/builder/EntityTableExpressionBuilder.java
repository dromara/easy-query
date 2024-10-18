package com.easy.query.core.expression.sql.builder;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.parser.core.base.ColumnSetter;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.sql.expression.EntityTableSQLExpression;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @FileName: EntityTableSegment.java
 * @Description: 文件说明
 * @Date: 2023/3/3 21:50
 * @author xuejiaming
 */
public interface EntityTableExpressionBuilder extends TableExpressionBuilder {
    EntityMetadata getEntityMetadata();

    void setTableNameAs(Function<String, String> tableNameAs);

    /**
     * 设计逻辑删除
     * @param tableLogicDel 返回false表示不启用逻辑删除
     */
    void setTableLogicDelete(Supplier<Boolean> tableLogicDel);

    void setSchemaAs(Function<String, String> schemaAs);
    void setTableLinkAs(Function<String, String> linkAs);
    void setTableSegmentAs(BiFunction<String, String, String> segmentAs);

    void asAlias(String alias);

    String getColumnName(String propertyName);

    SQLExpression1<WherePredicate<Object>> getLogicDeleteQueryFilterExpression();

    SQLExpression1<ColumnSetter<Object>> getLogicDeletedSQLExpression();

    EntityTableExpressionBuilder copyEntityTableExpressionBuilder();

    @Override
    EntityTableSQLExpression toExpression();
}
